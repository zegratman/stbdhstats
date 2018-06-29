package fr.stb.stats.module;

public class ModuleUtils {

    public static Integer safeStat(Integer stat) {
        return stat != null ? stat : 0;
    }

}
