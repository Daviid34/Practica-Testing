package evotingTest.VotingKioskTestsFromPart2;

import data.*;
import evoting.VotingKiosk;
import evoting.biometricdataperipheral.HumanBiometricScanner;
import evoting.biometricdataperipheral.HumanBiometricScannerImpl;
import evoting.biometricdataperipheral.PassportBiometricReader;
import evoting.biometricdataperipheral.PassportBiometricReaderImpl;
import exceptions.InvalidFormatException;
import exceptions.NullNifException;
import exceptions.NullPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class VotingKioskBasicTestPassport {
    private VotingKiosk server;
    private BiometricData voter;

    @BeforeEach
    void init() throws NullNifException, InvalidFormatException, NullPasswordException {
        HashMap<Nif, Boolean> canVoteHashMap = new HashMap<>();
        canVoteHashMap.put(new Nif("12345678K"), true);
        HashMap<String, Password> loginHashMap = new HashMap<>();
        loginHashMap.put("David", new Password("Password123-"));

        Scrutiny scrutiny = new ScrutinyImpl();
        LocalService localService = new LocalServiceImpl(loginHashMap);
        ElectoralOrganism electoralOrganism = new ElectoralOrganismImpl(canVoteHashMap);

        server = new VotingKiosk(scrutiny, localService, electoralOrganism);

        SingleBiometricData facial = new SingleBiometricData(new byte[]{1, 2, 3, 4});
        SingleBiometricData finger = new SingleBiometricData(new byte[]{10,7,4});
        voter = new BiometricData(facial, finger);
        HumanBiometricScanner humanBiometricScanner = new HumanBiometricScannerImpl(voter);
        server.setHumanBiometricScanner(humanBiometricScanner);

        HashMap<BiometricData, Nif> organism = new HashMap<>();
        Nif nifVoter = new Nif("12345678K");
        organism.put(voter, nifVoter);
        PassportBiometricReader passportBiometricReader = new PassportBiometricReaderImpl(voter);
        ((PassportBiometricReaderImpl) passportBiometricReader).setOrganism(organism);
        server.setPassportBiometricReader(passportBiometricReader);
    }

    @Test
    void basicIterationTest() {
        assertDoesNotThrow(()-> {server.initVoting();});
        assertDoesNotThrow(() -> {server.setDocument('p');});
        assertDoesNotThrow(() -> {server.grantExplicitConsent('v');});
        assertDoesNotThrow(() -> {server.readPassport();});
        assertDoesNotThrow(() -> {server.readFaceBiometrics();});
        assertDoesNotThrow(() -> {server.readFingerPrintBiometrics();});
        assertDoesNotThrow(() -> {server.initOptionsNavigation();});
        VotingOption party = server.getParties().get(0);
        assertDoesNotThrow(() -> {server.consultVotingOption(party);});
        assertDoesNotThrow(() -> {server.vote();});
        assertDoesNotThrow(() -> {server.confirmVotingOption('v');});
    }
}
