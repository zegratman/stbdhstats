package fr.stb.stats.model;

import java.util.Map;

public class PlayerStat {

    private PlayerName playerName;

    private Map<BaseballStat, Integer> initStats;

    public PlayerStat(PlayerName playerName, Map<BaseballStat, Integer> initStats) {
        this.playerName = playerName;
        this.initStats = initStats;
    }

    public PlayerName getPlayerName() {
        return playerName;
    }

    public Map<BaseballStat, Integer> getInitStats() {
        return initStats;
    }

    @Override
    public String toString() {
        return "PlayerStat{" +
                "playerName=" + playerName +
                ", initStats=" + initStats +
                '}';
    }

}
