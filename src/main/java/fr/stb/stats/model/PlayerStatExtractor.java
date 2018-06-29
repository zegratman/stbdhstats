package fr.stb.stats.model;

public interface PlayerStatExtractor {

    PlayerName getPlayerName();

    Integer getPlayerStat(BaseballStat baseballStat);

}
