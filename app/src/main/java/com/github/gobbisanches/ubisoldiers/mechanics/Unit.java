package com.github.gobbisanches.ubisoldiers.mechanics;

/**
 * Created by Sanches on 29/06/2014.
 */
public class Unit {
    Soldier soldier;
    Weapon weapon = Weapon.NONE;
    Armor armor = Armor.NONE;

    public Unit(Soldier soldier, Weapon weapon, Armor armor) {
        this.soldier = soldier;
        this.weapon = weapon;
        this.armor = armor;
    }

    public Unit(Unit other) {
        this(other.getSoldier(), other.getWeapon(), other.getArmor());
    }

    public Unit(Soldier soldier) {
        this.soldier = soldier;
    }

    public Soldier getSoldier() {
        return soldier;
    }

    public void setSoldier(Soldier soldier) {
        this.soldier = soldier;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public void setWeapon(Weapon weapon) {
        this.weapon = weapon;
    }

    public Armor getArmor() {
        return armor;
    }

    public void setArmor(Armor armor) {
        this.armor = armor;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "soldier=" + soldier +
                ", weapon=" + weapon +
                ", armor=" + armor +
                '}';
    }

    public double getAttack() {
        return soldier.getAttack() + weapon.getAttack();
    }

    public double getDefense() {
        return soldier.getDefense() + armor.getDefense();
    }
}
