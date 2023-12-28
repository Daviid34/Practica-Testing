package evoting;

import data.Nif;
import data.Password;
import data.VotingOption;
import exceptions.*;
import mocks.PartyListServer;
import services.ElectoralOrganism;
import services.LocalService;
import services.Scrutiny;

import java.util.List;

public class VotingKiosk {
    Scrutiny scrutiny;
    LocalService localService;
    ElectoralOrganism electoralOrganism;

    public VotingKiosk(Scrutiny scrutiny, LocalService localService, ElectoralOrganism electoralOrganism) {
        this.scrutiny = scrutiny;
        this.localService = localService;
        this.electoralOrganism = electoralOrganism;
    }

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
        localService.verifyAccount(login, pssw);
        System.out.println("The authentication was succesful");
        conf = 'f';
    }
    char conf = ' ';
    public void confirmIdentif(char conf) throws InvalidDNIDocumException, ProceduralException {
        //Check that enterAccount was called early
        if (this.conf != 'f') throw new ProceduralException("ERROR: enterAccount wasn't called earlier");
        this.conf = conf;
        if (conf == 'f') throw new InvalidDNIDocumException("ERROR: invalid DNI documentation");
        System.out.println("Enter the NIF");
    }

    Nif voter;
    public void enterNif(Nif nif) throws NotEnabledException, ConnectException, ProceduralException {
        //Check that confirmIdentif was called early
        if (conf != 'v') throw new ProceduralException("ERROR: confirmIdentif wasn't called earlier");
        nif.isValidNifFormat();
        electoralOrganism.canVote(nif);
        voter = nif;
        System.out.println("Verification of vote OK, you may now initialize the option navigation");
    }

    PartyListServer partyListServer;
    List<VotingOption> parties;
    public void initOptionsNavigation() throws ProceduralException {
        //Check that enterNif was called early
        if(voter == null) throw new ProceduralException("ERROR: enterNif wasn't called earlier");
        partyListServer = new PartyListServer();
        parties = partyListServer.getList();
        scrutiny.initVoteCount(parties);
        showParties(parties);
        partyChosed = null;
    }

    VotingOption partyChosed;
    public void consultVotingOption(VotingOption vopt) throws ProceduralException {
        //Check that initOptionsNavigation was called early
        if (partyListServer == null) throw new ProceduralException("ERROR: initOptionsNavigation wasn't called earlier");
        System.out.println();
        System.out.println("Information of the party specified:");
        System.out.println(vopt);
        partyChosed = vopt;
    }
    VotingOption party;
    public void vote() throws ProceduralException {
        //Check that consultOption was called early
        if (partyChosed == null) throw new ProceduralException("ERROR: consultVotingOption wasn't called earlier");
        party = partyChosed;
        System.out.println("Please confirm that your Voting Option is indeed: " + party.getParty());
    }

    public void confirmVotingOption (char conf) throws ProceduralException, ConnectException {
        //Check that vote was called early
        if (party == null) throw new ProceduralException("ERROR: vote wasn't called earlier");
        if (conf == 'f') party = null;
        if (conf == 'v') {
            System.out.println("\nVote confirmed!");
            scrutiny.scrutinize(party);
            electoralOrganism.disableVoter(voter);
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


