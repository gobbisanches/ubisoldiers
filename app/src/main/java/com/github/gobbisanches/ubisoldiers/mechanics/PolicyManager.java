package com.github.gobbisanches.ubisoldiers.mechanics;

/**
 * Created by Sanches on 29/06/2014.
 */
// Design Pattern: Singleton
public final class PolicyManager {
    private final static PolicyManager INSTANCE = new PolicyManager();
    private GameRules gameRules = new DefaultGameRules();

    private PolicyManager() {
    }

    private static final PolicyManager getInstance() {
        return INSTANCE;
    }

    public static GameRules getDefaultRules() {
        return getInstance().gameRules;
    }

    public static void setGameRules(GameRules gameRules) {
        getInstance().gameRules = gameRules;
    }
}
