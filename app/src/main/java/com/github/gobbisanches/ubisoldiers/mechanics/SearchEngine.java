package com.github.gobbisanches.ubisoldiers.mechanics;

import android.util.Log;

import java.util.Comparator;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by Sanches on 05/07/2014.
 */
public class SearchEngine {
    SortedSet<Item> allItems;
    Integer sumOfQualityOfAllItems;

    public SearchEngine() {
        allItems = General.getPlayerGeneral().getAllMissingItemsSortedByQuality();
        sumOfQualityOfAllItems = 0;
        for(Item item : allItems) {
            sumOfQualityOfAllItems += item.getQuality();
        }
    }

    public Item performSearch(Random random, double multiplier) {
        return getItemForRoll(performRoll(random, multiplier, sumOfQualityOfAllItems));
    }

    private int performRoll(Random random, double multiplier, int maxValue) {
        int initialRoll = Math.abs(random.nextInt(maxValue));
        int roll = Math.min((int) Math.floor(initialRoll * multiplier), maxValue);

        Log.d("UBISOLDIERS", "[SEARCHENGINE] maxValue = " + maxValue);
        Log.d("UBISOLDIERS", "[SEARCHENGINE] initialRoll = " + initialRoll);
        Log.d("UBISOLDIERS", "[SEARCHENGINE] roll = " + roll);

        return roll;
    }

    private Item getItemForRoll(int roll) {
        for (Item item : allItems) {
            roll -= item.getQuality();
            if(roll <= 0) {
                return item;
            }
        }

        throw new RuntimeException("Error: Could not find any Item for roll " + roll);
    }

    public boolean hasPlayerNoMissingItems() {
        return (allItems.isEmpty());
    }
}
