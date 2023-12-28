package evotingTest;

import data.Nif;
import data.Password;
import evoting.VotingKiosk;
import exceptions.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VotingKioskFailTest {

    VotingKiosk server = new VotingKiosk();

    @Test
    void testEnterAccount_InvalidAccount() throws InvalidAccountException {
        InvalidAccountException exception = assertThrows(InvalidAccountException.class,
                () -> server.enterAccount("invalidUser", new Password("Mm12345_")));
        assertEquals("ERROR: The account provided by the support staff is invalid", exception.getMessage());
    }

    @Test
    void testConfirmIdentif_InvalidDocument() throws InvalidDNIDocumException {
        InvalidDNIDocumException exception = assertThrows(InvalidDNIDocumException.class,
                () -> server.confirmIdentif('N'));
        assertEquals("Invalid document", exception.getMessage());
    }

    @Test
    void testEnterNif_NotEnabled() throws NotEnabledException, ConnectException {
        NotEnabledException exception = assertThrows(NotEnabledException.class,
                () -> server.enterNif(new Nif("87654321A")));
        assertEquals("ERROR: It does not exist in this electoral College", exception.getMessage());
    }
}
