package evotingTest;

import data.VotingOption;
import evoting.VotingKiosk;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import data.Nif;
import data.Password;
import data.VotingOption;
import exceptions.ConnectException;
import exceptions.InvalidAccountException;
import exceptions.InvalidDNIDocumException;
import exceptions.NotEnabledException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class VotingKioskSuccessTest {

    private static VotingKiosk votingKiosk;
    private static ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        votingKiosk = new VotingKiosk();
        outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
    }

    @AfterEach
    public void tearDown() {
        System.setOut(System.out);
        outputStream = null;
    }

    @Test
    public void testInitOptionNavigation() {
        ArrayList<VotingOption> votingOptions = votingKiosk.getValidOptions();
        votingOptions.add(new VotingOption("PP"));
        votingOptions.add(new VotingOption("PSOE"));
        votingOptions.add(new VotingOption("Podemos"));
        votingKiosk.initOptionsNavigation();
        assertEquals("Available Voting Options:\r\nPP\r\nPSOE\r\nPodemos", outputStream.toString().trim());
    }

    @Test
    public void testConsultVotingOption() {
        VotingOption votingOption = new VotingOption("PartyA");
        String expectedOutput = "Information for PartyA";
        votingKiosk.consultVotingOption(votingOption);
        assertEquals(expectedOutput, outputStream.toString().trim());
    }

    @Test
    public void testVote() {
        VotingOption votingOption = new VotingOption("PP");
        votingKiosk.scrutinize(votingOption);
        assertEquals(1, votingKiosk.getVotesFor(votingOption));
        assertEquals(1, votingKiosk.getTotal());
        votingKiosk.scrutinize(votingOption);
        votingKiosk.scrutinize(votingOption);
        votingKiosk.scrutinize(votingOption);
        assertEquals(4, votingKiosk.getVotesFor(votingOption));
        assertEquals(4, votingKiosk.getTotal());
    }

    @Test
    public void testConfirmVotingOption() throws ConnectException {
        char confirmation = 'Y';
        votingKiosk.confirmVotingOption(confirmation);
    }

    @Test
    public void testFinalizeSession() {
        votingKiosk.finalizeSession();
        assertEquals("Voting session finalized. Returning to the initial screen.", outputStream.toString().trim());
    }


}
