package com.github.gobbisanches.ubisoldiers.mechanics;

import java.util.Random;

/**
 * Created by Sanches on 29/06/2014.
 */
public class Battle {
    private final BattleSquad attackerSquad;
    private final BattleSquad defenderSquad;
    private final Random random;
    private int currentRound;
    private final BattleLog log;

    public static BattleLog performBattleAndReturnLog(Random random, Squad attackerSquad, Squad defenderSquad) {
        Battle battle = new Battle(random, attackerSquad, defenderSquad);
        battle.performBattle();
        return battle.log;
    }

    private Battle(Random random, Squad attackerSquad, Squad defenderSquad) {
        this.random = random;
        this.attackerSquad = new BattleSquad(attackerSquad);
        this.defenderSquad = new BattleSquad(defenderSquad);
        this.currentRound = 0;
        this.log = new BattleLog();
    }

    private void performBattle() {
        while (bothSidesHaveLivingUnits()) {
            performBattleRound();
        }

        checkBattleResult();
    }

    private void checkBattleResult() {
        BattleLogEntry.BattleResultType result;

        if      (didAttackerWon()) { result = BattleLogEntry.BattleResultType.ATTACKER_WON; }
        else if (didDefenderWon()) { result = BattleLogEntry.BattleResultType.DEFENDER_WON; }
        else                       { result = BattleLogEntry.BattleResultType.DRAW; }

        log.addBattleResultEntry(result);
    }

    private boolean didAttackerWon() {
        return ((!attackerSquad.isEveryoneDead()) &&
                 defenderSquad.isEveryoneDead());
    }

    private boolean didDefenderWon() {
        return (attackerSquad.isEveryoneDead() &&
                (!defenderSquad.isEveryoneDead()));
    }

    private boolean bothSidesHaveLivingUnits() {
        return (attackerSquad.isThereLivingUnits() &&
                defenderSquad.isThereLivingUnits());
    }

    private void performBattleRound() {
        startNewRound();
        performShootings();
        removeDeadUnits();
    }

    private void startNewRound() {
        ++currentRound;
    }

    private void performShootings() {
        performAttackerShootingsForRound();
        performDefenderShootingsForRound();
    }

    private void performAttackerShootingsForRound() {
        performShootingsForRound(attackerSquad, defenderSquad);
    }

    private void performDefenderShootingsForRound() {
        performShootingsForRound(defenderSquad, attackerSquad);
    }

    private void performShootingsForRound(BattleSquad shooters, BattleSquad targets) {
        for (BattleUnit shooter : shooters.getUnits()) {
            BattleUnit target = targets.getRandomUnit(random);
            performShooting(shooter, target);
        }
    }

    private void performShooting(BattleUnit shooter, BattleUnit target) {
        int damage = calculateDamage(shooter, target);
        target.takeDamage(damage);
        log.addShootingEntry(currentRound, shooter, target, damage);
    }

    private int calculateDamage(BattleUnit attacker, BattleUnit defender) {
        return PolicyManager.getMechanicsPolicy().calculateDamage(
                random,
                attacker.getUnit(),
                defender.getUnit());
    }

    private void removeDeadUnits() {
        attackerSquad.removeDeadUnits();
        defenderSquad.removeDeadUnits();
    }
}
