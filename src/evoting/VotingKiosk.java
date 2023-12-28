package evoting;

import data.Nif;
import data.Password;
import data.VotingOption;
import exceptions.ConnectException;
import exceptions.InvalidAccountException;
import exceptions.InvalidDNIDocumException;
import exceptions.NotEnabledException;
import services.ElectoralOrganism;
import services.LocalService;
import services.Scrutiny;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;

public class VotingKiosk implements ElectoralOrganism, LocalService, Scrutiny {
    HashMap<String, Password> loginHashMap;
    HashMap<Nif, Boolean> canVoteHashMap;

    public void canVote(Nif nif) throws NotEnabledException, ConnectException {
        boolean hola= canVoteHashMap.containsKey(nif);
        if (!hola) throw new NotEnabledException("ERROR: It does not exist in this electoral College");
        if (!canVoteHashMap.get(nif)) throw new NotEnabledException("ERROR: User already voted");
    }

    public void disableVoter(Nif nif) throws ConnectException {
        canVoteHashMap.replace(nif, false);
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

    //Scrutiny methods
    private Map<VotingOption, Integer> voteCounts;
    private int nullVotes;
    private int blankVotes;
    private int totalVotes;

    @Override
    public void initVoteCount(List<VotingOption> validParties) {
        voteCounts = new HashMap<>();
        for (VotingOption party : validParties) {
            voteCounts.put(party, 0);
        }
        nullVotes = 0;
        blankVotes = 0;
        totalVotes = 0;
    }

    @Override
    public void scrutinize(VotingOption vopt) {
        if (vopt == null) {
            nullVotes++;
        } else if (isBlank(vopt)) {
            blankVotes++;
        } else {
            voteCounts.put(vopt, voteCounts.get(vopt) + 1);
        }
        totalVotes++;
    }

    private boolean isBlank(VotingOption vopt) {
        return (vopt.equals('b'));
    }

    @Override
    public int getVotesFor(VotingOption vopt) {
        return voteCounts.get(vopt);
    }

    @Override
    public int getTotal() {
        return totalVotes;
    }

    @Override
    public int getNulls() {
        return nullVotes;
    }

    @Override
    public int getBlanks() {
        return blankVotes;
    }

    @Override
    public void getScrutinyResults() {
        System.out.println("Results of the Scrutiny:");
        System.out.println("Total Votes: " + totalVotes);
        for (Map.Entry<VotingOption, Integer> entry : voteCounts.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue() + " votes");
        }
        System.out.println("Blank Votes: " + blankVotes);
        System.out.println("Null Votes: " + nullVotes);
    }




    // VotingKiosk Methods
    public void initVoting() {
        List<VotingOption> validParties = new ArrayList<>();
        validParties = retrieveVotingOptions();
        initVoteCount(validParties);
    }

    public void setDocument(char opt) {
        char selectedDocument = opt;
    }

    public void enterAccount(String login, Password pssw) throws InvalidAccountException {
        verifyAccount(login, pssw);
        String loggedInUser = login;
    }

    public void confirmIdentif(char conf) throws InvalidDNIDocumException {
        //Y for Yes; N for No
        if (conf != 'Y') {
            throw new InvalidDNIDocumException("Invalid document");
        }
    }

    public void enterNif(Nif nif) throws NotEnabledException, ConnectException {
        canVote(nif);
    }

    public void initOptionsNavigation() {
        List<VotingOption> votingOptions = retrieveVotingOptions();
        displayVotingOptions(votingOptions);
    }


    public void consultVotingOption(VotingOption vopt) {
        String optionInformation = retrieveOptionInformation(vopt);
        displayOptionInformation(optionInformation);
    }

    public void vote() {
        VotingOption selectedOption = getSelectedOption();
        scrutinize(selectedOption);
    }

    public void confirmVotingOption(char conf) throws ConnectException {
        //Y for Yes; N for No
        if (conf != 'Y') {
            throw new ConnectException("Connect error");
        }
    }

    private void finalizeSession() {
        System.out.println("Voting session finalized. Returning to the initial screen.");
    }





    // Setters
    public void setVoteCounts(Map<VotingOption, Integer> voteCounts) {
        this.voteCounts = voteCounts;
    }

    public void setTotalVotes(int totalVotes) {
        this.totalVotes = totalVotes;
    }

    public void setNullVotes(int nullVotes) {
        this.nullVotes = nullVotes;
    }

    public void setBlankVotes(int blankVotes) {
        this.blankVotes = blankVotes;
    }




    //Additional Methods
    private List<VotingOption> retrieveVotingOptions() {
        List<VotingOption> votingOptions = new ArrayList<>();
        return votingOptions;
    }

    private void displayVotingOptions(List<VotingOption> votingOptions) {
        System.out.println("Available Voting Options:");
        for (VotingOption option : votingOptions) {
            System.out.println(option.getParty());
        }
    }

    private String retrieveOptionInformation(VotingOption vopt) {
        return "Information for " + vopt.getParty();
    }

    private void displayOptionInformation(String optionInformation) {
        System.out.println(optionInformation);
    }

    private VotingOption getSelectedOption() {
        List<VotingOption> votingOptions = retrieveVotingOptions();
        return votingOptions.isEmpty() ? null : votingOptions.get(0);
    }
}
