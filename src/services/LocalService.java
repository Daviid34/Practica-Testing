package services;

import data.Password;
import exceptions.InvalidAccountException;

public interface LocalService {
    void verifyAccount (String login, Password pssw) throws InvalidAccountException;
}
