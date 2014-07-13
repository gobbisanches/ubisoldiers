package com.github.gobbisanches.ubisoldiers.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import com.github.gobbisanches.ubisoldiers.mechanics.General;
import com.github.gobbisanches.ubisoldiers.mechanics.Unit;

/**
 * Created by Sanches on 13/07/2014.
 */
public class UnitCustomizationActivity extends SingleFragmentActivity implements UnitCustomizationFragment.UnitCustomizationFragmentListener {
    private static final String UNIT_INDEX = "com.github.gobbisanches.ubisoldier.app.UnitCustomizationActivity.UnitIndex";
    private static final String GENERAL = "com.github.gobbisanches.ubisoldier.app.UnitCustomizationActivity.General";
    private General general;
    private int unitIndex;

    @Override
    protected Fragment createFragment() {
        general = (General) getIntent().getSerializableExtra(GENERAL);
        unitIndex = getIntent().getIntExtra(UNIT_INDEX, -1);

        if (general == null) {
            throw new RuntimeException("No General object was passed");
        }

        if ((unitIndex < 0) || (unitIndex > 2)) {
            if (unitIndex == -1) {
                throw new RuntimeException("No unit index parameter was passed");
            } else {
                throw new RuntimeException("Invalid Unit Index: " + unitIndex);
            }
        }

        return UnitCustomizationFragment.newInstance(unitIndex, general, unitIndex, this);
    }

    @Override
    public void onChangeUnit(int unitCustomizationId, Unit updatedUnit) {
//        general.getSquad().setUnit(unitIndex, updatedUnit);
        General.getPlayerGeneral().getSquad().setUnit(unitIndex, updatedUnit);
        setResult(unitIndex);
    }

    public static void startForUnit(General general, int unitIndex, Activity caller) {
        if(general == null) {
            throw new RuntimeException("Invalid General object");
        }
        if((unitIndex < 0) || (unitIndex > 2)) {
            throw new RuntimeException("Invalid unitIndex " + unitIndex);
        }

        Intent intent = new Intent(caller, UnitCustomizationActivity.class);

        intent.putExtra(GENERAL, general);
        intent.putExtra(UNIT_INDEX, unitIndex);

        caller.startActivityForResult(intent, unitIndex);
    }
}

