package evotingTest.VotingKioskTestsFromPart1;

// The goal of this class is to test the navigation between the methods in the loop without throwing the ProceduralException

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

public class VotingKioskComplexTest {

    private VotingKiosk server;

    @BeforeEach
    void init() throws NullNifException, InvalidFormatException, NullPasswordException, ProceduralException, InvalidAccountException, InvalidDNIDocumException, NotEnabledException, ConnectException, InvalidCharacterException {
        HashMap<Nif, Boolean> canVoteHashMap = new HashMap<>();
        canVoteHashMap.put(new Nif("12345678K"), true);
        HashMap<String, Password> loginHashMap = new HashMap<>();
        loginHashMap.put("Eric", new Password("Password123-"));

        Scrutiny scrutiny = new ScrutinyImpl();
        LocalService localService = new LocalServiceImpl(loginHashMap);
        ElectoralOrganism electoralOrganism = new ElectoralOrganismImpl(canVoteHashMap);

        server = new VotingKiosk(scrutiny, localService, electoralOrganism);
        server.initVoting();
        server.setDocument('n');
        server.enterAccount("Eric", new Password("Password123-"));
        server.confirmIdentif('v');
        server.enterNif(new Nif("12345678K"));
        server.initOptionsNavigation();
        server.consultVotingOption(server.getParties().get(1));
        server.vote();
        server.confirmVotingOption('f');
        server.consultVotingOption(server.getParties().get(3));
        server.vote();
        server.confirmVotingOption('f');
    }

    @Test
    public void consultVotingOptionTest() throws ProceduralException, InvalidFormatException, NullPasswordException, InvalidAccountException, InvalidDNIDocumException, NullNifException, NotEnabledException, ConnectException {
        List<VotingOption> parties = server.getParties();
        VotingOption party = parties.get(2);
        assertDoesNotThrow(() -> {server.consultVotingOption(party);});
    }

    @Test
    public void voteTest() throws ProceduralException, InvalidFormatException, InvalidDNIDocumException, NullPasswordException, InvalidAccountException, NullNifException, NotEnabledException, ConnectException {
        consultVotingOptionTest();
        assertDoesNotThrow(() -> {server.vote();});
    }

    @Test
    public void confirmVotingOptionTest() throws NullNifException, ProceduralException, InvalidFormatException, InvalidDNIDocumException, NotEnabledException, NullPasswordException, InvalidAccountException, ConnectException {
        voteTest();
        assertDoesNotThrow(()-> {server.confirmVotingOption('v');});
    }
}
