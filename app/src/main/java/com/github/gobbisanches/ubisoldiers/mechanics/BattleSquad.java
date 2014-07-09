package com.github.gobbisanches.ubisoldiers.mechanics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Sanches on 29/06/2014.
 */
public class BattleSquad implements Serializable {
    private static final long serialVersionUID = 1L;
    private Squad squad;
    private List<BattleUnit> units;

    public BattleSquad(Squad squad) {
        this.squad = squad;
        this.units = new ArrayList<BattleUnit>();

        for(int i = 0; i < 3; ++i) {
            this.units.add(new BattleUnit(squad.getUnit(i)));
        }
    }

    public void removeDeadUnits() {
        List<BattleUnit> deadUnits = new ArrayList<BattleUnit>();

        for (int i = 0; i < units.size(); ++i) {
            if (units.get(i).isDead()) {
                deadUnits.add(units.get(i));
            }
        }

        units.removeAll(deadUnits);
    }

    public List<BattleUnit> getUnits() {
        return units;
    }

    public BattleUnit getRandomUnit(Random random) {
        return units.get(random.nextInt(units.size()));
    }

    public boolean isThereLivingUnits() {
        for (BattleUnit unit : units) {
            if (unit.isAlive()) {
                return true;
            }
        }

        return false;
    }

    public boolean isEveryoneDead() {
        return !isThereLivingUnits();
    }
}
