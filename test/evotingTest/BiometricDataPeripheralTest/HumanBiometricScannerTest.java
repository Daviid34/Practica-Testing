package evotingTest.BiometricDataPeripheralTest;

import data.BiometricData;
import data.SingleBiometricData;
import evoting.biometricdataperipheral.HumanBiometricScanner;
import evoting.biometricdataperipheral.HumanBiometricScannerImpl;
import exceptions.HumanBiometricScanningException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HumanBiometricScannerTest {
    HumanBiometricScanner HBS;
    SingleBiometricData facial;
    SingleBiometricData finger;
    BiometricData biometricData;

    @BeforeEach
    void init() {
        facial = new SingleBiometricData(new byte[]{1, 2, 3, 4});
        finger = new SingleBiometricData(new byte[]{10,7,4});
        biometricData = new BiometricData(facial, finger);
        HBS = new HumanBiometricScannerImpl(biometricData);
    }

    @Test
    void scanFaceBiometricTest() throws HumanBiometricScanningException {
        assertEquals(facial, HBS.scanFaceBiometrics());
    }

    @Test
    void scanFingerprintTest() throws HumanBiometricScanningException {
        assertEquals(finger, HBS.scanFingerprintBiometrics());
    }

    @Test
    void incorrectFaceScanTest() {
        HBS = new HumanBiometricScannerImpl(null);
        assertThrows(HumanBiometricScanningException.class, () -> {HBS.scanFaceBiometrics();});
    }

    @Test
    void incorrectFingerprintScanTest() {
        HBS = new HumanBiometricScannerImpl(null);
        assertThrows(HumanBiometricScanningException.class, () -> {HBS.scanFaceBiometrics();});
    }

}
