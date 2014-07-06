package com.github.gobbisanches.ubisoldiers.mechanics;

/**
 * Created by Sanches on 06/07/2014.
 */
public class Item {
    public enum Rarity { Common, Uncommon, Rare };
    private Rarity rarity;

    public Item(Rarity rarity) {
        this.rarity = rarity;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public Integer getQuality() {
        return PolicyManager.getDefaultRules().calculateQualityOf(this);
    }
}
