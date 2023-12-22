package services;

import exceptions.ConnectException;
import exceptions.NotEnabledException;

public interface ElectoralOrganism {
    void canVote(Nif nif) throws NotEnabledException, ConnectException;
    void disableVoter(Nif nif) throws ConnectException;
}
