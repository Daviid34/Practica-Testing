package evotingTest.VotingKioskTestsFromPart2;

import data.*;
import evoting.VotingKiosk;
import evoting.biometricdataperipheral.HumanBiometricScanner;
import evoting.biometricdataperipheral.HumanBiometricScannerImpl;
import evoting.biometricdataperipheral.PassportBiometricReader;
import evoting.biometricdataperipheral.PassportBiometricReaderImpl;
import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.*;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReadFingPrEntryPointTest {
    private VotingKiosk server;
    PassportBiometricReader passportBiometricReader;
    HumanBiometricScanner humanBiometricScanner;
    List<VotingOption> parties;


    @BeforeEach
    void init() throws NullNifException, InvalidFormatException, NullPasswordException {
        HashMap<Nif, Boolean> canVoteHashMap = new HashMap<>();
        canVoteHashMap.put(new Nif("87654321Z"), true);
        HashMap<String, Password> loginHashMap = new HashMap<>();
        loginHashMap.put("Eric", new Password("Password321-"));

        Scrutiny scrutiny = new ScrutinyImpl();
        LocalService localService = new LocalServiceImpl(loginHashMap);
        ElectoralOrganism electoralOrganism = new ElectoralOrganismImpl(canVoteHashMap);

        server = new VotingKiosk(scrutiny, localService, electoralOrganism);

        SingleBiometricData facial = new SingleBiometricData(new byte[]{1, 2, 3, 4});
        SingleBiometricData finger = new SingleBiometricData(new byte[]{10,7,4});
        BiometricData voter = new BiometricData(facial, finger);
        humanBiometricScanner = new HumanBiometricScannerImpl(voter);
        server.setHumanBiometricScanner(humanBiometricScanner);

        HashMap<BiometricData, Nif> organism = new HashMap<>();
        Nif nifVoter = new Nif("87654321Z");
        organism.put(voter, nifVoter);
        passportBiometricReader = new PassportBiometricReaderImpl(voter);
        ((PassportBiometricReaderImpl) passportBiometricReader).setOrganism(organism);
        server.setPassportBiometricReader(passportBiometricReader);

        server.initVoting();
        parties = server.getParties();
        server.entryPointSetter(VotingKiosk.EntryPoint.ReadFingerPrintBiometrics);
    }

    @Test
    public void setDocument() throws ProceduralException {
        assertThrows(ProceduralException.class, () -> {server.setDocument('p');});
    }

    @Test
    public void grantExplicitConsent() throws ProceduralException, InvalidCharacterException {
        assertThrows(ProceduralException.class, () -> {server.grantExplicitConsent('v');});
    }

    @Test
    public void readPassport() throws  ProceduralException, NotValidPassportException, PassportBiometricReadingException {
        assertThrows(ProceduralException.class, () -> {server.readPassport();});
    }

    @Test
    public void readFaceBiometrics() throws  ProceduralException, HumanBiometricScanningException {
        assertThrows(ProceduralException.class, () -> {server.readFaceBiometrics();});
    }

    @Test
    public void readFingerPrintBiometrics() throws  ProceduralException, NotEnabledException, HumanBiometricScanningException,
            BiometricVerificationFailedException, ConnectException {
        assertDoesNotThrow(() -> {server.readFingerPrintBiometrics();});
    }

    @Test
    public void initOptionsNavigation() throws ProceduralException {
        assertThrows(ProceduralException.class, () -> {server.initOptionsNavigation();});
    }

    @Test
    public void consultVotingOption() throws ProceduralException {
        assertThrows(ProceduralException.class, () -> {server.consultVotingOption(parties.get(2));});
    }

    @Test
    public void vote() throws ProceduralException {
        assertThrows(ProceduralException.class, () -> {server.vote();});
    }

    @Test
    public void confirmVotingOption() throws ProceduralException {
        assertThrows(ProceduralException.class, () -> {server.confirmVotingOption('v');});
    }
}

