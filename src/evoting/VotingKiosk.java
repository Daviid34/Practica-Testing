package evoting;

import data.Nif;
import data.Password;
import data.VotingOption;
import exceptions.*;
import mocks.PartyListServer;
import services.Scrutiny;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;

public class VotingKiosk implements Scrutiny {

    public VotingKiosk() {
    }
    /*
------------------------------------------SCRUTINITY--------------------------------------------------------------
 */


    public void initVoteCount(List<VotingOption> validParties) {
        //partyVotesHashmap = new HashMap<>();
        if (validParties != null) {
            for (VotingOption party : validParties) {
                //partyVotesHashmap.put(party, 0);
            }
        }
    }
    int nulls = 0;
    int blanks = 0;

    public void scrutinize(VotingOption vopt) {
        if (vopt == null) {
            blanks++;
        } //else if (vopt.getParty() == null || !partyVotesHashmap.containsKey(vopt)) {
            nulls++;
        //} else {
            //int votes = partyVotesHashmap.get(vopt);
            //partyVotesHashmap.put(vopt, votes + 1);
        //}
    }

    public int getVotesFor(VotingOption vopt) {
        //return partyVotesHashmap.get(vopt);
        return 1;
    }

    public int getTotal() {
        int count = 0;
        //for (VotingOption party : partyVotesHashmap.keySet()) {
            //count += partyVotesHashmap.get(party);
       // }
        return count + blanks;
    }

    public int getNulls() {
        return nulls;
    }

    public int getBlanks() {
        return blanks;
    }

    public void getScrutinyResults() {
        //for (VotingOption party : partyVotesHashmap.keySet()) {
            //System.out.print(party.toString() + ": " + partyVotesHashmap.get(party) + "\n");
        //}
        System.out.print("Blank votes: " + blanks + "\n");
        System.out.print("Null votes: " + nulls + "\n");
    }
/*
----------------------------------------------EASE-SCRUTINY-RESULTS-----------------------------------------------------
*/
    //To be transoformed into a mock
    public String ScrutinyResultsToString() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        getScrutinyResults();

        System.setOut(originalOut);
        return outputStream.toString();
    }
/*
------------------------------------------CLASS-METHODS-----------------------------------------------------------------
 */
    public void initVoting() {
        System.out.println("\rInitializing... ");
        System.out.println("Choose an identificative document:\nNIF -> n \nPassport -> p\n");
        opt = 'a';
    }

    char opt;
    public void setDocument(char c) throws ProceduralException {
        //Check if initVoting was called early
        if (opt != 'a') {
            throw new ProceduralException("ERROR: initVoting wasn't called earlier");
        }
        opt = c;
        //In case the NIF option was chosen
        if (c == 'n') System.out.println("Support staff is required");

    }

    public void enterAccount (String login, Password pssw) throws InvalidAccountException, ProceduralException {
        //Check that setDocument was called early
        if (opt != 'n') throw new  ProceduralException("ERROR: setDocument wasn't called earlier");
        //verifyAccount(login, pssw);
        System.out.println("The authentication was succesful");
        conf = 'f';
    }
    char conf = ' ';
    public void confirmIdentif(char conf) throws InvalidDNIDocumException, ProceduralException {
        if (this.conf != 'f') throw new ProceduralException("ERROR: enterAccount wasn't called earlier");
        this.conf = conf;
        if (conf == 'f') throw new InvalidDNIDocumException("ERROR: invalid DNI documentation");
        System.out.println("Enter the NIF");
    }

    Nif voter;
    public void enterNif(Nif nif) throws NotEnabledException, ConnectException, ProceduralException {
        if (conf != 'v') throw new ProceduralException("ERROR: confirmIdentif wasn't called earlier");
        nif.isValidNifFormat();
        //canVote(nif);
        voter = nif;
        System.out.println("Verification of vote OK, you may now initialize the option navigation");
    }

    PartyListServer partyListServer;
    List<VotingOption> parties;
    public void initOptionsNavigation() throws ProceduralException {
        if(voter == null) throw new ProceduralException("ERROR: enterNif wasn't called earlier");
        partyListServer = new PartyListServer();
        parties = partyListServer.getList();
        initVoteCount(parties);
        showParties(parties);
        partyChosed = null;
    }

    VotingOption partyChosed;
    public void consultVotingOption(VotingOption vopt) throws ProceduralException {
        if (partyListServer == null) throw new ProceduralException("ERROR: initOptionsNavigation wasn't called earlier");
        System.out.println();
        System.out.println("Information of the party specified:");
        System.out.println(vopt);
        partyChosed = vopt;
    }
    VotingOption party;
    public void vote() throws ProceduralException {
        if (partyChosed == null) throw new ProceduralException("ERROR: consultVotingOption wasn't called earlier");
        party = partyChosed;
        System.out.println("Please confirm that your Voting Option is indeed: " + party.getParty());
    }

    public void confirmVotingOption (char conf) throws ProceduralException, ConnectException {
        if (party == null) throw new ProceduralException("ERROR: vote wasn't called earlier");
        if (conf == 'f') party = null;
        if (conf == 'v') {
            System.out.println("\nVote confirmed!");
            scrutinize(party);
            //disableVoter(voter);
        }
    }
/*
----------------------------------------------ADDITIONAL-METHODS--------------------------------------------------------
 */
    public void showParties(List<VotingOption> parties) {
        System.out.println();
        System.out.println("Here you have the list of able parties to vote");
        for (VotingOption party : parties) {
            System.out.println(party.toString());
        }
    }


    public List<VotingOption> getParties() {
        return parties;
    }
}


