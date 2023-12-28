package mocks;

import data.VotingOption;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//The purpose of this class is to provide a server of different lists of party options
public class PartyListServer {
    List<List<VotingOption>> server;
    Random random;

    public PartyListServer() {
        server = new ArrayList<>();
        random = new Random();
        initServer();
    }

    public List<VotingOption> getList() {
        int seed = random.nextInt(5);
        return server.get(seed);
    }

    //Provides 5 different Lists of VotingOptions
    private void initServer() {
        String partyName = "Party";
        List<VotingOption> list1 = new ArrayList<>();
        for (int i = 1; i < 11; i++) {
            list1.add(new VotingOption(partyName+i));
        }
        server.add(list1);

        List<VotingOption> list2 = new ArrayList<>();
        for (int i = 65; i < 75; i++) {
            list2.add(new VotingOption(partyName+(char)i));
        }
        server.add(list2);

        List<VotingOption> list3 = new ArrayList<>();
        list3.add(new VotingOption("PP"));
        list3.add(new VotingOption("PSOE"));
        list3.add(new VotingOption("SUMAR"));
        list3.add(new VotingOption("VOX"));
        list3.add(new VotingOption("ERC"));
        list3.add(new VotingOption("UPN"));
        list3.add(new VotingOption("PSC"));
        server.add(list3);

        List<VotingOption> list4 = new ArrayList<>();
        list4.add(new VotingOption("SPD"));
        list4.add(new VotingOption("CDU"));
        list4.add(new VotingOption("CSU"));
        list4.add(new VotingOption("AfD"));
        list4.add(new VotingOption("GRÜNE"));
        server.add(list4);

        List<VotingOption> list5 = new ArrayList<>();
        list5.add(new VotingOption("Ciutadans"));
        list5.add(new VotingOption("ERC"));
        list5.add(new VotingOption("JxCat"));
        list5.add(new VotingOption("PSC"));
        list5.add(new VotingOption("CUP"));
        server.add(list5);
    }
}
