package com.github.gobbisanches.ubisoldiers.mechanics;

import static java.lang.Math.*;

/**
 * Created by Sanches on 29/06/2014.
 */
public class DefaultMechanicsPolicy implements MechanicsPolicy {
    public DefaultMechanicsPolicy() {
    }

    @Override
    public Integer calculateHitPointsForUnit(Unit unit) {
        return ((int) floor(unit.getDefense() * 5));
    }

    @Override
    public Integer calculateDamage(Unit attacker, Unit defender) {
        double dmg = attacker.getAttack() * 4 - defender.getDefense() *2

        return new Integer((int)Math.floor(dmg)));
    }
}
