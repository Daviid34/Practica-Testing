package evotingTest.interfaces;

import data.Nif;
import data.Password;
import data.VotingOption;
import evoting.VotingKiosk;
import exceptions.InvalidAccountException;
import exceptions.InvalidFormatException;
import exceptions.NullPasswordException;
import exceptions.ProceduralException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public interface VotingKioskTest {
    @Test
    void setDocumentTest() ;

    @Test
    void enterAccountTest();

    @Test
    void confirmIdentifTest() throws ProceduralException;

    @Test
    void enterNifTest() throws ProceduralException, InvalidFormatException, NullPasswordException, InvalidAccountException;

    @Test
    void initOptionsNavigationTest() throws ProceduralException;

    @Test
    void consultVotingOptionTest();

    @Test
    void voteTest();

    @Test
    void confirmVotingOptionTest();
}
