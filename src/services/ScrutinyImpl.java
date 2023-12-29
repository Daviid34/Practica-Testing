package services;

import data.VotingOption;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;

public class ScrutinyImpl implements Scrutiny{
    HashMap<VotingOption, Integer> partyVotesHashmap;

    @Override
    public void initVoteCount(List<VotingOption> validParties) {
        partyVotesHashmap = new HashMap<>();
        if (validParties != null) {
            for (VotingOption party : validParties) {
                partyVotesHashmap.put(party, 0);
            }
        }
    }
    int nulls = 0;
    int blanks = 0;

    public void scrutinize(VotingOption vopt) {
        if (vopt == null) {
            blanks++;
        } else if (vopt.getParty() == null || !partyVotesHashmap.containsKey(vopt)) {
        nulls++;
        } else {
        int votes = partyVotesHashmap.get(vopt);
        partyVotesHashmap.put(vopt, votes + 1);
        }
    }

    public int getVotesFor(VotingOption vopt) {
        return partyVotesHashmap.get(vopt);
    }

    public int getTotal() {
        int count = 0;
        for (VotingOption party : partyVotesHashmap.keySet()) {
        count += partyVotesHashmap.get(party);
        }
        return count + blanks;
    }

    public int getNulls() {
        return nulls;
    }

    public int getBlanks() {
        return blanks;
    }

    public void getScrutinyResults() {
        for (VotingOption party : partyVotesHashmap.keySet()) {
        System.out.print(party.toString() + ": " + partyVotesHashmap.get(party) + "\n");
        }
        System.out.print("Blank votes: " + blanks + "\n");
        System.out.print("Null votes: " + nulls + "\n");
    }

    public String ScrutinyResultsToString() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        getScrutinyResults();

        System.setOut(originalOut);
        return outputStream.toString();
    }
}
