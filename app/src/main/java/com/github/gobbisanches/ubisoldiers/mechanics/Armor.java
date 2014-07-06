package com.github.gobbisanches.ubisoldiers.mechanics;

/**
 * Created by Sanches on 29/06/2014.
 */
public class Armor extends Item {
    String name;
    double defense;
    public final static Armor NONE = new Armor(Item.Rarity.Common, "NONE", 0);

    public Armor(Item.Rarity rarity, String name, double defense) {
        super(rarity);
        this.name = name;
        this.defense = defense;
    }

    public Armor(Armor other) {
        this(other.getRarity(), other.getName(), other.getDefense());
    }


    public String getName() {
        return name;
    }

    public double getDefense() {
        return defense;
    }

    @Override
    public String toString() {
        return "Armor{" +
                "name='" + name + '\'' +
                ", defense=" + defense +
                '}';
    }
}
