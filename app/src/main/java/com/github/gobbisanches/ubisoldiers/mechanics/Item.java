package com.github.gobbisanches.ubisoldiers.mechanics;

import java.io.Serializable;

/**
 * Created by Sanches on 06/07/2014.
 */
public class Item implements Serializable {
    private static final long serialVersionUID = 1L;

    private Rarity rarity;
    ;
    private int id;

    public Item(int id, Rarity rarity) {
        this.id = id;
        this.rarity = rarity;
    }

    public int getId() {
        return id;
    }

    public Rarity getRarity() {
        return rarity;
    }

    public Integer getQuality() {
        return PolicyManager.getDefaultRules().calculateQualityOf(this);
    }

    public enum Rarity {Common, Uncommon, Rare}
}
