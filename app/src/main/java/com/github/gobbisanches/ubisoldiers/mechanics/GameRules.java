package com.github.gobbisanches.ubisoldiers.mechanics;

import android.location.Location;

import java.util.Random;

/**
 * Created by Sanches on 29/06/2014.
 */
public interface GameRules {
    public Integer calculateHitPointsForUnit(Unit unit);

    public Integer calculateDamage(Random random, Unit attacker, Unit defender);

    public Integer calculateQualityOf(Item item);

    public Double calculateSearchModifier(Location location, Long wifiStrength);
}
