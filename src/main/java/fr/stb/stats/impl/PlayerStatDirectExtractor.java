package fr.stb.stats.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.stb.stats.model.BaseballStat;
import fr.stb.stats.model.PlayerName;
import fr.stb.stats.model.PlayerStatExtractor;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

public class PlayerStatDirectExtractor implements PlayerStatExtractor {

    private static final Logger LOGGER = Logger.getLogger(PlayerStatDirectExtractor.class.getCanonicalName());

    private static final Set<String> AVAILABLE_STATS;

    static {
        AVAILABLE_STATS = new HashSet<>();
        for (BaseballStat value : BaseballStat.values()) {
            AVAILABLE_STATS.add(value.name().toLowerCase());
        }
    }

    private PlayerName name;

    private Map<BaseballStat, Integer> playerStats;

    public PlayerStatDirectExtractor(PlayerName name, File statFolder) {
        playerStats = new HashMap<>();
        this.name = name;
        ObjectMapper mapper = new ObjectMapper();
        if (statFolder.isDirectory()) {
            for (File file : statFolder.listFiles()) {
                if (file.isFile() && file.canRead() && file.getName().endsWith(".json")) {
                    try {
                        JsonNode root = mapper.readTree(file);
                        String type = root.get("type").asText();
                        if (type.equals("stat")) {
                            LOGGER.info("Reading stat from " + file.getName());
                            readStatFromFile(root);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private void readStatFromFile(JsonNode root) {
        root.get("players").iterator().forEachRemaining(
                playerNode -> {
                    PlayerName playerName = new PlayerName(playerNode.get("first-name").asText(), playerNode.get("last-name").asText());
                    if (playerName.equals(this.name)) {
                        LOGGER.info(String.format("Updating stat for player %s", playerName));
                        playerNode.fields().forEachRemaining(
                                statNode -> {
                                    String keyStat = statNode.getKey();
                                    if (AVAILABLE_STATS.contains(statNode.getKey())) {
                                        BaseballStat baseballStat = BaseballStat.valueOf(keyStat.toUpperCase());
                                        Integer oldVal = playerStats.containsKey(baseballStat) ? playerStats.get(baseballStat) : 0;
                                        Integer newVal = oldVal + statNode.getValue().asInt();
                                        playerStats.put(baseballStat, newVal);
                                        LOGGER.info(String.format("Updating stat %s to %d", keyStat, newVal));
                                    }
                                }
                        );
                    }
                }
        );
    }

    @Override
    public PlayerName getPlayerName() {
        return this.name;
    }

    @Override
    public Integer getPlayerStat(BaseballStat baseballStat) {
        return playerStats.get(baseballStat);
    }
}
