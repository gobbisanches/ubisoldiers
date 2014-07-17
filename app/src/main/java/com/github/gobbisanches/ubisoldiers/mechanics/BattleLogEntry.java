package com.github.gobbisanches.ubisoldiers.mechanics;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sanches on 7/4/14.
 */
public class BattleLogEntry implements Serializable {
    public enum ShootingDirection { FROM_ATTACKER, FROM_DEFENDER };
    private static final long serialVersionUID = 1L;
    private Type type;
    private List<String> params;

    private BattleLogEntry(Type type, List<String> params) {
        this.type = type;
        this.params = params;
    }

    public static BattleLogEntry createShootingEntry(int round, ShootingDirection direction,
                                                     BattleUnit shooter,
                                                     BattleUnit target,
                                                     int damage) {
        List<String> params = new ArrayList<String>();

        params.add(Integer.toString(round));
        params.add(direction.name());
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
        int round = Integer.valueOf(params.get(0)).intValue();
        ShootingDirection direction = ShootingDirection.valueOf(params.get(1));
        String shooterName = params.get(2);
        String targetName = params.get(3);
        int damage = Integer.valueOf(params.get(4)).intValue();

        parser.parseShootingEntry(round, direction, shooterName, targetName, damage);
    }

    public enum Type {SHOOTING, BATTLE_RESULT}

    public enum BattleResultType {ATTACKER_WON, DEFENDER_WON, DRAW}

    @Override
    public String toString() {
        return "BattleLogEntry{" +
                "type=" + type +
                ", params=" + params +
                "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BattleLogEntry)) return false;

        BattleLogEntry that = (BattleLogEntry) o;

        if (!params.equals(that.params)) return false;
        if (type != that.type) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = type.hashCode();
        result = 31 * result + params.hashCode();
        return result;
    }
}

