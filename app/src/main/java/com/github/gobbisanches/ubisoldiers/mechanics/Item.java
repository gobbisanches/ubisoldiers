package com.github.gobbisanches.ubisoldiers.mechanics;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by Sanches on 06/07/2014.
 */
public class Item implements Serializable, Comparable {
    public enum Rarity {Common, Uncommon, Rare, Epic, Legendary}
    private static final long serialVersionUID = 1L;

    private int id;
    private Rarity rarity;
    private String name;
    private double attack;
    private double defense;
    private Integer quality;

    public Item(int id, Rarity rarity, String name, double attack, double defense) {
        this.id = id;
        this.rarity = rarity;
        this.name = name;
        this.attack = attack;
        this.defense = defense;
        this.quality = PolicyManager.getRules().calculateQualityOf(this);
    }

    public Item(Item other) {
        this.id = other.id;
        this.rarity = other.rarity;
        this.name = other.name;
        this.attack = other.attack;
        this.defense = other.defense;
    }

    public int getId() {
        return id;
    }

    public Integer getIdAsInteger() {
        return Integer.valueOf(id);
    }

    public Rarity getRarity() {
        return rarity;
    }

    public Integer getQuality() {
        return quality;
    }

    public String getName() {
        return name;
    }

    public double getAttack() {
        return attack;
    }

    public double getDefense() {
        return defense;
    }

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", rarity=" + rarity +
                ", name='" + name + '\'' +
                ", attack=" + attack +
                ", defense=" + defense +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (id != item.id) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public int compareTo(Object o) {
        if(o instanceof Item) {
            Item otherItem = (Item) o;
            return id - otherItem.id;
        }
        else {
            throw new RuntimeException("Invalid comparison of items");
        }
    }

    public static class ItemQualityComparator implements Comparator<Item> {
        public ItemQualityComparator() {
        }

        @Override
        public int compare(Item item1, Item item2) {
            int qualityDiff =  item1.getQuality() - item2.getQuality();
            if (qualityDiff == 0) {
                // no draw is possible for different cards
                return item1.getId() - item2.getId();
            } else {
                return qualityDiff;
            }

        }
    }
}
