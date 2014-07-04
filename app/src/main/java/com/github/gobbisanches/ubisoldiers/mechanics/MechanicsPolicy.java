package com.github.gobbisanches.ubisoldiers.mechanics;

/**
 * Created by Sanches on 29/06/2014.
 */
public interface MechanicsPolicy {
    public Integer calculateHitPointsForUnit(Unit unit);
    public Integer calculateDamage(Unit attacker, Unit defender);
}
