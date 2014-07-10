package com.github.gobbisanches.ubisoldiers.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.github.gobbisanches.ubisoldiers.mechanics.*;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import static com.github.gobbisanches.ubisoldiers.app.UnitFragment.State.*;
import static com.github.gobbisanches.ubisoldiers.mechanics.BattleLogEntry.BattleResultType.*;

/**
 * Created by Sanches on 09/07/2014.
 */
public class BattleFragment extends Fragment implements BattleLogParser {
    private class Shooting {
        private BattleLogEntry.ShootingDirection direction;
        private String shooterName;
        private String targetName;
        private int damage;

        private Shooting(BattleLogEntry.ShootingDirection direction, String shooterName, String targetName, int damage) {
            this.direction = direction;
            this.shooterName = shooterName;
            this.targetName = targetName;
            this.damage = damage;
        }

        public BattleLogEntry.ShootingDirection getDirection() {
            return direction;
        }

        public String getShooterName() {
            return shooterName;
        }

        public String getTargetName() {
            return targetName;
        }

        public int getDamage() {
            return damage;
        }
    }

    private class ShootingTask extends TimerTask {
        @Override
        public void run() {
            handler.post(new ShootingDisplayer(nextShootingIndex));
            ++nextShootingIndex;

            if (nextShootingIndex >= shootings.size()) {
                shootingsTimer.cancel();
                shootingsTimer.purge();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        showBattleResult();
                    }
                });
            }
        }
    }

    private class ShootingDisplayer implements Runnable {
        private int index;

        private ShootingDisplayer(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            showShooting(index);
        }
    }

    private static final String ATTACKER_SQUAD = "com.github.gobbisanches.ubisoldier.app.BattleFragment.AttackerSquad";
    private static final String DEFENDER_SQUAD = "com.github.gobbisanches.ubisoldier.app.BattleFragment.DefenderSquad";
    private static final String BATTLE_LOG = "com.github.gobbisanches.ubisoldier.app.BattleFragment.BattleLog";

    private UnitFragment attackerUnit1Fragment;
    private UnitFragment attackerUnit2Fragment;
    private UnitFragment attackerUnit3Fragment;
    private UnitFragment defenderUnit1Fragment;
    private UnitFragment defenderUnit2Fragment;
    private UnitFragment defenderUnit3Fragment;
    private FragmentManager fragmentManager;
    private Button doneButton;
    private BattleSquad attackerSquad;
    private BattleSquad defenderSquad;
    private BattleLog battleLog;
    private ArrayList<Shooting> shootings;
    private Timer shootingsTimer;
    private int nextShootingIndex;
    private Handler handler;
    private BattleLogEntry.BattleResultType battleResult;

    @Override
    public void onStartParsing() {

    }

    @Override
    public void onFinishParsing() {

    }

    @Override
    public void parseShootingEntry(int round, BattleLogEntry.ShootingDirection direction, String shooterName, String targetName, int damage) {
        shootings.add(new Shooting(direction, shooterName, targetName, damage));
    }

    public void showBattleResult() {
        if (battleResult == ATTACKER_WON) {
            doneButton.setBackgroundResource(R.drawable.mission_accomplished);
        } else {
            doneButton.setBackgroundResource(R.drawable.failed3);
        }
    }

    public void showShooting(int index) {
        Log.d("UBISOLDIERS", "Showing shooting " + index);
        Shooting shooting = shootings.get(index);

        UnitFragment shooterFragment;
        UnitFragment targetFragment;
        if(shooting.getDirection() == BattleLogEntry.ShootingDirection.FROM_ATTACKER) {
            shooterFragment = getAttackerFragmentBySoldierName(shooting.getShooterName());
            targetFragment  = getDefenderFragmentBySoldierName(shooting.getTargetName());
        } else if(shooting.getDirection() == BattleLogEntry.ShootingDirection.FROM_DEFENDER) {
            shooterFragment  = getDefenderFragmentBySoldierName(shooting.getShooterName());
            targetFragment = getAttackerFragmentBySoldierName(shooting.getTargetName());
        } else {
            throw new RuntimeException("Invalid ShootingDirection");
        }
        targetFragment.takeDamage(shooting.getDamage());

        attackerUnit1Fragment.setState(NORMAL);
        attackerUnit2Fragment.setState(NORMAL);
        attackerUnit3Fragment.setState(NORMAL);

        defenderUnit1Fragment.setState(NORMAL);
        defenderUnit2Fragment.setState(NORMAL);
        defenderUnit3Fragment.setState(NORMAL);

        shooterFragment.setState(SHOOTING);
        targetFragment.setState(TAKING_DAMAGE);
    }

    private UnitFragment getAttackerFragmentBySoldierName(String soldierName) {
        UnitFragment fragment;
        if(soldierName == attackerUnit1Fragment.getSoldierName()) {
            fragment = attackerUnit1Fragment;
        } else if (soldierName == attackerUnit2Fragment.getSoldierName()) {
            fragment = attackerUnit2Fragment;
        }
        else if (soldierName == attackerUnit3Fragment.getSoldierName()) {
            fragment = attackerUnit3Fragment;
        } else {
            throw new RuntimeException("No attacker unit has soldier named \"" + soldierName +"\"");
        }
        return fragment;
    }

    private UnitFragment getDefenderFragmentBySoldierName(String soldierName) {
        UnitFragment fragment;
        if(soldierName == defenderUnit1Fragment.getSoldierName()) {
            fragment = defenderUnit1Fragment;
        } else if (soldierName == defenderUnit2Fragment.getSoldierName()) {
            fragment = defenderUnit2Fragment;
        }
        else if (soldierName == defenderUnit3Fragment.getSoldierName()) {
            fragment = defenderUnit3Fragment;
        } else {
            throw new RuntimeException("No defender unit has soldier named \"" + soldierName +"\"");
        }
        return fragment;
    }

    @Override
    public void parseBattleResultEntry(BattleLogEntry.BattleResultType battleResultType) {
        this.battleResult = battleResultType;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        attackerSquad = (BattleSquad) getArguments().getSerializable(ATTACKER_SQUAD);
        defenderSquad = (BattleSquad) getArguments().getSerializable(DEFENDER_SQUAD);
        battleLog     = (BattleLog)   getArguments().getSerializable(BATTLE_LOG);
        shootings = new ArrayList<Shooting>();
        battleLog.getParsedBy(this);
        nextShootingIndex = 0;
        handler = new Handler();

        if (attackerSquad == null) {
            throw new RuntimeException("Invalid attacker BattleSquad object in BattleFragment object");
        }
        if (defenderSquad == null) {
            throw new RuntimeException("Invalid defender BattleSquad object in BattleFragment object");
        }
        if (battleLog == null) {
            throw new RuntimeException("Invalid BattleLog object in BattleFragment object");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.battle_layout, container, false);
        fragmentManager = getFragmentManager();
        attackerUnit1Fragment = createIfMissing(R.id.attackerUnit1Fragment, attackerSquad.getUnits().get(0));
        attackerUnit2Fragment = createIfMissing(R.id.attackerUnit2Fragment, attackerSquad.getUnits().get(1));
        attackerUnit3Fragment = createIfMissing(R.id.attackerUnit3Fragment, attackerSquad.getUnits().get(2));
        defenderUnit1Fragment = createIfMissing(R.id.defenderUnit1Fragment, defenderSquad.getUnits().get(0));
        defenderUnit2Fragment = createIfMissing(R.id.defenderUnit2Fragment, defenderSquad.getUnits().get(1));
        defenderUnit3Fragment = createIfMissing(R.id.defenderUnit3Fragment, defenderSquad.getUnits().get(2));
        doneButton = (Button) v.findViewById(R.id.doneButton);

        doneButton.setClickable(true);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBattle();
            }
        });

        return v;
    }

    private UnitFragment createIfMissing(int id, BattleUnit battleUnit) {
        UnitFragment fragment = (UnitFragment) fragmentManager.findFragmentById(id);
        if(fragment == null) {
            fragment = UnitFragment.newInstance(battleUnit);
            fragmentManager.beginTransaction().add(id, fragment).commit();
        }

        return fragment;
    }

    public void showBattle() {
        doneButton.setEnabled(false);
        Log.d("UBISOLDIERS", battleLog.toString());

        final int intervalInMillisecs = 500;
        nextShootingIndex = 0;
        shootingsTimer = new Timer();
        shootingsTimer.scheduleAtFixedRate(new ShootingTask(), 0, intervalInMillisecs);
    }

    public static BattleFragment newInstance(Squad attackerSquad, Squad defenderSquad, BattleLog battleLog) {
        Bundle args = new Bundle();
        args.putSerializable(ATTACKER_SQUAD, new BattleSquad(attackerSquad));
        args.putSerializable(DEFENDER_SQUAD, new BattleSquad(defenderSquad));
        args.putSerializable(BATTLE_LOG, battleLog);

        BattleFragment fragment = new BattleFragment();
        fragment.setArguments(args);

        return fragment;
    }
}

