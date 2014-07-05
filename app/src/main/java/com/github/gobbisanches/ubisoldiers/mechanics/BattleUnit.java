package com.github.gobbisanches.ubisoldiers.mechanics;

/**
 * Created by sanches on 7/4/14.
 */
public class BattleUnit {
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
        hp -= damage;
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public boolean isDead() {
        return !isAlive();
    }

    private int calculateHitPointsForUnit() {
        return PolicyManager.getDefaultRules().calculateHitPointsForUnit(unit);
    }

    // helper method - a shortcut to avoid feature envy
    public String getSoldierName() {
        return getUnit().getSoldier().getName();
    }
}

