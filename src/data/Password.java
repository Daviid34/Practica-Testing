package data;

import exceptions.InvalidFormatException;
import exceptions.NullPasswordException;

import java.util.regex.Pattern;

final public class Password {
    private final String password;

    public Password(String password) throws InvalidFormatException, NullPasswordException {
        if (password == null) {
            throw new NullPasswordException("The introduced password is NULL");
        }
        if (!isValidPassword(password)) {
            throw new InvalidFormatException("The password must contain at least 8 characters, 1 upper case and 1 special character");
        }
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public boolean isValidPassword(String password) {
        String passwordPattern = "^(?=.*[A-Z])(?=.*[!@#$%^&*()-+_={}\\[\\]:;<>,.?/~]).{8,}$";
        return Pattern.matches(passwordPattern, password);
    }
}
