package services;

import data.Password;
import exceptions.InvalidAccountException;

import java.util.HashMap;

public class LocalServiceImpl implements LocalService {
    HashMap<String, Password> loginHashMap;
    public LocalServiceImpl (HashMap<String, Password> loginHashMap) {
        this.loginHashMap = loginHashMap;
    }

    @Override
    public void verifyAccount(String login, Password pssw) throws InvalidAccountException {

        if (!loginHashMap.get(login).equals(pssw)) throw new InvalidAccountException("ERROR: The account provided by the support staff is invalid");

        if (!loginHashMap.get(login).equals(pssw)){
            throw new InvalidAccountException("ERROR: The account provided by the support staff is invalid");
        }

    }
}
