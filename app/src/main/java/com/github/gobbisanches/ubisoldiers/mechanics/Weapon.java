package com.github.gobbisanches.ubisoldiers.mechanics;

/**
 * Created by Sanches on 29/06/2014.
 */
public class Weapon {
    String name;
    double attack;
    public final static Weapon NONE = new Weapon("NONE", 0);

    public Weapon(String name, double attack) {
        this.name = name;
        this.attack = attack;
    }

    public Weapon(Weapon other) {
        this(other.getName(), other.getAttack());
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
