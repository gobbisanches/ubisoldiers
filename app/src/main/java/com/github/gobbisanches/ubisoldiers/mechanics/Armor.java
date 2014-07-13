package com.github.gobbisanches.ubisoldiers.mechanics;

/**
 * Created by Sanches on 29/06/2014.
 */
public class Armor extends Item {
    public Armor(int id, Rarity rarity, String name, double defense) {
        super(id, rarity, name, 0, defense);
    }

    public Armor(Item other) {
        super(other);
    }

}
