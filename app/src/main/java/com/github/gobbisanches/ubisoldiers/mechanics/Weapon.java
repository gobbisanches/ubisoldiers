package com.github.gobbisanches.ubisoldiers.mechanics;

/**
 * Created by Sanches on 29/06/2014.
 */
public class Weapon extends Item {
    String name;
    double attack;
//    public final static Weapon NONE = new Weapon(Item.Rarity.Common, "NONE", 0);

    public Weapon(int id, Rarity rarity, String name, double attack) {
        super(id, rarity);
        this.name = name;
        this.attack = attack;
    }

    public Weapon(Weapon other) {
        this(other.getId(), other.getRarity(), other.getName(), other.getAttack());
    }

    public String getName() {
        return name;
    }

    public double getAttack() {
        return attack;
    }

    @Override
    public String toString() {
        return "Weapon{" +
                "name='" + name + '\'' +
                ", attack=" + attack +
                '}';
    }
}
