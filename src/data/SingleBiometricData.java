package data;

import java.util.Arrays;

final public class SingleBiometricData {
    private final byte[] biometricKey;

    public SingleBiometricData(byte[] biometricKey) {
        this.biometricKey = Arrays.copyOf(biometricKey, biometricKey.length);
    }

    public byte[] getBiometricKey() {
        return Arrays.copyOf(biometricKey, biometricKey.length);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleBiometricData sBD = (SingleBiometricData) o;
        return Arrays.equals(biometricKey, sBD.biometricKey);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(biometricKey);
    }
}
