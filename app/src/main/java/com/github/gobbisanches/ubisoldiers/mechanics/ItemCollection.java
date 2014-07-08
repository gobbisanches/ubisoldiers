package com.github.gobbisanches.ubisoldiers.mechanics;

import java.util.Comparator;
import java.util.SortedSet;
import java.util.TreeSet;

import static com.github.gobbisanches.ubisoldiers.mechanics.Item.Rarity.*;

/**
 * Created by Sanches on 06/07/2014.
 */
public class ItemCollection {
    private static final int FIRST_SOLDIER_ID = 1000;
    private static final int FIRST_WEAPON_ID = 2000;
    private static final int FIRST_ARMOR_ID = 3000;

    private static void addAllSoldiers(SortedSet<Item> items) {
        items.add(new Soldier(FIRST_SOLDIER_ID + 0, Common, "Private", 1, 1));
        items.add(new Soldier(FIRST_SOLDIER_ID + 1, Uncommon, "Sergeant", 3, 3));
        items.add(new Soldier(FIRST_SOLDIER_ID + 2, Rare, "Captain", 5, 5));
    }

    private static void addAllWeapons(SortedSet<Item> items) {
        items.add(new Weapon(FIRST_WEAPON_ID + 0, Common, "Handgun", 1));
        items.add(new Weapon(FIRST_WEAPON_ID + 1, Uncommon, "Shotgun", 3));
        items.add(new Weapon(FIRST_WEAPON_ID + 2, Rare, "Rifle", 5));
    }

    private static void addAllArmors(SortedSet<Item> items) {
        items.add(new Armor(FIRST_ARMOR_ID + 0, Common, "Leather", 1));
        items.add(new Armor(FIRST_ARMOR_ID + 1, Uncommon, "Kevlar", 3));
        items.add(new Armor(FIRST_ARMOR_ID + 2, Rare, "Spider Silk", 5));
    }

    public static SortedSet<Item> getAllItems() {
        SortedSet<Item> allItems = new TreeSet<Item>(new ItemQualityComparator());

        addAllSoldiers(allItems);
        addAllWeapons(allItems);
        addAllArmors(allItems);

        return allItems;
    }

    private static class ItemQualityComparator implements Comparator<Item> {
        private ItemQualityComparator() {
        }

        @Override
        public int compare(Item item1, Item item2) {
            return item1.getQuality() - item2.getQuality();
        }
    }
}

