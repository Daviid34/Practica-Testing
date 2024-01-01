package evoting.biometricdataperipheral;

import data.BiometricData;
import data.SingleBiometricData;
import exceptions.HumanBiometricScanningException;

public class HumanBiometricScannerImpl implements  HumanBiometricScanner {
    private BiometricData biometricData;

    public HumanBiometricScannerImpl (BiometricData biometricData) {
        this.biometricData = biometricData;
    }

    @Override
    public SingleBiometricData scanFaceBiometrics() throws HumanBiometricScanningException {
        if (biometricData == null) throw new HumanBiometricScanningException("ERROR: Error in the scan process");
        return biometricData.getFacialBiometric();
    }

    @Override
    public SingleBiometricData scanFingerprintBiometrics() throws HumanBiometricScanningException {
        if (biometricData == null) throw new HumanBiometricScanningException("ERROR: Error in the scan process");
        return biometricData.getFingerprintBiometric();
    }
}
