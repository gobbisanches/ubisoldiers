package com.github.gobbisanches.ubisoldiers.mechanics;

import java.io.Serializable;

/**
 * Created by sanches on 7/4/14.
 */
public class BattleUnit implements Serializable {
    private static final long serialVersionUID = 1L;
    private Unit unit;
    private int hp;

    public BattleUnit(Unit unit) {
        this.unit = unit;
        this.hp = calculateHitPointsForUnit();
    }

    public Unit getUnit() {
        return unit;
    }

    public int getHp() {
        return hp;
    }

    public void takeDamage(int damage) {
        hp = Math.max(hp - damage, 0);
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public boolean isDead() {
        return !isAlive();
    }

    private int calculateHitPointsForUnit() {
        return PolicyManager.getRules().calculateHitPointsForUnit(unit);
    }

    // helper method - a shortcut to avoid feature envy
    public String getSoldierName() {
        return getUnit().getSoldier().getName();
    }
}

