package com.github.gobbisanches.ubisoldiers.mechanics;

import android.util.Log;

import java.util.Random;

import static com.github.gobbisanches.ubisoldiers.mechanics.Item.Rarity.*;
import static java.lang.Math.floor;

/**
 * Created by Sanches on 29/06/2014.
 */
public class DefaultGameRules implements GameRules {
    public DefaultGameRules() {
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
            case Common: quality += 10;
                break;
            case Uncommon: quality += 100;
                break;
            case Rare: quality += 300;
                break;
            case Epic: quality += 500;
                break;
            case Legendary: quality += 1000;
                break;
            default: throw new RuntimeException("Invalid item rarity " + item.getRarity());
        }

        return new Integer(quality);
    }
}
