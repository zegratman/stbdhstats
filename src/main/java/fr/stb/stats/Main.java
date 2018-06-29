package fr.stb.stats;

import fr.stb.stats.export.CsvFileExporter;
import fr.stb.stats.export.FileExporter;
import fr.stb.stats.impl.*;
import fr.stb.stats.model.BaseballStat;
import fr.stb.stats.model.PlayerExtractor;
import fr.stb.stats.model.PlayerStat;
import fr.stb.stats.model.PlayerStatExtractor;
import fr.stb.stats.module.BattingAverageModule;
import fr.stb.stats.module.OnBasePercentageModule;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Main {

    private static final Logger LOGGER = Logger.getLogger(Main.class.getCanonicalName());

    public static void main(String[] args) {

        PlayerExtractor playerExtractor = new PlayerDirectExtractor("src/main/resources");

        List<PlayerStat> playerStats = new ArrayList<>();

        File outFile = new File("stb-dh-2018.csv");

        playerExtractor.getPlayerNames().forEach(playerName -> {
            LOGGER.info(playerName.toString());
            Map<BaseballStat, Integer> allStats = new HashMap<>();
            PlayerStatExtractor playerStatConnector = new PlayerStatDirectExtractor(playerName, new File("src/main/resources"));
            for (BaseballStat baseballStat : BaseballStat.values()) {
                Integer stat = playerStatConnector.getPlayerStat(baseballStat);
                allStats.put(baseballStat, stat);
            }
            playerStats.add(new PlayerStat(playerName, allStats));
        });

        try {
            FileExporter fileExporter = new CsvFileExporter(outFile);
            fileExporter.addStatModule(new BattingAverageModule());
            fileExporter.addStatModule(new OnBasePercentageModule());
            fileExporter.export(playerStats);
            fileExporter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        LOGGER.info(playerStats.toString());

    }

}
