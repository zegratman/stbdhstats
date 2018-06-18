package fr.stb.stats.model;

import fr.stb.stats.model.BaseballStat;
import fr.stb.stats.model.PlayerName;

public interface PlayerStatExtractor {

    PlayerName getPlayerName();

    Integer getPlayerStat(BaseballStat baseballStat);

}
