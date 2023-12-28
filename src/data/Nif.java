package data;

import exceptions.InvalidFormatException;
import exceptions.NullNifException;

import java.util.regex.Pattern;

final public class Nif {
    private final String nif;

    public Nif(String nif) throws InvalidFormatException, NullNifException {
        if (nif == null) {
            throw new NullNifException("The introduced NIF is NULL");
        }
        this.nif = nif;
        if (!isValidNifFormat()) {
            throw new InvalidFormatException("The NIF format is incorrect");
        }

    }

    public String getNif() {
        return this.nif;
    }

    public boolean isValidNifFormat() {
        String nifPattern = "^[0-9]{8}[A-Za-z]$";
        return Pattern.matches(nifPattern, nif);
    }

    public boolean equalNif (Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Nif n = (Nif) o;
        return nif.equals(n.nif);
    }
}
