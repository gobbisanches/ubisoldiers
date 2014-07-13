package com.github.gobbisanches.ubisoldiers.app;

import com.github.gobbisanches.ubisoldiers.mechanics.Item;
import com.github.gobbisanches.ubisoldiers.mechanics.ItemCollection;

import java.util.TreeMap;

import static com.github.gobbisanches.ubisoldiers.mechanics.ItemCollection.*;

/**
 * Created by Sanches on 12/07/2014.
 */
public class ResourceManager {
    public static TreeMap<Item, Integer> itemResources;

    public static int getResourceForItem(Item item) {
        if (itemResources == null) {
            itemResources = new TreeMap<Item, Integer>();

            itemResources.put(PRIVATE, R.drawable.rank_private);
            itemResources.put(PRIVATE_FIRST_CLASS, R.drawable.rank_private_first_class);
            itemResources.put(SPECIALIST, R.drawable.rank_specialist);
            itemResources.put(CORPORAL, R.drawable.rank_corporal);
            itemResources.put(SERGEANT, R.drawable.rank_sergeant);
            itemResources.put(STAFF_SERGEANT, R.drawable.rank_staff_sergeant);
            itemResources.put(SERGEANT_FIRST_CLASS, R.drawable.rank_sergeant_first_class);
            itemResources.put(FIRST_SERGEANT, R.drawable.rank_first_sergeant);
            itemResources.put(MASTER_SERGEANT, R.drawable.rank_master_sergeant);
            itemResources.put(SERGEANT_MAJOR, R.drawable.rank_sergeant_major);
            itemResources.put(COMMAND_SERGEANT_MAJOR, R.drawable.rank_command_sergeant_major);

            itemResources.put(CM901, R.drawable.weapon1);
            itemResources.put(M16A4, R.drawable.weapon2);
            itemResources.put(M4A1, R.drawable.weapon3);
            itemResources.put(G36C, R.drawable.weapon4);
            itemResources.put(MK14, R.drawable.weapon5);
            itemResources.put(USAS12, R.drawable.weapon6);
            itemResources.put(FAD, R.drawable.weapon7);
            itemResources.put(P90, R.drawable.weapon8);
            itemResources.put(MP7, R.drawable.weapon9);
            itemResources.put(KGS12, R.drawable.weapon10);
            itemResources.put(L118A, R.drawable.weapon11);
            itemResources.put(STRIKER, R.drawable.weapon12);
            itemResources.put(MP5, R.drawable.weapon13);
            itemResources.put(M60E4, R.drawable.weapon14);
            itemResources.put(KSG12, R.drawable.weapon15);
            itemResources.put(SPAS12, R.drawable.weapon16);
            itemResources.put(MODEL_1887, R.drawable.weapon17);
            itemResources.put(BARRET, R.drawable.weapon18);
            itemResources.put(L86LSQW, R.drawable.weapon19);

            itemResources.put(KEVLAR, R.drawable.armor2);
            itemResources.put(REINFORCED_KEVLAR, R.drawable.armor4);
            itemResources.put(TITANIUM_KEVLAR, R.drawable.armor7);
        }

        if(!itemResources.containsKey(item)) {
            throw new RuntimeException("No icon is registered for item: " + item.toString());
        }

        return itemResources.get(item);
    }

    public static int getResourceForAttack() {
        return R.drawable.attack2;
    }

    public static int getResourceForDefense() {
        return R.drawable.defense2;
    }
}

