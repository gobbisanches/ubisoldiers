package com.github.gobbisanches.ubisoldiers.app;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.github.gobbisanches.ubisoldiers.mechanics.*;

import java.util.Random;

/**
 * Created by Sanches on 13/07/2014.
 */
public class ArmyFragment extends Fragment {
    private static final String GENERAL = "com.github.gobbisanches.ubisoldier.app.ArmyFragment.General";

    private TextView generalNameView;
    private Button procurementButton;
    private Button battleButton;
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
        procurementButton = (Button) v.findViewById(R.id.procurementButton);
        battleButton = (Button) v.findViewById(R.id.battleButton);
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

        procurementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performProcurement();
            }
        });
        battleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performBattle();
            }
        });
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

    private void performBattle() {
        Long randomSeed = UosManager.getInstance().performBattle(General.getPlayerGeneral().getId(), 2);
        Random random = new Random(randomSeed.longValue());
        BattleLog battleLog = Battle.performBattleAndReturnLog(random, General.getPlayerGeneral().getSquad(),
                new Squad(General.getPlayerGeneral().getSquad()));
        BattleActivity.startForBattle(
                General.getPlayerGeneral().getSquad(),
                General.getPlayerGeneral().getSquad(),
                battleLog,
                getActivity());
    }

    private void performProcurement() {
        SearchEngine searchEngine = new SearchEngine();
        if (searchEngine.hasPlayerNoMissingItems()) {
            Toast.makeText(getActivity(), "Sir, there is no item left for you to acquire", Toast.LENGTH_LONG).show();
        } else {
            int generalId = General.getPlayerGeneral().getId();
            Location location = UosManager.getInstance().getLocation();
            long wifiLevel = UosManager.getInstance().getWifiSignalStrength();
            UosManager.SearchParameters params =
                    UosManager.getInstance().performSearch(generalId, location, wifiLevel);

            Item newItem = new SearchEngine().performSearch(new Random(params.getRandomSeed()), params.getModifier());

            General.getPlayerGeneral().addItem(newItem);
            Toast.makeText(getActivity(), "Sir, you managed to acquire " + newItem.getName(), Toast.LENGTH_LONG).show();
        }
    }

    private void customizeUnit(int unitIndex) {
//        Intent intent = new Intent(ArmyFragment.this, UnitCustomizationActivity.class);
//        intent.putEx

        UnitCustomizationActivity.startForUnit(General.getPlayerGeneral(), unitIndex, getActivity());
    }


    private UnitFragment createIfMissing(int id, Unit unit) {
        UnitFragment fragment = (UnitFragment) fragmentManager.findFragmentById(id);
        if (fragment == null) {
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

