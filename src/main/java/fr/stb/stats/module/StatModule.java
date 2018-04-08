package fr.stb.stats.module;

import fr.stb.stats.model.BaseballStat;

import java.util.Map;

public interface StatModule<A> {

    String getStatId();

    A getStat(Map<BaseballStat, Integer> initStats);

}
