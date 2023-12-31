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

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class VotingKioskMultipleVoterTest {
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

    @Test
    void multipleVoterTest() throws InvalidCharacterException, ProceduralException, ConnectException, InvalidFormatException, NullPasswordException, NullNifException, InvalidAccountException, InvalidDNIDocumException, NotEnabledException {
        server.initVoting();
        server.setDocument('n');
        server.enterAccount("David", new Password("Password123-"));
        server.confirmIdentif('v');
        server.enterNif(new Nif("12345678K"));
        server.initOptionsNavigation();
        server.consultVotingOption(server.getParties().get(1));
        server.vote();
        server.confirmVotingOption('v');

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
