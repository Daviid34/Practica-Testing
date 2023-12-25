package dataTest;

import data.VotingOption;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VotingOptionTest {

    @Test
    public void testEqualsWithSameInstance() {
        VotingOption option = new VotingOption("PartyA");
        assertTrue(option.equals(option));
    }


    @Test
    public void testEqualsWithEqualOptions() {
        VotingOption option1 = new VotingOption("PartyA");
        VotingOption option2 = new VotingOption("PartyA");
        assertTrue(option1.equals(option2));
    }

    @Test
    public void testEqualsWithDifferentOptions() {
        VotingOption option1 = new VotingOption("PartyA");
        VotingOption option2 = new VotingOption("PartyB");
        assertFalse(option1.equals(option2));
    }

    @Test
    public void testEqualsWithNull() {
        VotingOption option = new VotingOption("PartyA");
        assertFalse(option.equals(null));
    }

    @Test
    public void testEqualsWithDifferentClass() {
        VotingOption option = new VotingOption("PartyA");
        assertFalse(option.equals("PartyA"));
    }

    @Test
    public void testGetParty() {
        VotingOption option = new VotingOption("PartyA");
        assertEquals("PartyA", option.getParty());
    }

    @Test
    public void testToString() {
        VotingOption option = new VotingOption("PartyA");
        assertEquals("Vote option {party='PartyA'}", option.toString());
    }
}

