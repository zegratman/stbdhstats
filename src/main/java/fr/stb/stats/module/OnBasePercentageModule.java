package fr.stb.stats.module;

import fr.stb.stats.model.BaseballStat;

import java.util.Map;

public class OnBasePercentageModule implements StatModule<Float> {

    private static final String NAME = "OBP";

    @Override
    public String getStatId() {
        return NAME;
    }

    @Override
    public Float getStat(Map<BaseballStat, Integer> initStats) {
        Integer h = initStats.get(BaseballStat.H);
        Integer bb = initStats.get(BaseballStat.BB);
        Integer hp = initStats.get(BaseballStat.HP);
        Integer ab = initStats.get(BaseballStat.AB);
        Integer sf = initStats.get(BaseballStat.SF);

        Integer num = h + bb + hp;
        Integer div = ab + bb + hp + sf;

        if (div == 0) return Float.NaN;

        return num.floatValue() / div.floatValue();
    }
}
