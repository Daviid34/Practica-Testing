package evotingTest;

import data.Nif;
import data.Password;
import evoting.VotingKiosk;
import exceptions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;

public class VotingKioskSuccessTest {

    VotingKiosk server = new VotingKiosk();

    @BeforeEach
    void testInitVoting() {
        server.initVoting();
        assertEquals(0, server.getTotal());
        assertEquals(0, server.getNulls());
        assertEquals(0, server.getBlanks());
    }

    @Test
    void testSetDocument() {
        server.setDocument('D');
        char selectedDocument = server.getSelectedDocument();
        assertEquals('D', selectedDocument);
    }

    @Test
    void testEnterAccount_ValidAccount() throws InvalidFormatException, NullPasswordException, InvalidAccountException{
        String login = "user";
        Password password = new Password("Mm12345_");
        HashMap<String, Password> loginHashMap = new HashMap<>();
        loginHashMap.put(login, password);
        server.setLoginHashMap(loginHashMap);
        assertDoesNotThrow(() -> server.enterAccount(login, password));
        assertEquals(login, server.getLoggedInUser());
    }

    @Test
    void testConfirmIdentif_ValidDocument() {
        assertDoesNotThrow(() -> server.confirmIdentif('Y'));
    }

   @Test
    void testEnterNif_CanVote() throws NotEnabledException, ConnectException, NullNifException, InvalidFormatException {
       Nif validNif = new Nif("12345678X");
       HashMap<Nif, Boolean> canVoteHashMap = new HashMap<>();
       server.setCanVoteHashMap(canVoteHashMap);
       canVoteHashMap.put(validNif, true);
       assertDoesNotThrow(() -> server.enterNif(validNif));
       assertTrue(server.canVoteHashMap.get(validNif));
    }
}
