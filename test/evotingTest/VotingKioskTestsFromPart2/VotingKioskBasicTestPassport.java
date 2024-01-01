package evotingTest.VotingKioskTestsFromPart2;

import data.*;
import evoting.VotingKiosk;
import evoting.biometricdataperipheral.HumanBiometricScanner;
import evoting.biometricdataperipheral.HumanBiometricScannerImpl;
import evoting.biometricdataperipheral.PassportBiometricReader;
import evoting.biometricdataperipheral.PassportBiometricReaderImpl;
import evotingTest.interfaces.VotingKioskBasicTest;
import exceptions.InvalidCharacterException;
import exceptions.InvalidFormatException;
import exceptions.NullNifException;
import exceptions.NullPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.*;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class VotingKioskBasicTestPassport implements VotingKioskBasicTest {
    private VotingKiosk server;
    PassportBiometricReader passportBiometricReader;
    HumanBiometricScanner humanBiometricScanner;
    BiometricData voter2;


    @BeforeEach
    void init() throws NullNifException, InvalidFormatException, NullPasswordException {
        HashMap<Nif, Boolean> canVoteHashMap = new HashMap<>();
        canVoteHashMap.put(new Nif("12345678K"), true);
        canVoteHashMap.put(new Nif("87654321K"), true);
        HashMap<String, Password> loginHashMap = new HashMap<>();
        loginHashMap.put("David", new Password("Password123-"));
        loginHashMap.put("Tania", new Password("Password321-"));

        Scrutiny scrutiny = new ScrutinyImpl();
        LocalService localService = new LocalServiceImpl(loginHashMap);
        ElectoralOrganism electoralOrganism = new ElectoralOrganismImpl(canVoteHashMap);

        server = new VotingKiosk(scrutiny, localService, electoralOrganism);

        SingleBiometricData facial = new SingleBiometricData(new byte[]{1, 2, 3, 4});
        SingleBiometricData finger = new SingleBiometricData(new byte[]{10,7,4});
        BiometricData voter = new BiometricData(facial, finger);
        voter2 = new BiometricData(finger, facial);
        humanBiometricScanner = new HumanBiometricScannerImpl(voter);
        server.setHumanBiometricScanner(humanBiometricScanner);

        HashMap<BiometricData, Nif> organism = new HashMap<>();
        Nif nifVoter = new Nif("12345678K");
        Nif nifVoter2 = new Nif("87654321K");
        organism.put(voter2, nifVoter2);
        organism.put(voter, nifVoter);
        passportBiometricReader = new PassportBiometricReaderImpl(voter);
        ((PassportBiometricReaderImpl) passportBiometricReader).setOrganism(organism);
        server.setPassportBiometricReader(passportBiometricReader);
    }

    public void basicIterationTest() {
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

    @Test
    public void invalidCharTest() {
        server.initVoting();
        server.entryPointSetter(VotingKiosk.EntryPoint.GrantExplicitConsent);
        assertThrows(InvalidCharacterException.class, () -> {server.grantExplicitConsent('a');});
    }

    @Test
    public void multipleVoterTest() {
        basicIterationTest();

        ((PassportBiometricReaderImpl) passportBiometricReader).setDifferentPassport(voter2);
        humanBiometricScanner = new HumanBiometricScannerImpl(voter2);
        server.setPassportBiometricReader(passportBiometricReader);
        server.setHumanBiometricScanner(humanBiometricScanner);
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

    @Test
    public void removeBiometricDataTest() {
        basicIterationTest();
        assertEquals(null, server.getFaceData());
        assertEquals(null, server.getHumanBiometricData());
        assertEquals(null, server.getPassportBiometricData());
    }
}
