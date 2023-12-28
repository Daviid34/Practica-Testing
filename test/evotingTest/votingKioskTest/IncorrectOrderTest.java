package evotingTest.votingKioskTest;

import data.Nif;
import data.Password;
import data.VotingOption;
import evoting.VotingKiosk;
import evotingTest.interfaces.VotingKioskTest;
import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class IncorrectOrderTest implements VotingKioskTest {
    private VotingKiosk server;

    @BeforeEach
    void init() throws NullNifException, InvalidFormatException, NullPasswordException {
        server = new VotingKiosk();

        HashMap<Nif, Boolean> HM1 = new HashMap<>();
        HM1.put(new Nif("12345678K"), true);
        HashMap<String, Password> HM2 = new HashMap<>();
        HM2.put("David", new Password("Password123-"));

        server.setCanVoteHashMap(HM1);
        server.setLoginHashMap(HM2);
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
    public void initOptionsNavigationTest() throws ProceduralException {
        //Starting
        assertThrows(ProceduralException.class, () -> {server.initOptionsNavigation();});

        //After initVoting
        enterAccountTest();
        assertThrows(ProceduralException.class, () -> {server.initOptionsNavigation();});

        //After setDocumentation
        confirmIdentifTest();
        assertThrows(ProceduralException.class, () -> {server.initOptionsNavigation();});

    }

    @Override
    @Test
    public void consultVotingOptionTest() {
        VotingOption party = new VotingOption("Party1");
        assertThrows(ProceduralException.class, () -> {server.consultVotingOption(party);});
    }

    @Override
    @Test
    public void voteTest() {
        assertThrows(ProceduralException.class, () -> {server.vote();});
    }

    @Override
    @Test
    public void confirmVotingOptionTest() {
        assertThrows(ProceduralException.class, () -> {server.confirmVotingOption('v');});
    }

    
}
