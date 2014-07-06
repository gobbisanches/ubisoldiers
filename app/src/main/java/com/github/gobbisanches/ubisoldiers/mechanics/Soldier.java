package com.github.gobbisanches.ubisoldiers.mechanics;

/**
 * Created by Sanches on 29/06/2014.
 */
public class Soldier extends Item{
    String name;
    double attack;
    double defense;

    public Soldier(Item.Rarity rarity, String name, double attack, double defense) {
        super(rarity);
        this.name = name;
        this.attack = attack;
        this.defense = defense;
    }

    public Soldier(Soldier other) {
        this(other.getRarity(), other.getName(), other.getAttack(), other.getDefense());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAttack() {
        return attack;
    }

    public void setAttack(double attack) {
        this.attack = attack;
    }

    public double getDefense() {
        return defense;
    }

    public void setDefense(double defense) {
        this.defense = defense;
    }

    @Override
    public String toString() {
        return "Soldier{" +
                "name='" + name + '\'' +
                ", attack=" + attack +
                ", defense=" + defense +
                '}';
    }
}
