package fr.stb.stats;

import java.util.HashMap;
import java.util.Map;

public class Main {

    public static void main(String[] args) {

        CouchbaseConnector connector = new CouchbaseConnector("localhost", "statuser", "statuser");

        PlayerSeeker playerSeeker = new PlayerSeeker(connector.getBucket("stb-dh-2018"));

        Map<PlayerName, Map<BaseballStat, Integer>> allStats = new HashMap<>();

        playerSeeker.getPlayerNames().forEach(playerName -> {
            System.out.println(playerName.toString());
            allStats.put(playerName, new HashMap<>());
            PlayerStatConnector playerStatConnector = new PlayerStatConnector(playerName, connector.getBucket("stb-dh-2018"));
            for (BaseballStat baseballStat : BaseballStat.values()) {
                Integer stat = playerStatConnector.getPlayerStat(baseballStat);
                allStats.get(playerName).put(baseballStat, stat);
            }
        });

        System.out.println(allStats);

        connector.close();

    }

}
