package com.github.gobbisanches.ubisoldiers.mechanics;

import android.util.Log;

import java.util.*;

import static com.github.gobbisanches.ubisoldiers.mechanics.Item.Rarity.*;

/**
 * Created by Sanches on 06/07/2014.
 */
public class ItemCollection {
    private static class ItemQualityComparator implements Comparator<Item> {
        private ItemQualityComparator() {
        }

        @Override
        public int compare(Item item1, Item item2) {
            return item1.getQuality() - item2.getQuality();
        }
    }

    private static void addAllSoldiers(SortedSet<Item> items) {
        items.add(new Soldier(Common,   "Private",  1, 1));
        items.add(new Soldier(Uncommon, "Sergeant", 3, 3));
        items.add(new Soldier(Rare,     "Captain",  5, 5));
    }

    private static void addAllWeapons(SortedSet<Item> items) {
        items.add(new Weapon(Common,   "Handgun", 1));
        items.add(new Weapon(Uncommon, "Shotgun", 3));
        items.add(new Weapon(Rare,     "Rifle", 5));
    }

    private static void addAllArmors(SortedSet<Item> items) {
        items.add(new Armor(Common, "Leather", 1));
        items.add(new Armor(Uncommon, "Kevlar",      3));
        items.add(new Armor(Rare,     "Spider Silk", 5));
    }

    public static SortedSet<Item> getAllItems() {
        SortedSet<Item> allItems = new TreeSet<Item>(new ItemQualityComparator());

        addAllSoldiers(allItems);
        addAllWeapons(allItems);
        addAllArmors(allItems);

        return allItems;
    }
}

