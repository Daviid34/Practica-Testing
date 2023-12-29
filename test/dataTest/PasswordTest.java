package dataTest;

import data.Password;
import exceptions.InvalidFormatException;
import exceptions.NullPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PasswordTest {
    Password password;

    @BeforeEach
    void init() throws InvalidFormatException, NullPasswordException {
        password = new Password("My_Password");
    }

    @Test
    public void testPasswordValid() {
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
    public void testEqualPasswordWithSameInstance() {
        assertTrue(password.equals(password));
    }

    @Test
    public void testEqualPasswordWithSamePasswords() throws InvalidFormatException, NullPasswordException {
        Password pwd2 = new Password("My_Password");
        assertTrue(password.equals(pwd2));
    }

    @Test
    public void testEqualPasswordWithDifferentPasswords() throws InvalidFormatException, NullPasswordException {
        Password pwd2 = new Password("Your_Password");
        assertFalse(password.equals(pwd2));
    }

    @Test
    public void testEqualPasswordWithNull() {
        assertFalse(password.equals(null));
    }

    @Test
    public void testEqualPasswordWithDifferentClass() {
        assertFalse(password.equals("My_Password"));
    }

}
