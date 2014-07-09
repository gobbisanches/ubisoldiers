package com.github.gobbisanches.ubisoldiers.app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.github.gobbisanches.ubisoldiers.mechanics.*;

import static com.github.gobbisanches.ubisoldiers.mechanics.Item.Rarity.*;

import java.util.ArrayList;


public class MainActivity extends Activity {
    private static final int FIRST_SOLDIER_ID = 1000;
    private static final int FIRST_WEAPON_ID = 2000;
    private static final int FIRST_ARMOR_ID = 3000;
    private BattleFragment battleFragment;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        Soldier soldier1 = new Soldier(FIRST_SOLDIER_ID + 0, Common, "Private", 1, 1);
        Soldier soldier2 = new Soldier(FIRST_SOLDIER_ID + 1, Uncommon, "Sergeant", 3, 3);
        Soldier soldier3 = new Soldier(FIRST_SOLDIER_ID + 2, Rare, "Captain", 5, 5);

        Weapon weapon1 = new Weapon(FIRST_WEAPON_ID + 0, Common, "Handgun", 1);
        Weapon weapon2 = new Weapon(FIRST_WEAPON_ID + 1, Uncommon, "Shotgun", 3);
        Weapon weapon3 = new Weapon(FIRST_WEAPON_ID + 2, Rare, "Rifle", 5);

        Armor armor1 = new Armor(FIRST_ARMOR_ID + 0, Common, "Leather", 1);
        Armor armor2 = new Armor(FIRST_ARMOR_ID + 1, Uncommon, "Kevlar", 3);
        Armor armor3 = new Armor(FIRST_ARMOR_ID + 2, Rare, "Spider Silk", 5);

        ArrayList<Unit> attackerUnits = new ArrayList<Unit>();
        attackerUnits.add(new Unit(soldier1, weapon3, armor2));
        attackerUnits.add(new Unit(soldier2, weapon1, armor3));
        attackerUnits.add(new Unit(soldier3, weapon2, armor1));
        Squad attackerSquad = new Squad(attackerUnits);

        ArrayList<Unit> defenderUnits = new ArrayList<Unit>();
        defenderUnits.add(new Unit(soldier2, weapon2, armor1));
        defenderUnits.add(new Unit(soldier3, weapon3, armor2));
        defenderUnits.add(new Unit(soldier1, weapon1, armor3));
        Squad defenderSquad = new Squad(defenderUnits);

        BattleLog battleLog = new BattleLog();

        fragmentManager = getFragmentManager();
        battleFragment = createIfMissing(R.id.fragmentContainer, attackerSquad, defenderSquad, battleLog);
    }

    private BattleFragment createIfMissing(int id, Squad attackerSquad, Squad defenderSquad, BattleLog battleLog) {
        BattleFragment fragment = (BattleFragment) fragmentManager.findFragmentById(id);
        if(fragment == null) {
            fragment = BattleFragment.newInstance(attackerSquad, defenderSquad, battleLog);
            fragmentManager.beginTransaction().add(id, fragment).commit();
        }

        return fragment;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
