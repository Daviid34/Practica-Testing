package dataTest;

import data.Password;
import exceptions.InvalidFormatException;
import exceptions.NullPasswordException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void testEqualPasswordWithSameInstance() throws InvalidFormatException, NullPasswordException {
        Password password = new Password("My_password");
        assertTrue(password.equals(password));
    }

    @Test
    public void testEqualPasswordWithSamePasswords() throws InvalidFormatException, NullPasswordException {
        Password pwd1 = new Password("My_password");
        Password pwd2 = new Password("My_password");
        assertTrue(pwd1.equals(pwd2));
    }

    @Test
    public void testEqualPasswordWithDifferentPasswords() throws InvalidFormatException, NullPasswordException {
        Password pwd1 = new Password("My_password");
        Password pwd2 = new Password("Your_password");
        assertFalse(pwd1.equals(pwd2));
    }

    @Test
    public void testEqualPasswordWithNull() throws InvalidFormatException, NullPasswordException {
        Password password = new Password("My_password");
        assertFalse(password.equals(null));
    }

    @Test
    public void testEqualPasswordWithDifferentClass() throws InvalidFormatException, NullPasswordException {
        Password password = new Password("My_password");
        assertFalse(password.equals("My_password"));
    }

}
