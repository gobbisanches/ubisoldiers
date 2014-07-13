package com.github.gobbisanches.ubisoldiers.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import com.github.gobbisanches.ubisoldiers.mechanics.*;

import java.util.ArrayList;

import static com.github.gobbisanches.ubisoldiers.app.ItemListFragment.*;

/**
 * Created by Sanches on 12/07/2014.
 */
public class UnitCustomizationFragment extends Fragment implements ItemListFragmentListener {
    public interface UnitCustomizationFragmentListener {
        public void onChangeUnit(int unitCustomizationId, Unit updatedUnit);
    }

    private static final String UNIT_CUSTOMIZATION_ID = "com.github.gobbisanches.ubisoldier.app.UnitCustomizationFragment.UnitCustomizationId";
    private static final String UNIT_INDEX = "com.github.gobbisanches.ubisoldier.app.UnitCustomizationFragment.Unit";
    private static final String GENERAL = "com.github.gobbisanches.ubisoldier.app.UnitCustomizationFragment.General";
    private static final int SOLDIER_LIST_ID = 0;
    private static final int WEAPON_LIST_ID = 1;
    private static final int ARMOR_LIST_ID = 2;

    private int unitCustomizationId;
    private General general;
    private Unit unit;
    private int unitIndex;
    private UnitFragment unitFragment;
    private ItemListFragment soldierListFragment;
    private ItemListFragment weaponListFragment;
    private ItemListFragment armorListFragment;
    private FragmentManager fragmentManager;
    private UnitCustomizationFragmentListener listener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        unitCustomizationId = getArguments().getInt(UNIT_CUSTOMIZATION_ID);
        general = (General) getArguments().getSerializable(GENERAL);
        unitIndex = getArguments().getInt(UNIT_INDEX);
        unit = general.getSquad().getUnit(unitIndex);
        fragmentManager = getFragmentManager();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.unit_customization_layout, container, false);

        unitFragment = createUnitFragmentIfMissing(R.id.unitFragmentView, unit);
        soldierListFragment = createListFragmentIfMissing(R.id.soldierListView, SOLDIER_LIST_ID, new ArrayList<Item>(general.getSoldiers().values()));
        weaponListFragment = createListFragmentIfMissing(R.id.weaponListView, WEAPON_LIST_ID, new ArrayList<Item>(general.getWeapons().values()));
        armorListFragment = createListFragmentIfMissing(R.id.armorListView, ARMOR_LIST_ID, new ArrayList<Item>(general.getArmors().values()));

        return v;
    }

    private ItemListFragment createListFragmentIfMissing(int id, int listId, ArrayList<Item> items) {
        ItemListFragment fragment = (ItemListFragment) fragmentManager.findFragmentById(id);
        if (fragment == null) {
            fragment = ItemListFragment.newInstance(listId, items, this);
            fragmentManager.beginTransaction().add(id, fragment).commit();
        }

        return fragment;
    }

    private UnitFragment createUnitFragmentIfMissing(int id, Unit unit) {
        UnitFragment fragment = (UnitFragment) fragmentManager.findFragmentById(id);
        if (fragment == null) {
            fragment = UnitFragment.newInstance(unit);
            fragmentManager.beginTransaction().add(id, fragment).commit();
        }

        return fragment;
    }

    @Override
    public void onSelectItem(int id, Item item, int position, ArrayList<Item> allItems) {
        switch (id) {
            case SOLDIER_LIST_ID:
                unit.setSoldier((Soldier) item);
                break;
            case WEAPON_LIST_ID:
                unit.setWeapon((Weapon) item);
                break;
            case ARMOR_LIST_ID:
                unit.setArmor((Armor) item);
                break;
            default:
                throw new RuntimeException("Invalid list ID " + id);
        }

        unitFragment.updateUnitAndSetHpToFull(unit);
        listener.onChangeUnit(unitCustomizationId, unit);
    }

    private void setUnitCustomizationListener(UnitCustomizationFragmentListener listener) {
        this.listener = listener;
    }

    public static UnitCustomizationFragment newInstance(int unitCustomizationId,
                                                        General general,
                                                        int unitIndex,
                                                        UnitCustomizationFragmentListener listener) {
        Bundle args = new Bundle();
        args.putInt(UNIT_CUSTOMIZATION_ID, unitCustomizationId);
        args.putSerializable(GENERAL, general);
        args.putInt(UNIT_INDEX, unitIndex);

        UnitCustomizationFragment fragment = new UnitCustomizationFragment();
        fragment.setArguments(args);
        fragment.setUnitCustomizationListener(listener);

        return fragment;
    }
}
