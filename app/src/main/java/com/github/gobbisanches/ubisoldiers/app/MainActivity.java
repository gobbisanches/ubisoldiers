package com.github.gobbisanches.ubisoldiers.app;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.github.gobbisanches.ubisoldiers.mechanics.*;

import static com.github.gobbisanches.ubisoldiers.mechanics.Item.Rarity.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import org.unbiquitous.uos.core.UOS;
import org.unbiquitous.uos.core.adaptabitilyEngine.Gateway;
import org.unbiquitous.uos.core.driverManager.DriverData;
import org.unbiquitous.uos.core.messageEngine.dataType.UpDriver;


public class MainActivity extends Activity {
    private static final int FIRST_SOLDIER_ID = 1000;
    private static final int FIRST_WEAPON_ID = 2000;
    private static final int FIRST_ARMOR_ID = 3000;
    private BattleFragment battleFragment;
    private UnitCustomizationFragment unitCustomizationFragment;
    private ArmyFragment armyFragment;
    private FragmentManager fragmentManager;
    private Random random;
    private UosManager uosManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        General.setPlayerGeneral(new General(1945, "Winston Churchill"));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_layout);

        fragmentManager = getFragmentManager();
        armyFragment = createArmyFragmentIfMissing(R.id.fragmentContainer, General.getPlayerGeneral());

        UosManager.initInstance(this);

    }

    private ArmyFragment createArmyFragmentIfMissing(int id, General general) {
        ArmyFragment fragment = (ArmyFragment) fragmentManager.findFragmentById(id);
        if (fragment == null) {
            fragment = ArmyFragment.newInstance(general);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        armyFragment.updateViews();
    }
}
