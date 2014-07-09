package com.github.gobbisanches.ubisoldiers.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.github.gobbisanches.ubisoldiers.mechanics.*;

/**
 * Created by Sanches on 09/07/2014.
 */
public class BattleFragment extends Fragment {
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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        attackerSquad = (BattleSquad) getArguments().getSerializable(ATTACKER_SQUAD);
        defenderSquad = (BattleSquad) getArguments().getSerializable(DEFENDER_SQUAD);
        battleLog     = (BattleLog)   getArguments().getSerializable(BATTLE_LOG);

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

