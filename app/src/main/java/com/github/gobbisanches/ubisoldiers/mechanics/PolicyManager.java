package com.github.gobbisanches.ubisoldiers.mechanics;

/**
 * Created by Sanches on 29/06/2014.
 */
// Design Pattern: Singleton
public final class PolicyManager {
    private final static PolicyManager INSTANCE = new PolicyManager();
    private MechanicsPolicy mechanicsPolicy = new DefaultMechanicsPolicy();

    private PolicyManager() {
    }

    private static final PolicyManager getInstance() {
        return INSTANCE;
    }

    public static MechanicsPolicy getMechanicsPolicy() {
        return getInstance().mechanicsPolicy;
    }

    public static void setMechanicsPolicy(MechanicsPolicy mechanicsPolicy) {
        getInstance().mechanicsPolicy = mechanicsPolicy;
    }
}
