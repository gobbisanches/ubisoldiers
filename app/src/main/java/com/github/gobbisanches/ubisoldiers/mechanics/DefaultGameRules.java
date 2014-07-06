package com.github.gobbisanches.ubisoldiers.mechanics;

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
        return ((int) floor(unit.getDefense() * 5));
    }

    @Override
    public Integer calculateDamage(Random random, Unit attacker, Unit defender) {
        double damage = attacker.getAttack() * 4 - defender.getDefense() * 2;

        return new Integer((int) Math.floor(damage));
    }

    @Override
    public Integer calculateQualityOf(Item item) {
        int quality = 0;

        switch(item.getRarity()) {
            case Common: quality += 10;
                break;
            case Uncommon: quality += 100;
                break;
            case Rare: quality += 1000;
                break;
            default: throw new RuntimeException("Invalid item rarity" + item.getRarity());
        }

        if(item instanceof Soldier) { quality += 3; }
        else if (item instanceof Weapon) { quality += 2; }
        else if (item instanceof Armor) { quality += 1; }

        return new Integer(quality);
    }
}
