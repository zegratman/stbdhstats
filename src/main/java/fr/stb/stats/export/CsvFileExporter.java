package fr.stb.stats.export;

import fr.stb.stats.model.BaseballStat;
import fr.stb.stats.model.PlayerStat;
import fr.stb.stats.module.StatModule;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.logging.Logger;

public class CsvFileExporter implements FileExporter {

    private static final Logger LOGGER = Logger.getLogger(CsvFileExporter.class.getCanonicalName());

    private File file;

    private FileOutputStream fileOutputStream;

    private List<StatModule> modules;

    public CsvFileExporter(File file) {
        this.file = file;
        this.modules = new ArrayList<>();
        try {
            this.fileOutputStream = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public File getExportFile() {
        return file;
    }

    @Override
    public void export(List<PlayerStat> playerStats) {
        boolean headerDone = false;
        for (PlayerStat playerStat : playerStats) {

            Set<BaseballStat> keys = new HashSet<>();
            Arrays.stream(BaseballStat.values()).forEach(baseballStat -> keys.add(baseballStat));

            // Creating header
            if (!headerDone) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("PLAYER");
                keys.forEach(key -> {stringBuilder.append(","+key.name());});
                this.modules.forEach(statModule -> stringBuilder.append("," + statModule.getStatId()));
                stringBuilder.append("\n");
                try {
                    this.fileOutputStream.write(stringBuilder.toString().getBytes("UTF-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                headerDone = true;
            }

            // writing player stats
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(playerStat.getPlayerName().asString());
            keys.forEach(baseballStat -> stringBuilder.append("," + String.valueOf(playerStat.getInitStats().get(baseballStat))));
            this.modules.forEach(statModule -> stringBuilder.append("," + statModule.getStat(playerStat.getInitStats())));
            stringBuilder.append("\n");
            try {
                this.fileOutputStream.write(stringBuilder.toString().getBytes("UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        LOGGER.info("Export to CSV ends.");
    }

    @Override
    public void addStatModule(StatModule statModule) {
        modules.add(statModule);
    }

    @Override
    public void close() throws IOException {
        this.fileOutputStream.close();
    }
}
