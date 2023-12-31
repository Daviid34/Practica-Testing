package evoting;

import data.*;
import exceptions.*;
import mocks.PartyListServer;

import services.ElectoralOrganism;
import services.LocalService;
import services.Scrutiny;

import evoting.biometricdataperipheral.HumanBiometricScanner;
import evoting.biometricdataperipheral.PassportBiometricReader;

import java.net.ConnectException;
import java.util.List;

public class VotingKiosk {
    Scrutiny scrutiny;
    LocalService localService;
    ElectoralOrganism electoralOrganism;
    Context context;

    PartyListServer partyListServer;
    List<VotingOption> parties;
    VotingOption partyChosen;

    Nif voter;

    public VotingKiosk(Scrutiny scrutiny, LocalService localService, ElectoralOrganism electoralOrganism) {
        this.scrutiny = scrutiny;
        this.localService = localService;
        this.electoralOrganism = electoralOrganism;

        partyListServer = new PartyListServer();
        parties = partyListServer.getList();
        scrutiny.initVoteCount(parties);
    }

    private static class Context{
        VotingKiosk.EntryPoint entryPoint;
        public Context() {
            entryPoint = EntryPoint.SetDocument;
        }
    }

    public enum EntryPoint {
        SetDocument, EnterAccount, ConfirmIdentif, EnterNif,
        InitOptionsNavigation, ConsultVotingOptions, Vote, ConfirmVotingOption,
        GrantExplicitConsent, ReadPassport,
        ReadFaceBiometrics, ReadFingerPrintBiometrics;
    }

    public void initVoting() {
        System.out.println("\rInitializing... ");
        System.out.println("Choose an identification document:\nNIF -> n \nPassport -> p\n");
        context = new Context();
    }

    public void setDocument(char c) throws ProceduralException, InvalidCharacterException {
        //Check if initVoting was called early
        if (context.entryPoint != EntryPoint.SetDocument) {
            throw new ProceduralException("ERROR: initVoting wasn't called earlier");
        }
        //In case the NIF option was chosen
        if (c == 'n') {
            System.out.println("Login via NIF detected: Support staff is required");
            context.entryPoint = EntryPoint.EnterAccount;
        }
        //In case the Passport option was chosen
        else if (c == 'p') {
            System.out.println("Login via Passport detected: Please grant 'v' or not 'f' your consent");
            context.entryPoint = EntryPoint.GrantExplicitConsent;
        } else {
            throw new InvalidCharacterException("ERROR: the char must be 'p' (passport) or 'n' (NIF)");
        }

    }

    public void enterAccount(String login, Password pssw) throws InvalidAccountException, ProceduralException {
        //Check that setDocument was called early
        if (context.entryPoint != EntryPoint.EnterAccount) throw new ProceduralException("ERROR: setDocument wasn't called earlier");
        localService.verifyAccount(login, pssw);
        System.out.println("The authentication was succesful");
        context.entryPoint = EntryPoint.ConfirmIdentif;
    }


    public void confirmIdentif(char conf) throws InvalidDNIDocumException, ProceduralException, InvalidCharacterException {
        //Check that enterAccount was called early
        if (context.entryPoint != EntryPoint.ConfirmIdentif) throw new ProceduralException("ERROR: enterAccount wasn't called earlier");
        if (conf != 'v' && conf != 'f') throw new InvalidCharacterException("ERROR: The char must be 'v' (confirm) or 'f' (deny)");
        if (conf == 'f') throw new InvalidDNIDocumException("ERROR: invalid DNI documentation");
        System.out.println("Enter the NIF");
        context.entryPoint = EntryPoint.EnterNif;
    }

    public void enterNif(Nif nif) throws NotEnabledException, ConnectException, ProceduralException {
        //Check that confirmIdentif was called early
        if (context.entryPoint != EntryPoint.EnterNif) throw new ProceduralException("ERROR: confirmIdentif wasn't called earlier");
        nif.isValidNifFormat();
        electoralOrganism.canVote(nif);
        voter = nif;
        System.out.println("Verification of vote OK, you may now initialize the option navigation");
        context.entryPoint = EntryPoint.InitOptionsNavigation;
    }

    public void initOptionsNavigation() throws ProceduralException {
        //Check that enterNif was called early
        if (context.entryPoint != EntryPoint.InitOptionsNavigation) throw new ProceduralException("ERROR: enterNif wasn't called earlier");
        showParties(parties);
        context.entryPoint = EntryPoint.ConsultVotingOptions;
    }


    public void consultVotingOption(VotingOption vopt) throws ProceduralException {
        //Check that initOptionsNavigation was called early
        if (context.entryPoint != EntryPoint.ConsultVotingOptions && context.entryPoint != EntryPoint.ConfirmVotingOption)
            throw new ProceduralException("ERROR: initOptionsNavigation wasn't called earlier");
        System.out.println();
        System.out.println("Information of the party specified:");
        System.out.println(vopt);
        partyChosen = vopt;
        context.entryPoint = EntryPoint.Vote;
    }

    public void vote() throws ProceduralException {
        //Check that consultOption was called early
        if (context.entryPoint != EntryPoint.Vote) throw new ProceduralException("ERROR: consultVotingOption wasn't called earlier");
        System.out.println("Please confirm that your Voting Option is indeed: " + partyChosen.getParty());
        context.entryPoint = EntryPoint.ConfirmVotingOption;
    }

