package evotingTest.VotingKioskEnterPointTest;

import data.Nif;
import data.Password;
import data.VotingOption;
import evoting.VotingKiosk;
import evotingTest.VotingKioskEnterPointTest.interfaces.VotingKioskTest;
import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.*;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class EnterNifEntryPointTest implements VotingKioskTest {
    private VotingKiosk server;
    List<VotingOption> parties;

    @BeforeEach
    void init() throws NullNifException, InvalidFormatException, NullPasswordException {
        HashMap<Nif, Boolean> canVoteHashMap = new HashMap<>();
        canVoteHashMap.put(new Nif("12345678K"), true);
        HashMap<String, Password> loginHashMap = new HashMap<>();
        loginHashMap.put("Maria", new Password("Password123-"));

        Scrutiny scrutiny = new ScrutinyImpl();
        LocalService localService = new LocalServiceImpl(loginHashMap);
        ElectoralOrganism electoralOrganism = new ElectoralOrganismImpl(canVoteHashMap);

        server = new VotingKiosk(scrutiny, localService, electoralOrganism);
        server.initVoting();
        parties = server.getParties();
        server.entryPointSetter(VotingKiosk.EntryPoint.EnterNif);
    }

    @Override
    @Test
    public void setDocument() throws ProceduralException {
        assertThrows(ProceduralException.class, ()-> {server.setDocument('n');});
    }

    @Override
    @Test
    public void enterAccount() throws InvalidAccountException, ProceduralException {
        assertThrows(ProceduralException.class, ()-> {server.enterAccount("Maria", new Password("Password123-"));});
    }

    @Override
    @Test
    public void confirmIdentif() throws InvalidDNIDocumException, ProceduralException {
        assertThrows(ProceduralException.class, ()-> {server.confirmIdentif('v');});
    }

    @Override
    @Test
    public void enterNif() throws NotEnabledException, ConnectException, ProceduralException {
        assertDoesNotThrow(()-> {server.enterNif(new Nif("12345678K"));});
    }

    @Override
    @Test
    public void initOptionsNavigation() throws ProceduralException {
        assertThrows(ProceduralException.class, ()-> {server.initOptionsNavigation();});
    }

    @Override
    @Test
    public void consultVotingOption() throws ProceduralException {
        assertThrows(ProceduralException.class, ()-> {server.consultVotingOption(parties.get(0));});
    }

    @Override
    @Test
    public void vote() throws ProceduralException {
        assertThrows(ProceduralException.class, ()-> {server.vote();});
    }

    @Override
    @Test
    public void confirmVotingOption() throws ProceduralException, ConnectException {
        assertThrows(ProceduralException.class, ()-> {server.confirmVotingOption('v');});
    }
}