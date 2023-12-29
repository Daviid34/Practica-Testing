package evotingTest;

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
    public void initVotingTest() {
        assertDoesNotThrow(() ->{server.initVoting();});
    }

    @Test
    public void setDocumentTest() {
        initVotingTest();
        assertDoesNotThrow(() -> {server.setDocument('n');});
    }

    @Test
    public void enterAccountTest() {
        setDocumentTest();
        assertDoesNotThrow(()->{server.enterAccount("David", new Password("Password123-"));});
    }

    @Test
    public void confirmIdentifTest() {
        enterAccountTest();
        assertThrows(InvalidDNIDocumException.class, () -> {server.confirmIdentif('f');});
        server.entryPointSetter(VotingKiosk.EntryPoint.ConfirmIdentif);
        assertDoesNotThrow(() -> {server.confirmIdentif('v');});
    }

    @Test
    public void enterNifTest() {
        confirmIdentifTest();
        assertDoesNotThrow(()-> {server.enterNif(new Nif("12345678K"));});
    }

    @Test
    public void initOptionsNavigationTest() {
        enterNifTest();
        assertDoesNotThrow(()-> {server.initOptionsNavigation();});
    }

    @Test
    public void consultVotingOptionTest() {
        initOptionsNavigationTest();
        List<VotingOption> parties = server.getParties();
        VotingOption party = parties.get(2);
        assertDoesNotThrow(() -> {server.consultVotingOption(party);});
    }

    @Test
    public void voteTest() {
        consultVotingOptionTest();
        assertDoesNotThrow(() -> {server.vote();});
    }

    @Test
    public void confirmVotingOptionTest() {
        voteTest();
        assertDoesNotThrow(()-> {server.confirmVotingOption('v');});
    }
}
