package evotingTest.votingKioskTest;

import data.Nif;
import data.Password;
import data.VotingOption;
import evoting.VotingKiosk;
import evotingTest.interfaces.VotingKioskTest;
import exceptions.InvalidFormatException;
import exceptions.NullNifException;
import exceptions.NullPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.*;

import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class VotingKioskBasicTest implements VotingKioskTest {
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
    public void initVotingTest() {
        assertDoesNotThrow(() ->{server.initVoting();});
    }

    @Override
    @Test
    public void setDocumentTest() {
        initVotingTest();
        assertDoesNotThrow(() -> {server.setDocument('n');});
    }

    @Override
    @Test
    public void enterAccountTest() {
        setDocumentTest();
        assertDoesNotThrow(()->{server.enterAccount("David", new Password("Password123-"));});
    }

    @Override
    @Test
    public void confirmIdentifTest() {
        enterAccountTest();
        assertDoesNotThrow(() -> {server.confirmIdentif('v');});
    }

    @Override
    @Test
    public void enterNifTest() {
        confirmIdentifTest();
        assertDoesNotThrow(()-> {server.enterNif(new Nif("12345678K"));});
    }

    @Override
    @Test
    public void initOptionsNavigationTest() {
        enterNifTest();
        assertDoesNotThrow(()-> {server.initOptionsNavigation();});
    }

    @Override
    @Test
    public void consultVotingOptionTest() {
        initOptionsNavigationTest();
        List<VotingOption> parties = server.getParties();
        VotingOption party = parties.get(2);
        assertDoesNotThrow(() -> {server.consultVotingOption(party);});
    }

    @Override
    @Test
    public void voteTest() {
        consultVotingOptionTest();
        assertDoesNotThrow(() -> {server.vote();});
    }

    @Override
    @Test
    public void confirmVotingOptionTest() {
        voteTest();
        assertDoesNotThrow(()-> {server.confirmVotingOption('v');});
    }
}
