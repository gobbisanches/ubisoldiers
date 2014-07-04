package com.github.gobbisanches.ubisoldiers.mechanics;

/**
 * Created by Sanches on 29/06/2014.
 */
public class Armor {
    String name;
    double defense;
    public final static Armor NONE = new Armor("NONE", 0);

    public Armor(String name, double defense) {
        this.name = name;
        this.defense = defense;
    }

    public Armor(Armor other) {
        this(other.getName(), other.getDefense());
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
