package data;

import exceptions.InvalidFormatException;
import exceptions.NullPasswordException;

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
        int specialCh = 0;
        int upperCh = 0;
        int other = 0;
        for (int i = 0; i < password.length(); i++) {
            char c = password.charAt(i);
            if (Character.isUpperCase(c)) upperCh++;
            else if (!Character.isLetterOrDigit(c) && !Character.isWhitespace(c)) specialCh++;
            else other++;
        }
        int totalCh = specialCh + upperCh + other;
        return totalCh > 7 && upperCh > 0 && specialCh > 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password pwd = (Password) o;
        return password.equals(pwd.password);
    }
    @Override
    public int hashCode () { return password.hashCode(); }
}
