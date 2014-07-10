package com.github.gobbisanches.ubisoldiers.mechanics;

/**
 * Created by sanches on 7/5/14.
 */
// Design Pattern: Visitor
// A BattleLogParser object is a visitor to the BattleLog and BattleLogEntry classes
public interface BattleLogParser {
    public void onStartParsing();

    public void onFinishParsing();

    public void parseShootingEntry(
            int round, BattleLogEntry.ShootingDirection direction, String shooterName,
            String targetName,
            int damage);

    public void parseBattleResultEntry(
            BattleLogEntry.BattleResultType battleResultType);
}
