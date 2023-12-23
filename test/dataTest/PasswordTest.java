package dataTest;

import data.Password;
import exceptions.InvalidFormatException;
import exceptions.NullPasswordException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PasswordTest {

    @Test
    public void testPasswordValid() throws InvalidFormatException, NullPasswordException {
        Password password = new Password("My_Password");
        assertEquals("My_Password", password.getPassword());
    }

    @Test
    public void testPasswordInvalid() {
        assertThrows(InvalidFormatException.class, () -> {new Password("12345");});
    }

    @Test
    public void testPasswordNull() {
        assertThrows(NullPasswordException.class, () -> {new Password(null);});
    }

}
