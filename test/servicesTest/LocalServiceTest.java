package servicesTest;

import data.Password;
import evoting.VotingKiosk;
import exceptions.InvalidAccountException;
import exceptions.InvalidFormatException;
import exceptions.NullPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.LocalService;
import services.LocalServiceImpl;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class LocalServiceTest {
    LocalService server;

    @BeforeEach
    void init() throws InvalidFormatException, NullPasswordException {
        HashMap<String, Password> loginHashMap = new HashMap<>();
        loginHashMap.put("David", new Password("FakeP1-2"));
        server = new LocalServiceImpl(loginHashMap);
    }

    @Test
    void validAccountTest() throws InvalidFormatException, NullPasswordException {
        Password pass = new Password("FakeP1-2");
        assertDoesNotThrow(()-> {server.verifyAccount("David", pass);});
    }

    @Test
    void invalidAccountTest() throws InvalidFormatException, NullPasswordException, InvalidAccountException {
        Password pass = new Password("FakeP1-23");
        assertThrows(InvalidAccountException.class, () ->{server.verifyAccount("David", pass);});
    }
}
