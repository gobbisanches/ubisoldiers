package com.github.gobbisanches.ubisoldiers.mechanics;

/**
 * Created by Sanches on 29/06/2014.
 */
public class Weapon extends Item {
    public Weapon(int id, Rarity rarity, String name, double attack) {
        super(id, rarity, name, attack, 0);
    }

    public Weapon(Weapon other) {
        super(other);
    }
}
