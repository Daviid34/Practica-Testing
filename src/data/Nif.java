package data;

import exceptions.InvalidFormatException;
import exceptions.NullNifException;

import java.util.regex.Pattern;

public class Nif {
    private static String nif = "";

    public Nif(String nif) throws InvalidFormatException, NullNifException {
        if (nif == null) {
            throw new NullNifException("The introduced NIF is NULL");
        }
        if (!isValidNifFormat(nif)) {
            throw new InvalidFormatException("The NIF format is incorrect");
        }
        Nif.nif = nif;
    }

    public static String getNif() {
        return nif;
    }

    public boolean isValidNifFormat(String nif) {
        String nifPattern = "^[0-9]{8}[A-Za-z]$";
        return Pattern.matches(nifPattern, nif);
    }
}
