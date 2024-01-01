package data;

import java.util.Objects;

final public class BiometricData {
    private final SingleBiometricData facialBiometric;
    private final SingleBiometricData fingerprintBiometric;

    public BiometricData(SingleBiometricData facialBiometric, SingleBiometricData fingerprintBiometric) {
        this.facialBiometric = Objects.requireNonNull(facialBiometric);
        this.fingerprintBiometric = Objects.requireNonNull(fingerprintBiometric);
    }

    public SingleBiometricData getFacialBiometric() {
        return facialBiometric;
    }

    public SingleBiometricData getFingerprintBiometric() {
        return fingerprintBiometric;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BiometricData bD = (BiometricData) o;
        return facialBiometric.equals(bD.facialBiometric) && fingerprintBiometric.equals(bD.fingerprintBiometric);
    }

    @Override
    public int hashCode() {
        return Objects.hash(facialBiometric, fingerprintBiometric);
    }
}