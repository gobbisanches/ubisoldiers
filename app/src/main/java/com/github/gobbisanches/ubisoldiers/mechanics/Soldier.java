package com.github.gobbisanches.ubisoldiers.mechanics;

/**
 * Created by Sanches on 29/06/2014.
 */
public class Soldier extends Item {
    public Soldier(int id, Rarity rarity, String name, double attack, double defense) {
        super(id, rarity, name, attack, defense);
    }

    public Soldier(Soldier other) {
        super(other);
    }
}
