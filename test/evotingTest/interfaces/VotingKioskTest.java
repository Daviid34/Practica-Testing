package evotingTest.interfaces;

import data.Nif;
import data.Password;
import data.VotingOption;
import evoting.VotingKiosk;
import exceptions.*;
import org.junit.jupiter.api.Test;
import java.net.ConnectException;

import java.net.ConnectException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public interface VotingKioskTest {
    @Test
    void consultVotingOptionTest() throws ProceduralException, InvalidFormatException, NullPasswordException, InvalidAccountException, InvalidDNIDocumException, NullNifException, NotEnabledException, ConnectException;

    @Test
    void voteTest() throws ProceduralException, InvalidFormatException, InvalidDNIDocumException, NullPasswordException, InvalidAccountException, NullNifException, NotEnabledException, ConnectException;

    @Test
    void confirmVotingOptionTest() throws NullNifException, ProceduralException, InvalidFormatException, InvalidDNIDocumException, NotEnabledException, NullPasswordException, InvalidAccountException, ConnectException;
}
