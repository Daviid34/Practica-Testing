package dataTest;

import data.Nif;
import data.Password;
import exceptions.InvalidFormatException;
import exceptions.NullNifException;
import exceptions.NullPasswordException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NifTest {
    Nif nif;

    @BeforeEach
    void init() throws InvalidFormatException, NullPasswordException, NullNifException {
        nif = new Nif("12345678A");
    }

    @Test
    public void testNifValid() throws NullNifException, InvalidFormatException {
        assertEquals("12345678A", nif.getNif());
    }

    @Test
    public void testNifInvalid() {
        assertThrows(InvalidFormatException.class, () -> {new Nif("invalid_nif");});
    }

    @Test
    public void testNifNull() {
        assertThrows(NullNifException.class, () -> {new Nif(null);});
    }

    @Test
    public void testEqualNifWithSameInstance() throws NullNifException, InvalidFormatException {
        assertTrue(nif.equals(nif));
    }

    @Test
    public void testEqualNifWithSameNifs() throws NullNifException, InvalidFormatException {
        Nif nif2 = new Nif("12345678A");
        assertTrue(nif.equals(nif2));
    }

    @Test
    public void testEqualNifWithDifferentNifs() throws NullNifException, InvalidFormatException {
        Nif nif2 = new Nif("87654321Z");
        assertFalse(nif.equals(nif2));
    }

    @Test
    public void testEqualNifWithNull() throws NullNifException, InvalidFormatException {
        assertFalse(nif.equals(null));
    }

    @Test
    public void testEqualNifWithDifferentClass() throws NullNifException, InvalidFormatException {
        assertFalse(nif.equals("12345678A"));
    }
}
