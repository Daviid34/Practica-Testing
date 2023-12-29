package evotingTest.VotingKioskEnterPointTest.interfaces;

import data.Nif;
import data.Password;
import data.VotingOption;
import exceptions.InvalidAccountException;
import exceptions.InvalidDNIDocumException;
import exceptions.NotEnabledException;
import exceptions.ProceduralException;

import java.net.ConnectException;

public interface VotingKioskTest {
    void setDocument() throws ProceduralException;

    void enterAccount() throws InvalidAccountException, ProceduralException;


    void confirmIdentif() throws InvalidDNIDocumException, ProceduralException;

    void enterNif() throws NotEnabledException, ConnectException, ProceduralException;

    void initOptionsNavigation() throws ProceduralException;


    void consultVotingOption() throws ProceduralException;

    void vote() throws ProceduralException;

    void confirmVotingOption() throws ProceduralException, ConnectException;
}
