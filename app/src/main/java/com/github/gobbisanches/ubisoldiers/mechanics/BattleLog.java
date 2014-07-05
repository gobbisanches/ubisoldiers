package com.github.gobbisanches.ubisoldiers.mechanics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanches on 7/4/14.
 */
public class BattleLog {
    private List<BattleLogEntry> entries = new ArrayList<BattleLogEntry>();

    public BattleLog() {
    }

    public void addShootingEntry(int round, BattleUnit shooter, BattleUnit target, int damage) {
        entries.add(BattleLogEntry.createShootingEntry(
                round, shooter, target, damage));
    }

    public void addBattleResultEntry(BattleLogEntry.BattleResultType battleResultType) {
        entries.add(BattleLogEntry.createBattleResultEntry(battleResultType));
    }
}
