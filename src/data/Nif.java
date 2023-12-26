package data;

import exceptions.InvalidFormatException;
import exceptions.NullNifException;
import java.util.Objects;

import java.util.regex.Pattern;

final public class Nif {
    private final String nif;

    public Nif(String nif) throws InvalidFormatException, NullNifException {
        if (nif == null) {
            throw new NullNifException("The introduced NIF is NULL");
        }
        if (!isValidNifFormat(nif)) {
            throw new InvalidFormatException("The NIF format is incorrect");
        }
        this.nif = nif;
    }

    public String getNif() {
        return this.nif;
    }

    public boolean isValidNifFormat(String nif) {
        String nifPattern = "^[0-9]{8}[A-Za-z]$";
        return Pattern.matches(nifPattern, nif);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Nif n = (Nif)other;
        return nif.equals(n.nif);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nif);
    }
}
