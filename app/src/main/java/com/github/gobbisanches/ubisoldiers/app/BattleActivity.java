package com.github.gobbisanches.ubisoldiers.app;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import com.github.gobbisanches.ubisoldiers.mechanics.*;

/**
 * Created by Sanches on 13/07/2014.
 */
public class BattleActivity extends SingleFragmentActivity  {
    private static final String ATTACKER_SQUAD = "com.github.gobbisanches.ubisoldier.app.BattleActivity.AttackerSquad";
    private static final String DEFENDER_SQUAD = "com.github.gobbisanches.ubisoldier.app.BattleActivity.DefenderSquad";
    private static final String BATTLE_LOG = "com.github.gobbisanches.ubisoldier.app.BattleActivity.BattleLog";

    private Squad attackerSquad;
    private Squad defenderSquad;
    private BattleLog battleLog;

    @Override
    protected Fragment createFragment() {
        attackerSquad = (Squad) getIntent().getSerializableExtra(ATTACKER_SQUAD);
        defenderSquad = (Squad) getIntent().getSerializableExtra(DEFENDER_SQUAD);
        battleLog     = (BattleLog)   getIntent().getSerializableExtra(BATTLE_LOG);

        return BattleFragment.newInstance(attackerSquad, defenderSquad, battleLog);
    }

    public static void startForBattle(Squad attackerSquad, Squad defenderSquad, BattleLog battleLog, Activity caller) {
        Intent intent = new Intent(caller, BattleActivity.class);

        intent.putExtra(ATTACKER_SQUAD, attackerSquad);
        intent.putExtra(DEFENDER_SQUAD, defenderSquad);
        intent.putExtra(BATTLE_LOG, battleLog);

        caller.startActivityForResult(intent, 0);
    }
}

