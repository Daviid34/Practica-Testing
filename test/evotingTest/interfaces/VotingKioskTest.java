package evotingTest.interfaces;

import data.Nif;
import data.Password;
import data.VotingOption;
import evoting.VotingKiosk;
import exceptions.InvalidAccountException;
import exceptions.InvalidDNIDocumException;
import exceptions.NotEnabledException;
import exceptions.ProceduralException;
import mocks.PartyListServer;

import java.net.ConnectException;

public interface VotingKioskTest {
    void setDocument(char c) throws ProceduralException;

    void enterAccount(String login, Password pssw) throws InvalidAccountException, ProceduralException;


    void confirmIdentif(char conf) throws InvalidDNIDocumException, ProceduralException;

    void enterNif(Nif nif) throws NotEnabledException, ConnectException, ProceduralException;

    void initOptionsNavigation() throws ProceduralException;


    void consultVotingOption(VotingOption vopt) throws ProceduralException;

    void vote() throws ProceduralException;

    void confirmVotingOption(char conf) throws ProceduralException, ConnectException;
}
