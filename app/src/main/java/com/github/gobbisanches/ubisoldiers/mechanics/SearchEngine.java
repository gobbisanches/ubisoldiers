package com.github.gobbisanches.ubisoldiers.mechanics;

import java.util.Random;
import java.util.SortedSet;

/**
 * Created by Sanches on 05/07/2014.
 */
public class SearchEngine {
    SortedSet<Item> allItems;
    Integer sumOfQualityOfAllItems;

    public SearchEngine() {
        allItems = ItemCollection.getAllItems();
        sumOfQualityOfAllItems = 0;
        for(Item item : allItems) {
            sumOfQualityOfAllItems += item.getQuality();
        }
    }

    public Item performSearch(Random random, double multiplier) {
        return getItemForRoll(performRoll(random, multiplier, sumOfQualityOfAllItems));
    }

    private int performRoll(Random random, double multiplier, int maxValue) {
        int initialRoll = random.nextInt(maxValue);
        return Math.max((int) Math.floor(initialRoll * multiplier), maxValue);
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
}
