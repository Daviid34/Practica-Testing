package evotingTest.scrutinyTest;

import data.VotingOption;
import evoting.VotingKiosk;
import evotingTest.interfaces.ScrutinyTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScrutinyNullTest implements ScrutinyTest {
    private VotingKiosk server;

    @BeforeEach
    void init() {
        server = new VotingKiosk();
        server.initVoteCount(null);
    }

    @Test
    public void blankVoteTest() {
        server.scrutinize(null);
        assertEquals(1, server.getBlanks());
    }

    @Test
    public void nullVoteTest() {
        server.scrutinize(new VotingOption("A"));
        assertEquals(1, server.getNulls());
    }

    @Test
    public void blankVotingOptionTest(){
        server.scrutinize(new VotingOption(null));
        assertEquals(1, server.getNulls());
    }
}