    public void confirmVotingOption(char conf) throws ProceduralException, ConnectException, InvalidCharacterException {
        //Check that vote was called early
        if (context.entryPoint != EntryPoint.ConfirmVotingOption) throw new ProceduralException("ERROR: vote wasn't called earlier");
        if (conf != 'v' && conf != 'f') throw new InvalidCharacterException("ERROR: The char must be 'v' (confirm) or 'f' (deny)");
        if (conf == 'f'){
            partyChosen = null;
            //In case it's not confirmed, show again the voting options are anable to go back to ConsultVotingOptions
            showParties(parties);
            context.entryPoint = EntryPoint.ConsultVotingOptions;
        }
        if (conf == 'v') {
            System.out.println("\nVote confirmed!");
            scrutiny.scrutinize(partyChosen);
            electoralOrganism.disableVoter(voter);
            System.out.println("End of voting session");
            scrutiny.getScrutinyResults();
            System.out.println("Returning to main screen");
        }
    }

    /*
    ----------------------------------------------ADDITIONAL-METHODS--------------------------------------------------------
    */
    //All methods are implemented in order to ease the test
    public void showParties(List<VotingOption> parties) {
        System.out.println();
        System.out.println("Here you have the list of available parties to vote");
        for (VotingOption party : parties) {
            System.out.println(party.toString());
        }
    }

    public List<VotingOption> getParties() {
        return parties;
    }

    public void entryPointSetter(EntryPoint entryPoint) {
        context.entryPoint = entryPoint;
    }

    public void setHumanBiometricScanner(HumanBiometricScanner humanBiometricScanner) {this.humanBiometricScanner = humanBiometricScanner;}

    public void setPassportBiometricReader(PassportBiometricReader passportBiometricReader) {this.passportBiometricReader = passportBiometricReader;}

    /*
    ----------------------------------------------PART-2--------------------------------------------------------
    */
    BiometricData passportBiometricData;
    BiometricData humanBiometricData;
    SingleBiometricData faceData;

    HumanBiometricScanner humanBiometricScanner;
    PassportBiometricReader passportBiometricReader;

    public void grantExplicitConsent(char cons) throws ProceduralException, InvalidCharacterException {
        //Check that setDocument was called early
        if (context.entryPoint != EntryPoint.GrantExplicitConsent) throw new ProceduralException("ERROR: removeBiometricData wasn't called earlier");
        if (cons != 'v' && cons != 'f') throw new InvalidCharacterException("ERROR: The char must be 'v' (confirm) or 'f' (deny)");
        if (cons == 'v') {
            context.entryPoint = EntryPoint.ReadPassport;
            System.out.println("Proceeding to read passport");
        } else {
            context.entryPoint = EntryPoint.SetDocument;
            System.out.println("Consent denied...\nReturning to the 'Select Document' page...");
        }
    }

    public void readPassport ()
            throws NotValidPassportException, PassportBiometricReadingException, ProceduralException {
        //Check that grantExplicitConsent was called early
        if (context.entryPoint != EntryPoint.ReadPassport) throw new ProceduralException("ERROR: grantExplicitConsent wasn't called earlier");
        passportBiometricReader.validatePassport();
        passportBiometricData = passportBiometricReader.getPassportBiometricData();
        if (passportBiometricData == null) throw  new PassportBiometricReadingException("ERROR: Critical failure reading the passport");
        System.out.println("Validity and reading of parameters OK");
        voter = passportBiometricReader.getNifWithOCR();
        context.entryPoint = EntryPoint.ReadFaceBiometrics;
    }

    public void readFaceBiometrics () throws HumanBiometricScanningException, ProceduralException {
        //Check that readPassport was called early
        if (context.entryPoint != EntryPoint.ReadFaceBiometrics) throw new ProceduralException("ERROR: readPassport wasn't called earlier");
        faceData = humanBiometricScanner.scanFaceBiometrics();
        if(passportBiometricData.getFacialBiometric() != faceData){
            throw new HumanBiometricScanningException("ERROR: Error in the scan process");
        }
        System.out.println("Proceeding to read fingerprint biometrics");
        context.entryPoint = EntryPoint.ReadFingerPrintBiometrics;
    }

    public void readFingerPrintBiometrics()
            throws NotEnabledException, HumanBiometricScanningException,
            BiometricVerificationFailedException, ConnectException, ProceduralException {
        //Check that readFaceBiometrics was called early
        if (context.entryPoint != EntryPoint.ReadFingerPrintBiometrics) throw new ProceduralException("ERROR: readFaceBiometrics wasn't called earlier");
        SingleBiometricData fingerPrintData = humanBiometricScanner.scanFingerprintBiometrics();
        if(passportBiometricData.getFingerprintBiometric() != fingerPrintData) {
            throw new HumanBiometricScanningException("ERROR: Fingerprint biometric does not match");
        }
        humanBiometricData = new BiometricData(faceData, fingerPrintData);
        verifyBiometricData(humanBiometricData, passportBiometricData);
        removeBiometricData();
        electoralOrganism.canVote(voter);
        context.entryPoint = EntryPoint.InitOptionsNavigation;
        System.out.println("Successful identity and voting rights verification");
    }


    private void verifyBiometricData(BiometricData humanBioD, BiometricData passpBioD) throws BiometricVerificationFailedException {
        if (!humanBioD.equals(passpBioD)) {
            removeBiometricData();
            throw new BiometricVerificationFailedException("ERROR: biometric data does not match");
        }
        removeBiometricData();
    }

    private void removeBiometricData() {
        faceData = null;
        humanBiometricData = null;
        passportBiometricData = null;
    }
}
