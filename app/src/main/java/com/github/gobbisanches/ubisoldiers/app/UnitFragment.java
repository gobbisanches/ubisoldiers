package com.github.gobbisanches.ubisoldiers.app;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Sanches on 08/07/2014.
 */
public class UnitFragment extends Fragment {
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

        return v;
    }


}
