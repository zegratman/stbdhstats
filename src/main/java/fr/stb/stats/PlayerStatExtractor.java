package fr.stb.stats;

import fr.stb.stats.model.BaseballStat;
import fr.stb.stats.model.PlayerName;

public interface PlayerStatExtractor {

    PlayerName getPlayerName();

    Integer getPlayerStat(BaseballStat baseballStat);

}
