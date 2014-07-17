package com.github.gobbisanches.ubisoldiers.mechanics;

import android.location.Location;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import static com.github.gobbisanches.ubisoldiers.mechanics.Item.Rarity.*;
import static java.lang.Math.floor;

/**
 * Created by Sanches on 29/06/2014.
 */
public class DefaultGameRules implements GameRules {
    private Location baseLocation;

    public DefaultGameRules() {
        baseLocation = new Location("");
        baseLocation.setLatitude(-15.76243);
        baseLocation.setLongitude(-47.87004);
    }

    @Override
    public Integer calculateHitPointsForUnit(Unit unit) {
        return ((int) floor(unit.getDefense() * 10));
    }

    @Override
    public Integer calculateDamage(Random random, Unit attacker, Unit defender) {
        double randomValue = random.nextDouble();
        int damage = (int)Math.floor(Math.max(attacker.getAttack() * (2+2* randomValue), 0));

        Log.d("UBISOLDIERS", "Damage = " + damage + ", with attack=" + attacker.getAttack() +
                ", with defense=" + defender.getDefense() +
                " and randomValue=" + randomValue);

        return new Integer(damage);
    }

    @Override
    public Integer calculateQualityOf(Item item) {
        int quality = 0;

        switch(item.getRarity()) {
            case Common: quality += 1000;
                break;
            case Uncommon: quality += 500;
                break;
            case Rare: quality += 300;
                break;
            case Epic: quality += 50;
                break;
            case Legendary: quality += 10;
                break;
            default: throw new RuntimeException("Invalid item rarity " + item.getRarity());
        }

        return new Integer(quality);
    }

    @Override
    public Double calculateSearchModifier(Location location, Long wifiStrength) {
        // modifier is a number between 0.6 and 1.4 (+/- 30%)

        // locationModifier is a number between -0.3 and 0.3 (+/- 30%)
        long distanceInKm = (long) Math.floor(Math.abs(location.distanceTo(baseLocation) / 1000));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(location.getTime());

        long dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        double locationComponent = distanceInKm + dayOfMonth;
        double locationModifier = (((locationComponent % 30) - 15)*2 / 100);

        double wifiComponent = ((((double)wifiStrength) / 500) - 0.1);
        double wifiModifier = Math.max(Math.min(wifiComponent, 0.1), -0.1);

        double modifier = Math.max(Math.min(1 + locationModifier + wifiModifier, 1.3), 0.7);

        Log.d("UBISOLDIERS", "[CALCULATE SEARCH MODIFIER] distance = " + distanceInKm);
        Log.d("UBISOLDIERS", "[CALCULATE SEARCH MODIFIER] wifiStrength = " + wifiStrength);
        Log.d("UBISOLDIERS", "[CALCULATE SEARCH MODIFIER] modifier = " + modifier);

        return new Double(modifier);
    }
}
;