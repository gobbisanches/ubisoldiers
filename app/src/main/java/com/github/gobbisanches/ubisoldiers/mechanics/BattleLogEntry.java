package com.github.gobbisanches.ubisoldiers.mechanics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanches on 7/4/14.
 */
public class BattleLogEntry {
    private Type type;
    ;
    private List<String> params;
    ;

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

    public void getParsedBy(BattleLogParser parser) {
        switch (type) {
            case SHOOTING:
                shootingGetParsedBy(parser);
                break;
            case BATTLE_RESULT:
                battleResultGetParsedBy(parser);
                break;
            default:
                throw new RuntimeException("Invalid Log Entry Type " + type);
        }
    }

    private void battleResultGetParsedBy(BattleLogParser parser) {
        BattleResultType result = BattleResultType.valueOf(params.get(0));
        parser.parseBattleResultEntry(result);
    }

    private void shootingGetParsedBy(BattleLogParser parser) {
        List<String> params = new ArrayList<String>();

        int round = Integer.valueOf(params.get(0)).intValue();
        String shooterName = params.get(1);
        String targetName = params.get(2);
        int damage = Integer.valueOf(params.get(3)).intValue();

        parser.parseShootingEntry(round, shooterName, targetName, damage);
    }

    public enum Type {SHOOTING, BATTLE_RESULT}

    public enum BattleResultType {ATTACKER_WON, DEFENDER_WON, DRAW}
}

