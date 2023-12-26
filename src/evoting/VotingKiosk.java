package evoting;

import data.Nif;
import data.Password;
import data.VotingOption;
import exceptions.ConnectException;
import exceptions.InvalidAccountException;
import exceptions.NotEnabledException;
import services.ElectoralOrganism;
import services.LocalService;
import services.Scrutiny;

import java.util.HashMap;
import java.util.List;

public class VotingKiosk implements ElectoralOrganism, LocalService, Scrutiny {
    HashMap<String, Password> loginHashMap;
    HashMap<Nif, Boolean> canVoteHashMap;

    public void canVote(Nif nif) throws NotEnabledException, ConnectException {
        if (!canVoteHashMap.containsKey(nif)) throw new NotEnabledException("ERROR: It does not exist in this electoral College");
        if (!canVoteHashMap.get(nif)) throw new NotEnabledException("ERROR: User already voted");
    }

    public void disableVoter(Nif nif) throws ConnectException {
        canVoteHashMap.replace(nif, true);
    }

    public void verifyAccount(String login, Password pssw) throws InvalidAccountException {
        if (!loginHashMap.get(login).equalPassword(pssw)) throw new InvalidAccountException("ERROR: The account provided by the support staff is invalid");
    }

    public void setLoginHashMap(HashMap<String, Password> loginHashMap) {
        this.loginHashMap = loginHashMap;
    }

    public void setCanVoteHashMap(HashMap<Nif, Boolean> canVoteHashMap) {
        this.canVoteHashMap = canVoteHashMap;
    }

    @Override
    public void initVoteCount(List<VotingOption> validParties) {

    }

    @Override
    public void scrutinize(VotingOption vopt) {

    }

    @Override
    public int getVotesFor(VotingOption vopt) {
        return 0;
    }

    @Override
    public int getTotal() {
        return 0;
    }

    @Override
    public int getNulls() {
        return 0;
    }

    @Override
    public int getBlanks() {
        return 0;
    }

    @Override
    public void getScrutinyResults() {

    }
}
