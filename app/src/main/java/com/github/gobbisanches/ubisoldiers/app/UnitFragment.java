package com.github.gobbisanches.ubisoldiers.app;

import android.app.Fragment;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.github.gobbisanches.ubisoldiers.mechanics.BattleUnit;
import com.github.gobbisanches.ubisoldiers.mechanics.Unit;

/**
 * Created by Sanches on 08/07/2014.
 */
public class UnitFragment extends Fragment {
    public enum State { SHOOTING, TAKING_DAMAGE, NORMAL }
    private static final String BATTLE_UNIT = "com.github.gobbisanches.ubisoldier.app.UnitFragment.BattleUnit";

    private BattleUnit battleUnit;
    private TextView nameView;
    private TextView attackView;
    private TextView defenseView;
    private TextView hpView;
    private ImageView soldierImageView;
    private ImageView weaponImageView;
    private ImageView armorImageView;
    private State state;
    private LinearLayout linearLayout;

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
        linearLayout = (LinearLayout) v.findViewById(R.id.unit_linear_layout);
        state = State.NORMAL;

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

    public void setState(State state) {
        this.state = state;
        updateView();
    }

    public String getSoldierName() {
        return battleUnit.getUnit().getSoldier().getName();
    }

    private void updateView() {
        nameView.setText(battleUnit.getUnit().getSoldier().getName());
        attackView.setText(valueOf(battleUnit.getUnit().getAttack()));
        defenseView.setText(valueOf(battleUnit.getUnit().getDefense()));
        hpView.setText(valueOf(battleUnit.getHp()));
        switch (state) {
            case NORMAL: linearLayout.setBackgroundResource(R.drawable.unit_background1);
                break;
            case SHOOTING: linearLayout.setBackgroundResource(R.drawable.unit_background_attack1);
                break;
            case TAKING_DAMAGE: linearLayout.setBackgroundResource(R.drawable.unit_background_damage1);
                break;
            default: throw new RuntimeException("Invalid state for UnitFragment object");
        }
        if(battleUnit.isDead()) {
            nameView.setTextColor(Color.RED);
        } else {
            nameView.setTextColor(Color.BLACK);
        }
    }

    private String valueOf(double value) {
        return String.valueOf((int) Math.floor(value));
    }
}
