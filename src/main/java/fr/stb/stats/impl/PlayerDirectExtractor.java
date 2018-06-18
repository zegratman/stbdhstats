package fr.stb.stats.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.stb.stats.model.PlayerExtractor;
import fr.stb.stats.model.PlayerName;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class PlayerDirectExtractor implements PlayerExtractor {

    private static final Logger LOGGER = Logger.getLogger(PlayerDirectExtractor.class.getCanonicalName());

    String workDir;

    List<PlayerName> playerNames;

    public PlayerDirectExtractor(String workingDir) {
        this.workDir = workingDir;
    }

    @Override
    public List<PlayerName> getPlayerNames() {
        if (playerNames == null) {
            playerNames = extractPlayers();
        }
        return playerNames;
    }

    private List<PlayerName> extractPlayers() {
        List<PlayerName> playerNames = new ArrayList<>();
        File rootDir = new File(this.workDir);
        ObjectMapper mapper = new ObjectMapper();
        for (File jsonFile : rootDir.listFiles()) {
            if (jsonFile.isFile() && jsonFile.canRead() && jsonFile.getName().endsWith(".json")) {
                try {
                    JsonNode root = mapper.readTree(jsonFile);
                    String type = root.get("type").asText();
                    if (type.equals("stat")) {
                        LOGGER.info("Reading stat from " + jsonFile.getName());
                        readAndUpdateList(root, playerNames);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return playerNames;
    }

    private void readAndUpdateList(JsonNode root, List<PlayerName> playerNames) {
        root.get("players").elements().forEachRemaining(node -> {
            String firstName = node.get("first-name").asText();
            String lastName = node.get("last-name").asText();
            PlayerName playerName = new PlayerName(firstName, lastName);
            if (! playerNames.contains(playerName)) {
                LOGGER.info("Adding player : " + playerName.asString());
                playerNames.add(playerName);
            }
        });
    }
}
