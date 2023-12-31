package servicesTest;

import data.Nif;
import exceptions.InvalidFormatException;
import exceptions.NotEnabledException;
import exceptions.NullNifException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import services.ElectoralOrganism;
import services.ElectoralOrganismImpl;

import java.net.ConnectException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ElectoralOrganismTest {
    ElectoralOrganism server;

    @BeforeEach
    void init() throws NullNifException, InvalidFormatException {
        HashMap<Nif, Boolean> canVoteHashMap = new HashMap<>();
        canVoteHashMap.put(new Nif("12345678K"), true);
        server = new ElectoralOrganismImpl(canVoteHashMap);
    }

    @Test
    void validVoterTest() {
        assertDoesNotThrow(()-> {server.canVote(new Nif("12345678K"));});
    }

    @Test
    void nifNotExistsTest() {
        assertThrows(NotEnabledException.class, ()-> {server.canVote(new Nif("12345668K"));});
    }

    @Test
    void invalidVoterTest() throws NullNifException, InvalidFormatException, ConnectException {
        server.disableVoter(new Nif("12345678K"));
        assertThrows(NotEnabledException.class, () ->{server.canVote(new Nif("12345678K"));});
    }
}
