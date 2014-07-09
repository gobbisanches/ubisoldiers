package com.github.gobbisanches.ubisoldiers.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.github.gobbisanches.ubisoldiers.mechanics.BattleUnit;
import com.github.gobbisanches.ubisoldiers.mechanics.Unit;

/**
 * Created by Sanches on 08/07/2014.
 */
public class UnitFragment extends Fragment {
    private static final String BATTLE_UNIT = "com.github.gobbisanches.ubisoldier.app.UnitFragment.BattleUnit";

    private BattleUnit battleUnit;
    private TextView nameView;
    private TextView attackView;
    private TextView defenseView;
    private TextView hpView;
    private ImageView soldierImageView;
    private ImageView weaponImageView;
    private ImageView armorImageView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        battleUnit = (BattleUnit) getArguments().getSerializable(BATTLE_UNIT);
        if (battleUnit == null) {
            throw new RuntimeException("Invalid BattleUnit object in UnitFragment object");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.unit_layout, container, false);
        nameView = (TextView) v.findViewById(R.id.nameView);
        attackView = (TextView) v.findViewById(R.id.attackView);
        defenseView = (TextView) v.findViewById(R.id.defenseView);
        hpView = (TextView) v.findViewById(R.id.hpView);
        soldierImageView  = (ImageView) v.findViewById(R.id.soldierImageView);
        weaponImageView = (ImageView) v.findViewById(R.id.soldierImageView);
        armorImageView = (ImageView) v.findViewById(R.id.armorImageView);

        updateView();

        return v;
    }

    public static UnitFragment newInstance(BattleUnit battleUnit) {
        Bundle args = new Bundle();
        args.putSerializable(BATTLE_UNIT, battleUnit);

        UnitFragment fragment = new UnitFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public static UnitFragment newInstance(Unit unit) {
        Bundle args = new Bundle();
        args.putSerializable(BATTLE_UNIT, new BattleUnit(unit));

        UnitFragment fragment = new UnitFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public void takeDamage(int amount) {
        battleUnit.takeDamage(amount);
        updateView();
    }

    private void updateView() {
        nameView.setText(battleUnit.getUnit().getSoldier().getName());
        attackView.setText(valueOf(battleUnit.getUnit().getAttack()));
        defenseView.setText(valueOf(battleUnit.getUnit().getDefense()));
        hpView.setText(valueOf(battleUnit.getHp()));
    }

    private String valueOf(double value) {
        return String.valueOf((int) Math.floor(value));
    }
}
