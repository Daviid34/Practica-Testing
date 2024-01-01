package dataTest;

import data.BiometricData;
import data.SingleBiometricData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BiometricDataTest {

    byte[] facialKey1;
    byte[] facialKey2;
    byte[] fingerPrintKey1;
    byte[] fingerPrintKey2;

    SingleBiometricData biometricFacialData1;
    SingleBiometricData biometricFacialData2;
    SingleBiometricData biometricFingerData1;
    SingleBiometricData biometricFingerData2;

    BiometricData biometricData1;
    BiometricData biometricData2;
    BiometricData biometricData3;


    @BeforeEach
    void init() {
        facialKey1 = new byte[]{1, 2, 3, 4};
        facialKey2 = new byte[]{4, 3, 2, 1};
        fingerPrintKey1 = new byte[]{5, 6, 7, 8};
        fingerPrintKey2 = new byte[]{8, 7, 6, 5};
        biometricFacialData1 = new SingleBiometricData(facialKey1);
        biometricFacialData2 = new SingleBiometricData(facialKey2);
        biometricFingerData1 = new SingleBiometricData(fingerPrintKey1);
        biometricFingerData2 = new SingleBiometricData(fingerPrintKey2);
        biometricData1 = new BiometricData(biometricFacialData1, biometricFingerData1);
        biometricData2 = new BiometricData(biometricFacialData1, biometricFingerData1);
        biometricData3 = new BiometricData(biometricFacialData2, biometricFingerData2);
    }

    @Test
    public void BiometricDataConstructorAndGetterTest() {
        assertEquals(biometricFacialData1, biometricData1.getFacialBiometric());
        assertEquals(biometricFingerData1, biometricData1.getFingerprintBiometric());
        assertEquals(biometricFacialData1, biometricData2.getFacialBiometric());
        assertEquals(biometricFingerData1, biometricData2.getFingerprintBiometric());
        assertEquals(biometricFacialData2, biometricData3.getFacialBiometric());
        assertEquals(biometricFingerData2, biometricData3.getFingerprintBiometric());
    }

    @Test
    public void testEqualBiometricDataWithSameInstance() {
        assertTrue(biometricData1.equals(biometricData1));
        assertTrue(biometricData2.equals(biometricData2));
        assertTrue(biometricData3.equals(biometricData3));
    }

    @Test
    public void testEqualSingleBiometricDataWithSameKeys() {
        assertTrue(biometricData1.equals(biometricData2));
    }

    @Test
    public void testEqualSingleBiometricDataWithDifferentKeys() {
        assertFalse(biometricData1.equals(biometricData3));
        assertFalse(biometricData2.equals(biometricData3));
    }

    @Test
    public void testEqualSingleBiometricDataWithNull() {
        assertFalse(biometricData1.equals(null));
        assertFalse(biometricData2.equals(null));
        assertFalse(biometricData3.equals(null));
    }

    @Test
    public void testSameHashCodeWithSameSingleBiometricData() {
        assertEquals(biometricData1.hashCode(), biometricData2.hashCode());
    }

    @Test
    public void testDifferentHashCodeWithDifferentSingleBiometricData() {
        assertNotEquals(biometricData1.hashCode(), biometricData3.hashCode());
        assertNotEquals(biometricData2.hashCode(), biometricData3.hashCode());
    }
}