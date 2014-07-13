package com.github.gobbisanches.ubisoldiers.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.github.gobbisanches.ubisoldiers.mechanics.BattleUnit;
import com.github.gobbisanches.ubisoldiers.mechanics.General;
import com.github.gobbisanches.ubisoldiers.mechanics.Unit;

/**
 * Created by Sanches on 13/07/2014.
 */
public class ArmyFragment extends Fragment {
    private static final String GENERAL = "com.github.gobbisanches.ubisoldier.app.ArmyFragment.General";

    private TextView generalNameView;
    private UnitFragment unit1Fragment;
    private UnitFragment unit2Fragment;
    private UnitFragment unit3Fragment;
    private FrameLayout unit1View;
    private FrameLayout unit2View;
    private FrameLayout unit3View;
    private FragmentManager fragmentManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(R.layout.army_layout, container, false);
        generalNameView = (TextView) v.findViewById(R.id.generalNameView);
        fragmentManager = getFragmentManager();
        unit1Fragment = createIfMissing(R.id.unit1View,
                General.getPlayerGeneral().getSquad().getUnit(0));
        unit2Fragment = createIfMissing(R.id.unit2View,
                General.getPlayerGeneral().getSquad().getUnit(1));
        unit3Fragment = createIfMissing(R.id.unit3View,
                General.getPlayerGeneral().getSquad().getUnit(2));
        unit1View = (FrameLayout) v.findViewById(R.id.unit1View);
        unit2View = (FrameLayout) v.findViewById(R.id.unit2View);
        unit3View = (FrameLayout) v.findViewById(R.id.unit3View);

        unit1View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customizeUnit(0);
            }
        });

        unit2View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customizeUnit(1);
            }
        });

        unit3View.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                customizeUnit(2);
            }
        });



        return v;
    }

    private void customizeUnit(int unitIndex) {
//        Intent intent = new Intent(ArmyFragment.this, UnitCustomizationActivity.class);
//        intent.putEx

        UnitCustomizationActivity.startForUnit(General.getPlayerGeneral(), unitIndex, getActivity());
    }


    private UnitFragment createIfMissing(int id, Unit unit) {
        UnitFragment fragment = (UnitFragment) fragmentManager.findFragmentById(id);
        if(fragment == null) {
            fragment = UnitFragment.newInstance(new BattleUnit(unit));
            fragmentManager.beginTransaction().add(id, fragment).commit();
        }

        return fragment;
    }

    public static ArmyFragment newInstance(General general) {
        Bundle args = new Bundle();
        args.putSerializable(GENERAL, general);

        ArmyFragment fragment = new ArmyFragment();
        fragment.setArguments(args);

        return fragment;
    }

    public void updateViews() {
        generalNameView.setText("General " + General.getPlayerGeneral().getName());
        unit1Fragment.updateUnitAndSetHpToFull(General.getPlayerGeneral().getSquad().getUnit(0));
        unit2Fragment.updateUnitAndSetHpToFull(General.getPlayerGeneral().getSquad().getUnit(1));
        unit3Fragment.updateUnitAndSetHpToFull(General.getPlayerGeneral().getSquad().getUnit(2));
    }
}

