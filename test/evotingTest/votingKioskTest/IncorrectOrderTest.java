package evotingTest.votingKioskTest;

import data.Nif;
import data.Password;
import data.VotingOption;
import evoting.VotingKiosk;
import evotingTest.interfaces.VotingKioskTest;
import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.*;
import java.net.ConnectException;

import java.net.ConnectException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class IncorrectOrderTest implements VotingKioskTest {
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

    @Override
    @Test
    public void setDocumentTest() {
        assertThrows(ProceduralException.class, () -> {server.setDocument('v');});
    }

    @Override
    @Test
    public void enterAccountTest() {
        //Starting
        assertThrows(ProceduralException.class, () -> {server.enterAccount("David", new Password("Password123-"));});

        //After initVoting
        server.initVoting();
        assertThrows(ProceduralException.class, () -> {server.enterAccount("David", new Password("Password123-"));});
    }

    @Override
    @Test
    public void confirmIdentifTest() throws ProceduralException {
        //Starting
        assertThrows(ProceduralException.class, () -> {server.confirmIdentif('v');});

        //After initVoting
        enterAccountTest();
        assertThrows(ProceduralException.class, () -> {server.confirmIdentif('v');});

        //After setDocumentation
        server.setDocument('n');
        assertThrows(ProceduralException.class, () -> {server.confirmIdentif('v');});
    }

    @Override
    @Test
    public void enterNifTest() throws ProceduralException, InvalidFormatException, NullPasswordException, InvalidAccountException {
        //Starting
        assertThrows(ProceduralException.class, () -> {server.enterNif(new Nif("12345678K"));});

        //After initVoting
        enterAccountTest();
        assertThrows(ProceduralException.class, () -> {server.enterNif(new Nif("12345678K"));});

        //After setDocumentation
        confirmIdentifTest();
        assertThrows(ProceduralException.class, () -> {server.enterNif(new Nif("12345678K"));});

        //After enterAccountTest
        server.enterAccount("David", new Password("Password123-"));
    }

    @Override
    @Test
    public void initOptionsNavigationTest() throws ProceduralException, InvalidFormatException, NullPasswordException, InvalidAccountException, InvalidDNIDocumException {
        //Starting
        assertThrows(ProceduralException.class, () -> {server.initOptionsNavigation();});

        //After initVoting
        enterAccountTest();
        assertThrows(ProceduralException.class, () -> {server.initOptionsNavigation();});

        //After setDocumentation
        confirmIdentifTest();
        assertThrows(ProceduralException.class, () -> {server.initOptionsNavigation();});

        //After enterAccount
        enterNifTest();
        assertThrows(ProceduralException.class, () -> {server.initOptionsNavigation();});

        //After confirmIdentify
        server.confirmIdentif('v');
        assertThrows(ProceduralException.class, () -> {server.initOptionsNavigation();});
    }

    @Override
    @Test
    public void consultVotingOptionTest() throws ProceduralException, InvalidFormatException, NullPasswordException, InvalidAccountException, InvalidDNIDocumException, NullNifException, NotEnabledException, ConnectException {
        VotingOption party = new VotingOption("Party1");
        //Starting
        assertThrows(ProceduralException.class, () -> {server.consultVotingOption(party);});

        //After initVoting
        enterAccountTest();
        assertThrows(ProceduralException.class, () -> {server.consultVotingOption(party);});

        //After setDocumentation
        confirmIdentifTest();
        assertThrows(ProceduralException.class, () -> {server.consultVotingOption(party);});

        //After enterAccount
        enterNifTest();
        assertThrows(ProceduralException.class, () -> {server.consultVotingOption(party);});

        //After confirmIdentif
        initOptionsNavigationTest();
        assertThrows(ProceduralException.class, () -> {server.consultVotingOption(party);});

        //After enterNif
        server.enterNif(new Nif("12345678K"));
        assertThrows(ProceduralException.class, () -> {server.consultVotingOption(party);});
    }

    @Override
    @Test
    public void voteTest() throws ProceduralException, InvalidFormatException, InvalidDNIDocumException, NullPasswordException, InvalidAccountException, NullNifException, NotEnabledException, ConnectException {
        //Strarting
        assertThrows(ProceduralException.class, () -> {server.vote();});

        //After initVoting
        enterAccountTest();
        assertThrows(ProceduralException.class, () -> {server.vote();});

        //After setDocumentation
        confirmIdentifTest();
        assertThrows(ProceduralException.class, () -> {server.vote();});

        //After enterAccount
        enterNifTest();
        assertThrows(ProceduralException.class, () -> {server.vote();});

        //After confirmIdentif
        initOptionsNavigationTest();
        assertThrows(ProceduralException.class, () -> {server.vote();});

        //After enterNif
        consultVotingOptionTest();
        assertThrows(ProceduralException.class, () -> {server.vote();});

        //After initOptionNavigation
        server.initOptionsNavigation();
        assertThrows(ProceduralException.class, () -> {server.vote();});

    }

    @Override
    @Test
    public void confirmVotingOptionTest() throws NullNifException, ProceduralException, InvalidFormatException, InvalidDNIDocumException, NotEnabledException, NullPasswordException, InvalidAccountException, ConnectException {
        //Starting
        assertThrows(ProceduralException.class, () -> {server.confirmVotingOption('v');});

        //Strarting
        assertThrows(ProceduralException.class, () -> {server.confirmVotingOption('v');});

        //After initVoting
        enterAccountTest();
        assertThrows(ProceduralException.class, () -> {server.confirmVotingOption('v');});

        //After setDocumentation
        confirmIdentifTest();
        assertThrows(ProceduralException.class, () -> {server.confirmVotingOption('v');});

        //After enterAccount
        enterNifTest();
        assertThrows(ProceduralException.class, () -> {server.confirmVotingOption('v');});

        //After confirmIdentif
        initOptionsNavigationTest();
        assertThrows(ProceduralException.class, () -> {server.confirmVotingOption('v');});

        //After enterNif
        consultVotingOptionTest();
        assertThrows(ProceduralException.class, () -> {server.confirmVotingOption('v');});

        //After initOptionsNavigation
        voteTest();
        assertThrows(ProceduralException.class, () -> {server.confirmVotingOption('v');});

        //After consultingVotingOptions
        server.consultVotingOption(new VotingOption("Party1"));
        assertThrows(ProceduralException.class, () -> {server.confirmVotingOption('v');});
    }

    
}
