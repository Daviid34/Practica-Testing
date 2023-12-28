package evotingTest.scrutinyTest;

import data.VotingOption;
import evoting.VotingKiosk;
import evotingTest.interfaces.ScrutinyTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScrutinyBasicTest implements ScrutinyTest {
    private VotingKiosk server;
    private VotingOption joker;

    @BeforeEach
    void init() throws InterruptedException {
        List<VotingOption> parties = new ArrayList<>();
        joker = new VotingOption("PartyA");
        parties.add(joker);
        parties.add(new VotingOption("PartyB"));
        parties.add(new VotingOption("PartyC"));
        parties.add(new VotingOption("PartyD"));

        server = new VotingKiosk();
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

        String printedContent = server.ScrutinyResultsToString();
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
