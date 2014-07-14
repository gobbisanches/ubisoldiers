package com.github.gobbisanches.ubisoldiers.mechanics;

import java.io.Serializable;
import java.util.*;

import static com.github.gobbisanches.ubisoldiers.mechanics.ItemCollection.*;

/**
 * Created by Sanches on 12/07/2014.
 */
public class General implements Serializable {
    private static final long serialVersionUID = 1L;
    private static General PlayerGeneral;
    private int id;
    private String name;
    private Squad squad;
    private TreeSet<Integer> soldierIds;
    private TreeSet<Integer> weaponIds;
    private TreeSet<Integer> armorIds;

    public General(int id, String name, List<Unit> squad, SortedSet<Integer> soldierIds, SortedSet<Integer> weaponIds, SortedSet<Integer> armorIds) {
        setSquadForInitialUsers();

        this.id = id;
        this.name = name;
        this.squad = new Squad(squad);
        this.soldierIds.addAll(soldierIds);
        this.weaponIds.addAll(weaponIds);
        this.armorIds.addAll(armorIds);

        insertAllSquadItemsInTheCollections();
    }

    public General(int id, String name) {
        this.id = id;
        this.name = name;

        setSquadForInitialUsers();
    }

    public General(int id, String name, Squad squad) {
        this.id = id;
        this.name = name;
        this.squad = squad;
    }

    private void setSquadForInitialUsers() {
        soldierIds = new TreeSet<Integer>();
        weaponIds = new TreeSet<Integer>();
        armorIds = new TreeSet<Integer>();

        Unit unit = new Unit(PRIVATE, CM901, KEVLAR);
        ArrayList<Unit> units = new ArrayList<Unit>();
        units.add(new Unit(unit));
        units.add(new Unit(unit));
        units.add(new Unit(unit));
        squad = new Squad(units);

        insertAllSquadItemsInTheCollections();

//        soldierIds.add(PRIVATE_FIRST_CLASS.getId());
//        soldierIds.add(SPECIALIST.getId());
//        soldierIds.add(CORPORAL.getId());
//        soldierIds.add(SERGEANT.getId());
//        soldierIds.add(STAFF_SERGEANT.getId());
//        soldierIds.add(SERGEANT_FIRST_CLASS.getId());
//        soldierIds.add(FIRST_SERGEANT.getId());
//        soldierIds.add(MASTER_SERGEANT.getId());
//        soldierIds.add(SERGEANT_MAJOR.getId());
//        soldierIds.add(COMMAND_SERGEANT_MAJOR.getId());
//
//        weaponIds.add(M16A4.getId());
//        weaponIds.add(M4A1.getId());
//        weaponIds.add(G36C.getId());
//        weaponIds.add(MK14.getId());
//        weaponIds.add(USAS12.getId());
//        weaponIds.add(FAD.getId());
//        weaponIds.add(P90.getId());
//        weaponIds.add(MP7.getId());
//        weaponIds.add(KGS12.getId());
//        weaponIds.add(L118A.getId());
//        weaponIds.add(STRIKER.getId());
//        weaponIds.add(MP5.getId());
//        weaponIds.add(M60E4.getId());
//        weaponIds.add(KSG12.getId());
//        weaponIds.add(SPAS12.getId());
//        weaponIds.add(MODEL_1887.getId());
//        weaponIds.add(BARRET.getId());
//        weaponIds.add(L86LSQW.getId());
//
//        armorIds.add(REINFORCED_KEVLAR.getId());
//        armorIds.add(TITANIUM_KEVLAR.getId());
    }

    private void insertAllSquadItemsInTheCollections() {
        for (int i=0; i < 3; ++i) {
            Unit unit = squad.getUnit(i);
            soldierIds.add(unit.getSoldier().getId());
            weaponIds.add(unit.getWeapon().getId());
            armorIds.add(unit.getArmor().getId());
        }
    }

    public static void setPlayerGeneral(General playerGeneral) {
        PlayerGeneral = playerGeneral;
    }

    public static General getPlayerGeneral() {
        if (PlayerGeneral == null) {
            throw new RuntimeException("No Player General has been set");
        }

        return PlayerGeneral;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Squad getSquad() {
        return squad;
    }

    public SortedSet<Integer> getSoldierIds() {
        return soldierIds;
    }

    public SortedSet<Integer> getWeaponIds() {
        return weaponIds;
    }

    public SortedSet<Integer> getArmorIds() {
        return armorIds;
    }

    public SortedMap<Integer, Soldier> getSoldiers() {
        SortedMap<Integer, Soldier> mySoldiers = new TreeMap<Integer, Soldier>();
        SortedMap<Integer, Soldier> allSoldiers = ItemCollection.getAllSoldiers();

        for(Integer index : getSoldierIds()) {
            mySoldiers.put(index, allSoldiers.get(index));
        }

        return mySoldiers;
    }

    public SortedMap<Integer, Weapon> getWeapons() {
        SortedMap<Integer, Weapon> myWeapons = new TreeMap<Integer, Weapon>();
        SortedMap<Integer, Weapon> allWeapons = ItemCollection.getAllWeapons();

        for(Integer index : getWeaponIds()) {
            myWeapons.put(index, allWeapons.get(index));
        }

        return myWeapons;
    }

    public SortedMap<Integer, Armor> getArmors() {
        SortedMap<Integer, Armor> myArmors = new TreeMap<Integer, Armor>();
        SortedMap<Integer, Armor> allArmors = ItemCollection.getAllArmors();

        for(Integer index : getArmorIds()) {
            myArmors.put(index, allArmors.get(index));
        }

        return myArmors;
    }

    public final SortedSet<Integer> getItemIdsCopy() {
        SortedSet<Integer> itemIds = new TreeSet<Integer>();

        itemIds.addAll(soldierIds);
        itemIds.addAll(weaponIds);
        itemIds.addAll(armorIds);

        return itemIds;
    }

    public SortedSet<Item> getAllMissingItemsSortedByQuality() {
        SortedSet<Item> allItems = ItemCollection.getAllItemsSortedByQuality();
        SortedSet<Integer> myItemIds = getItemIdsCopy();
        SortedSet<Item> allMissingItems = new TreeSet<Item>(new Item.ItemQualityComparator());

        for(Item item : allItems) {
            if (!myItemIds.contains(item.getIdAsInteger())) {
                allMissingItems.add(item);
            }
        }

        return allMissingItems;
    }

    public void addItem(Item item) {
        if(item instanceof Soldier) {
            soldierIds.add(item.getIdAsInteger());
        } else if (item instanceof Weapon) {
            weaponIds.add(item.getIdAsInteger());
        } else if (item instanceof Armor) {
            armorIds.add(item.getIdAsInteger());
        } else {
            throw new RuntimeException("Invalid item " + item.toString());
        }

    }
}
