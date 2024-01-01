package dataTest;

import data.SingleBiometricData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SingleBiometricDataTest {

    byte[] facialKey1; //Could also be FingerPrintKey; it's just an example for the tests
    byte[] facialKey2;
    byte[] facialKey3;

    SingleBiometricData biometricData1;
    SingleBiometricData biometricData2;
    SingleBiometricData biometricData3;

    @BeforeEach
    void init() {
        facialKey1 = new byte[]{1, 2, 3, 4};
        facialKey2 = new byte[]{1, 2, 3, 4}; // Same as facialKey1
        facialKey3 = new byte[]{4, 3, 2, 1}; // Different
        biometricData1 = new SingleBiometricData(facialKey1);
        biometricData2 = new SingleBiometricData(facialKey2);
        biometricData3 = new SingleBiometricData(facialKey3);
    }

    @Test
    public void singleBiometricDataConstructorAndGetterTest() {
        assertArrayEquals(facialKey1, biometricData1.getBiometricKey());
        assertArrayEquals(facialKey2, biometricData2.getBiometricKey());
        assertArrayEquals(facialKey3, biometricData3.getBiometricKey());
    }

    @Test
    public void testEqualSingleBiometricDataWithSameInstance() {
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
