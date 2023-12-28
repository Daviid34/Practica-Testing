package servicesTest.scrutinyTest;

import data.VotingOption;
import servicesTest.interfaces.ScrutinyTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.Scrutiny;
import services.ScrutinyImpl;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScrutinyBasicTest implements ScrutinyTest {
    private Scrutiny server;
    private VotingOption joker;

    @BeforeEach
    void init() throws InterruptedException {
        List<VotingOption> parties = new ArrayList<>();
        joker = new VotingOption("PartyA");
        parties.add(joker);
        parties.add(new VotingOption("PartyB"));
        parties.add(new VotingOption("PartyC"));
        parties.add(new VotingOption("PartyD"));

        server = new ScrutinyImpl();
        server.initVoteCount(parties);
    }

    @Test
    void scrutinizeTest() {
        server.scrutinize(joker);
        assertEquals(1, server.getVotesFor(joker));
    }

    @Test
    public void blankVoteTest() {
        server.scrutinize(null);
        assertEquals(1, server.getBlanks());
    }

    @Test
    public void nullVoteTest() {
        server.scrutinize(new VotingOption("Party123"));
        assertEquals(1, server.getNulls());
    }

    @Test
    void getTotalTest() {
        server.scrutinize(joker);
        server.scrutinize(null);
        server.scrutinize(new VotingOption("Party123"));
        assertEquals(2, server.getTotal());
    }

    @Test
    public void blankVotingOptionTest() {
        server.scrutinize(new VotingOption(null));
        assertEquals(1, server.getNulls());
    }

    @Test
    void getResultsTest() {
        server.scrutinize(joker);
        server.scrutinize(joker);
        server.scrutinize(new VotingOption("PartyC"));
        server.scrutinize(null);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        server.getScrutinyResults();

        System.setOut(originalOut);
        String printedContent = outputStream.toString();
        String expectedOutput = """
                Vote option {party='PartyA'}: 2
                Vote option {party='PartyB'}: 0
                Vote option {party='PartyC'}: 1
                Vote option {party='PartyD'}: 0
                Blank votes: 1
                Null votes: 0
                """;
        assertEquals(expectedOutput, printedContent);
    }

}
