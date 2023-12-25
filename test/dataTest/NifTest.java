package dataTest;

import data.Nif;
import exceptions.InvalidFormatException;
import exceptions.NullNifException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NifTest {

    @Test
    public void testNifValid() throws NullNifException, InvalidFormatException {
        Nif nif = new Nif("12345678A");
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
        Nif nif = new Nif("12345678A");
        assertTrue(nif.equalNif(nif));
    }

    @Test
    public void testEqualNifWithSameNifs() throws NullNifException, InvalidFormatException {
        Nif nif1 = new Nif("12345678A");
        Nif nif2 = new Nif("12345678A");
        assertTrue(nif1.equalNif(nif2));
    }

    @Test
    public void testEqualNifWithDifferentNifs() throws NullNifException, InvalidFormatException {
        Nif nif1 = new Nif("12345678A");
        Nif nif2 = new Nif("87654321Z");
        assertFalse(nif1.equalNif(nif2));
    }

    @Test
    public void testEqualNifWithNull() throws NullNifException, InvalidFormatException {
        Nif nif = new Nif("12345678A");
        assertFalse(nif.equalNif(null));
    }

    @Test
    public void testEqualNifWithDifferentClass() throws NullNifException, InvalidFormatException {
        Nif nif = new Nif("12345678A");
        assertFalse(nif.equalNif("12345678A"));
    }
}
