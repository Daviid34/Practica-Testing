package dataTest;

import data.Nif;
import exceptions.InvalidFormatException;
import exceptions.NullNifException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NifTest {

    @Test
    public void testNifValid() throws NullNifException, InvalidFormatException {
        new Nif("12345678A");
        assertEquals("12345678A", Nif.getNif());
    }

    @Test
    public void testNifInvalid() {
        assertThrows(InvalidFormatException.class, () -> {new Nif("invalid_nif");});
    }

    @Test
    public void testNifNull() {
        assertThrows(NullNifException.class, () -> {new Nif(null);});
    }
}
