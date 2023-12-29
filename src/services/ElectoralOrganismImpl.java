package services;

import data.Nif;
import exceptions.NotEnabledException;

import java.net.ConnectException;
import java.util.HashMap;

public class ElectoralOrganismImpl implements ElectoralOrganism {
    HashMap<Nif, Boolean> canVoteHashMap;

    public ElectoralOrganismImpl(HashMap<Nif, Boolean> canVoteHashMap) {
        this.canVoteHashMap = canVoteHashMap;
    }

    @Override
    public void canVote(Nif nif) throws NotEnabledException, ConnectException {
        if (!canVoteHashMap.containsKey(nif)) throw new NotEnabledException("ERROR: It does not exist in this electoral College");
        if (!canVoteHashMap.get(nif)) throw new NotEnabledException("ERROR: User already voted");
    }

    @Override
        public void disableVoter(Nif nif) throws ConnectException {
        canVoteHashMap.replace(nif, false);
    }
}
