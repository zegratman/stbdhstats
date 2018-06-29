package fr.stb.stats.module;

import fr.stb.stats.model.BaseballStat;

import java.util.Map;

public class BattingAverageModule implements StatModule<Float> {

    private static final String NAME = "BA";

    @Override
    public String getStatId() {
        return NAME;
    }

    @Override
    public Float getStat(Map<BaseballStat, Integer> initStats) {
        Integer ab = ModuleUtils.safeStat(initStats.get(BaseballStat.AB));
        Integer h = ModuleUtils.safeStat(initStats.get(BaseballStat.H));
        if (ab == 0) return Float.NaN;
        return h.floatValue() / ab.floatValue();
    }
}
