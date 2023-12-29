package evoting.biometricdataperipheral;

import data.BiometricData;
import data.Nif;
import exceptions.NotValidPassportException;
import exceptions.PassportBiometricReadingException;

import java.util.HashMap;

public class PassportBiometricReaderImpl implements  PassportBiometricReader{
    HashMap<BiometricData, Nif> organism;
    BiometricData passport;

    public PassportBiometricReaderImpl (BiometricData pasaport) {
        this.passport = pasaport;
    }

    public void setOrganism(HashMap<BiometricData, Nif> organism) {
        this.organism = organism;
    }

    @Override
    public void validatePassport() throws NotValidPassportException {
        if (!organism.containsKey(passport)) throw new NotValidPassportException("ERROR: there's a problem with the pasport");
    }

    @Override
    public Nif getNifWithOCR() {
        return organism.get(passport);
    }

    @Override
    public BiometricData getPassportBiometricData() throws PassportBiometricReadingException {
        if (passport == null) throw new PassportBiometricReadingException("ERROR: there's a problem with the biometric data scanning");
        return passport;
    }
}
