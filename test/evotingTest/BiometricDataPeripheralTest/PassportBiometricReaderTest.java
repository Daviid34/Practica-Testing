package evotingTest.BiometricDataPeripheralTest;

import data.BiometricData;
import data.Nif;
import data.SingleBiometricData;
import evoting.biometricdataperipheral.PassportBiometricReader;
import evoting.biometricdataperipheral.PassportBiometricReaderImpl;
import exceptions.InvalidFormatException;
import exceptions.NotValidPassportException;
import exceptions.NullNifException;
import exceptions.PassportBiometricReadingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class PassportBiometricReaderTest {
    BiometricData passport;
    PassportBiometricReader passportBiometricReader;
    Nif voter;

    @BeforeEach
    void init() throws NullNifException, InvalidFormatException {
        SingleBiometricData facial = new SingleBiometricData(new byte[]{1, 2, 3, 4});
        SingleBiometricData finger = new SingleBiometricData(new byte[]{10,7,4});
        passport = new BiometricData(facial, finger);

        HashMap<BiometricData, Nif> organism = new HashMap<>();
        voter = new Nif("12345678K");
        organism.put(passport, voter);
        passportBiometricReader = new PassportBiometricReaderImpl(passport);
        ((PassportBiometricReaderImpl) passportBiometricReader).setOrganism(organism);
    }

    @Test
    void validatePassportTest() {
        assertDoesNotThrow(() -> {passportBiometricReader.validatePassport();});
    }

    @Test
    void incorrectValidationTest() {
        HashMap<BiometricData, Nif> organism = new HashMap<>();
        SingleBiometricData face = new SingleBiometricData(new byte[]{1});
        SingleBiometricData finger = new SingleBiometricData(new byte[]{10,7,4});
        organism.put(new BiometricData(face, finger), voter);
        ((PassportBiometricReaderImpl) passportBiometricReader).setOrganism(organism);
        assertThrows(NotValidPassportException.class, () -> {passportBiometricReader.validatePassport();});
    }

    @Test
    void getPassportBiometricDataTest() throws PassportBiometricReadingException {
        assertEquals(passport, passportBiometricReader.getPassportBiometricData());
    }

    @Test
    void incorrectGetPassportBiomDataTest() {
        passportBiometricReader = new PassportBiometricReaderImpl(null);
        assertThrows( PassportBiometricReadingException.class, () -> {passportBiometricReader.getPassportBiometricData();});
    }

    @Test
    void getNifOCRTest(){
        assertEquals(voter, passportBiometricReader.getNifWithOCR());
    }
}
