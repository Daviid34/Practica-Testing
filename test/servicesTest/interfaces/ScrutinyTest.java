package servicesTest.interfaces;

import data.VotingOption;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public interface ScrutinyTest {
    @Test
    void blankVoteTest();

    @Test
    void nullVoteTest();

    @Test
    void blankVotingOptionTest();
}
