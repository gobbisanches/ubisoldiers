package com.github.gobbisanches.ubisoldiers.mechanics;

import java.util.*;

import static com.github.gobbisanches.ubisoldiers.mechanics.Item.Rarity.*;

/**
 * Created by Sanches on 06/07/2014.
 */
public class ItemCollection {

    private static final int FIRST_SOLDIER_ID = 1000;
    private static final int FIRST_WEAPON_ID = 2000;
    private static final int FIRST_ARMOR_ID = 3000;

    public static final Soldier PRIVATE
            = new Soldier(1000, Common, "Private", 1, 1);

    public static final Soldier PRIVATE_FIRST_CLASS
            = new Soldier(1001, Common, "Private First Class", 2, 2);

    public static final Soldier SPECIALIST
            = new Soldier(1002, Common, "Specialist", 3, 3);

    public static final Soldier CORPORAL
            = new Soldier(1003, Uncommon, "Corporal", 4, 4);

    public static final Soldier SERGEANT
            = new Soldier(1004, Uncommon, "Sergeant", 5, 5);

    public static final Soldier STAFF_SERGEANT
            = new Soldier(1005, Uncommon, "Staff Sergeant", 6, 6);

    public static final Soldier SERGEANT_FIRST_CLASS
            = new Soldier(1006, Rare, "Sergeant First Class", 7, 7);

    public static final Soldier FIRST_SERGEANT
            = new Soldier(1007, Rare, "First Sergeant", 8, 8);

    public static final Soldier MASTER_SERGEANT
            = new Soldier(1008, Rare, "Master Sergeant", 9, 9);

    public static final Soldier SERGEANT_MAJOR
            = new Soldier(1009, Epic, "Sergeant Major", 10, 10);

    public static final Soldier COMMAND_SERGEANT_MAJOR
            = new Soldier(1010, Legendary, "Command Sergeant Major", 11, 11);

    public static final Weapon CM901
            = new Weapon(2000, Common, "CM901", 1);

    public static final Weapon M16A4
            = new Weapon(2001, Common, "M16A4", 1);

    public static final Weapon M4A1
            = new Weapon(2002, Common, "M4A1", 2);

    public static final Weapon G36C
            = new Weapon(2003, Common, "G36C", 2);

    public static final Weapon MK14
            = new Weapon(2004, Common, "MK14", 3);

    public static final Weapon USAS12
            = new Weapon(2005, Common, "USAS12", 3);

    public static final Weapon FAD
            = new Weapon(2006, Uncommon, "FAD", 4);

    public static final Weapon P90
            = new Weapon(2007, Uncommon, "P90", 4);

    public static final Weapon MP7
            = new Weapon(2008, Uncommon, "MP7", 5);

    public static final Weapon KGS12
            = new Weapon(2009, Uncommon, "KGS12", 5);

    public static final Weapon L118A
            = new Weapon(2010, Uncommon, "L118A", 6);

    public static final Weapon STRIKER
            = new Weapon(2011, Uncommon, "Striker", 6);

    public static final Weapon MP5
            = new Weapon(2012, Rare, "MP5", 7);

    public static final Weapon M60E4
            = new Weapon(2013, Rare, "M60E4", 7);

    public static final Weapon KSG12
            = new Weapon(2014, Rare, "KSG12", 8);

    public static final Weapon SPAS12
            = new Weapon(2015, Rare, "SPAS12", 8);

    public static final Weapon MODEL_1887
            = new Weapon(2016, Epic, "Model 1887", 9);

    public static final Weapon BARRET
            = new Weapon(2017, Epic, "Barret", 9);

    public static final Weapon L86LSQW
            = new Weapon(2018, Legendary, "L86LSQW", 10);

    public static final Armor KEVLAR
            = new Armor(3000, Common, "Kevlar", 2);

    public static final Armor REINFORCED_KEVLAR
            = new Armor(3001, Uncommon, "Reinforced Kevlar", 4);

    public static final Armor TITANIUM_KEVLAR
            = new Armor(3002, Rare, "Titanium Kevlar", 7);

