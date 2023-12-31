package evotingTest.VotingKioskTestsFromPart1;

import data.Nif;
import data.Password;
import data.VotingOption;
import evoting.VotingKiosk;
import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.*;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VotingKioskBasicTest {
    private VotingKiosk server;

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
    }

    @Test
    void basicIterationTest() {
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
    void invalidCharTest() {
        server.initVoting();
        server.entryPointSetter(VotingKiosk.EntryPoint.SetDocument);
        assertThrows(InvalidCharacterException.class, () -> {server.setDocument('a');});
        server.entryPointSetter(VotingKiosk.EntryPoint.ConfirmIdentif);
        assertThrows(InvalidCharacterException.class, () ->{server.confirmIdentif('a');});
        server.entryPointSetter(VotingKiosk.EntryPoint.ConfirmVotingOption);
        assertThrows(InvalidCharacterException.class, () -> {server.confirmVotingOption('a');});
    }
}
