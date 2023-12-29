package dataTest;

import data.VotingOption;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VotingOptionTest {
    VotingOption option;

    @BeforeEach
    void init() {
        option = new VotingOption("PartyA");
    }

    @Test
    public void testEqualsWithSameInstance() {
        assertTrue(option.equals(option));
    }


    @Test
    public void testEqualsWithEqualOptions() {
        VotingOption option2 = new VotingOption("PartyA");
        assertTrue(option.equals(option2));
    }

    @Test
    public void testEqualsWithDifferentOptions() {
        VotingOption option2 = new VotingOption("PartyB");
        assertFalse(option.equals(option2));
    }

    @Test
    public void testEqualsWithNull() {
        assertFalse(option.equals(null));
    }

    @Test
    public void testEqualsWithDifferentClass() {
        assertFalse(option.equals("PartyA"));
    }

    @Test
    public void testGetParty() {
        assertEquals("PartyA", option.getParty());
    }

    @Test
    public void testToString() {
        assertEquals("Vote option {party='PartyA'}", option.toString());
    }
}