    private static TreeMap<Integer, Soldier> allSoldiers;
    private static TreeMap<Integer, Weapon> allWeapons;
    private static TreeMap<Integer, Armor> allArmors;
    private static TreeMap<Integer, Item> allItems;
    private static TreeSet<Item> allItemsSortedByQuality;

    public static SortedMap<Integer, Soldier> getAllSoldiers() {
        if(allSoldiers == null) {
            allSoldiers = new TreeMap<Integer, Soldier>();
            ArrayList<Soldier> allSoldiersList = new ArrayList<Soldier>();

            allSoldiersList.add(PRIVATE);
            allSoldiersList.add(PRIVATE_FIRST_CLASS);
            allSoldiersList.add(SPECIALIST);
            allSoldiersList.add(CORPORAL);
            allSoldiersList.add(SERGEANT);
            allSoldiersList.add(STAFF_SERGEANT);
            allSoldiersList.add(SERGEANT_FIRST_CLASS);
            allSoldiersList.add(FIRST_SERGEANT);
            allSoldiersList.add(MASTER_SERGEANT);
            allSoldiersList.add(SERGEANT_MAJOR);
            allSoldiersList.add(COMMAND_SERGEANT_MAJOR);

            for(Soldier soldier : allSoldiersList) {
                allSoldiers.put(soldier.getIdAsInteger(), soldier);
            }
        }

        return new TreeMap<Integer, Soldier>(allSoldiers);
    }

    public static SortedMap<Integer, Weapon> getAllWeapons() {
        if(allWeapons == null) {
            allWeapons = new TreeMap<Integer, Weapon>();
            ArrayList<Weapon> allWeaponsList = new ArrayList<Weapon>();

            allWeaponsList.add(CM901);
            allWeaponsList.add(M16A4);
            allWeaponsList.add(M4A1);
            allWeaponsList.add(G36C);
            allWeaponsList.add(MK14);
            allWeaponsList.add(USAS12);
            allWeaponsList.add(FAD);
            allWeaponsList.add(P90);
            allWeaponsList.add(MP7);
            allWeaponsList.add(KGS12);
            allWeaponsList.add(L118A);
            allWeaponsList.add(STRIKER);
            allWeaponsList.add(MP5);
            allWeaponsList.add(M60E4);
            allWeaponsList.add(KSG12);
            allWeaponsList.add(SPAS12);
            allWeaponsList.add(MODEL_1887);
            allWeaponsList.add(BARRET);
            allWeaponsList.add(L86LSQW);

            for (Weapon weapon : allWeaponsList) {
                allWeapons.put(weapon.getIdAsInteger(), weapon);
            }
        }

        return new TreeMap<Integer, Weapon>(allWeapons);
    }

    public static SortedMap<Integer, Armor> getAllArmors() {
        if(allArmors == null) {
            allArmors = new TreeMap<Integer, Armor>();
            ArrayList<Armor> allArmorsList = new ArrayList<Armor>();

            allArmorsList.add(KEVLAR);
            allArmorsList.add(REINFORCED_KEVLAR);
            allArmorsList.add(TITANIUM_KEVLAR);

            for (Armor armor : allArmorsList) {
                allArmors.put(armor.getIdAsInteger(), armor);
            }
        }

        return new TreeMap<Integer, Armor>(allArmors);
    }

    public static SortedMap<Integer, Item> getAllItems() {
        if (allItems == null) {
            allItems = new TreeMap<Integer, Item>();

            allItems.putAll(getAllSoldiers());
            allItems.putAll(getAllWeapons());
            allItems.putAll(getAllArmors());
        }

        return new TreeMap<Integer, Item>(allItems);
    }

    public static SortedSet<Item> getAllItemsSortedByQuality() {
        if (allItemsSortedByQuality == null) {
            allItemsSortedByQuality = new TreeSet<Item>(new Item.ItemQualityComparator());

            allItemsSortedByQuality.addAll(getAllItems().values());
        }

        return new TreeSet<Item>(allItemsSortedByQuality);
    }
}

