package fr.stb.stats.export;

import fr.stb.stats.model.PlayerStat;
import fr.stb.stats.module.StatModule;

import java.io.Closeable;
import java.io.File;
import java.util.List;

public interface FileExporter extends Closeable {

    File getExportFile();

    void export(List<PlayerStat> playerStats);

    void addStatModule(StatModule statModule);

}
