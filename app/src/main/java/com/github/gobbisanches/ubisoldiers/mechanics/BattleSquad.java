package com.github.gobbisanches.ubisoldiers.mechanics;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Sanches on 29/06/2014.
 */
public class BattleSquad {
    private final Squad squad;
    private List<Integer> unitHp;
    // unitHp has 3 elements. Each one stores the hp of the corresponding unit
    // with the same index the in the Squad

    public BattleSquad(Squad squad) {
        this.squad = squad;
        this.unitHp = new ArrayList<Integer>(3);

        for(int i = 0; i < 3; ++i) {
            this.unitHp.set(i, PolicyManager.getMechanicsPolicy().calculateHitPointsForUnit(squad.getUnit(i)));
        }
    }

    public void takeDamageForUnit(int unitIndex, int damage) {
        if((unitIndex < 0) || (unitIndex > 2)) {
            throw new RuntimeException("Index for a Unit in a Squad must be in 0..2, but it was" + unitIndex);
        }

        this.unitHp.set(unitIndex, this.unitHp.get(unitIndex) - damage);
    }

    public void takeDamageForRandomUnit(int damage) {

    }

    public void getRandomUnit(Random random) {

    }

    private boolean isUnitAlive(int index) {
        return this.unitHp.get(index) > 0;
    }

    private int getCountOfAliveUnits() {
        int aliveCount = 0;

        for (Integer i : this.unitHp) {

        }
    }

    public List<Integer> getRandomAliveUnitSequence(Random random, int sequenceLength) {

    }

    public List<Unit> getAliveUnits() {
        List<Unit> aliveUnits = new ArrayList<Unit>(0);

        for(int i = 0; i < 3; ++i) {
            if(isUnitAlive(i)) {
                aliveUnits.add(this.squad.getUnit(i));
            }
        }

        return aliveUnits;
    }
}
