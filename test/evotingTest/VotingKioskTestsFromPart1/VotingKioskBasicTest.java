package evotingTest.VotingKioskTestsFromPart1;

import data.Nif;
import data.Password;
import data.VotingOption;
import evoting.VotingKiosk;
import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.*;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VotingKioskBasicTest implements evotingTest.interfaces.VotingKioskBasicTest {
    private VotingKiosk server;

    @BeforeEach
    void init() throws NullNifException, InvalidFormatException, NullPasswordException {
        HashMap<Nif, Boolean> canVoteHashMap = new HashMap<>();
        canVoteHashMap.put(new Nif("12345678K"), true);
        canVoteHashMap.put(new Nif("87654321Z"), true);

        HashMap<String, Password> loginHashMap = new HashMap<>();
        loginHashMap.put("David", new Password("Password123-"));
        loginHashMap.put("Eric", new Password("Password321-"));

        Scrutiny scrutiny = new ScrutinyImpl();
        LocalService localService = new LocalServiceImpl(loginHashMap);
        ElectoralOrganism electoralOrganism = new ElectoralOrganismImpl(canVoteHashMap);

        server = new VotingKiosk(scrutiny, localService, electoralOrganism);
    }

    //I decided to not put the @Test here bc with having it in multilpeVoterTest is enough
    //Otherwise this test would be checked twice
    public void basicIterationTest() {
        assertDoesNotThrow(()-> {server.initVoting();});
        assertDoesNotThrow(() -> {server.setDocument('n');});
        assertDoesNotThrow(() -> {server.enterAccount("David", new Password("Password123-"));});
        assertDoesNotThrow(() -> {server.confirmIdentif('v');});
        assertDoesNotThrow(() -> {server.enterNif(new Nif("12345678K"));});
        assertDoesNotThrow(() -> {server.initOptionsNavigation();});
        VotingOption party = server.getParties().get(0);
        assertDoesNotThrow(() -> {server.consultVotingOption(party);});
        assertDoesNotThrow(() -> {server.vote();});
        assertDoesNotThrow(() -> {server.confirmVotingOption('v');});

    }

    @Test
    public void invalidCharTest() {
        server.initVoting();
        server.entryPointSetter(VotingKiosk.EntryPoint.SetDocument);
        assertThrows(InvalidCharacterException.class, () -> {server.setDocument('a');});
        server.entryPointSetter(VotingKiosk.EntryPoint.ConfirmIdentif);
        assertThrows(InvalidCharacterException.class, () ->{server.confirmIdentif('a');});
        server.entryPointSetter(VotingKiosk.EntryPoint.ConfirmVotingOption);
        assertThrows(InvalidCharacterException.class, () -> {server.confirmVotingOption('a');});
    }

    @Test
    public void multipleVoterTest() {
        basicIterationTest();

        assertDoesNotThrow(()-> {server.initVoting();});
        assertDoesNotThrow(() -> {server.setDocument('n');});
        assertDoesNotThrow(() -> {server.enterAccount("Eric", new Password("Password321-"));});
        assertDoesNotThrow(() -> {server.confirmIdentif('v');});
        assertDoesNotThrow(() -> {server.enterNif(new Nif("87654321Z"));});
        assertDoesNotThrow(() -> {server.initOptionsNavigation();});
        VotingOption party = server.getParties().get(0);
        assertDoesNotThrow(() -> {server.consultVotingOption(party);});
        assertDoesNotThrow(() -> {server.vote();});
        assertDoesNotThrow(() -> {server.confirmVotingOption('v');});
    }
}
