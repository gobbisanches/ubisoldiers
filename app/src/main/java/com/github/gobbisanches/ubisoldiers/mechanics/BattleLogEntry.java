package com.github.gobbisanches.ubisoldiers.mechanics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanches on 7/4/14.
 */
public class BattleLogEntry {
    public enum Type { SHOOTING, BATTLE_RESULT };
    public enum BattleResultType { ATTACKER_WON, DEFENDER_WON, DRAW };

    private Type type;
    private List<String> params;

    private BattleLogEntry(Type type, List<String> params) {
        this.type = type;
        this.params = params;
    }

    public static BattleLogEntry createShootingEntry(int round, BattleUnit shooter,
                                                     BattleUnit target,
                                                     int damage) {
        List<String> params = new ArrayList<String>();

        params.add(Integer.toString(round));
        params.add(shooter.getSoldierName());
        params.add(target.getSoldierName());
        params.add(Integer.toString(damage));

        return new BattleLogEntry(BattleLogEntry.Type.SHOOTING, params);
    }

    public static BattleLogEntry createBattleResultEntry(
            BattleResultType battleResultType) {
        List<String> params = new ArrayList<String>();
        params.add(battleResultType.name());

        return new BattleLogEntry(Type.BATTLE_RESULT, params);
    }
}

