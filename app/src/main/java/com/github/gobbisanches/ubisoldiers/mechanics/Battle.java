package com.github.gobbisanches.ubisoldiers.mechanics;

import java.util.Random;

/**
 * Created by Sanches on 29/06/2014.
 */
public class Battle {
    private final BattleSquad attackerSquad;
    private final BattleSquad defenderSquad;
    Random random;

    public Battle(Random random, Squad attackerSquad, Squad defenderSquad) {
        this.random = random;
        this.attackerSquad = new BattleSquad(attackerSquad);
        this.defenderSquad = new BattleSquad(defenderSquad);
    }

    public void performBattle() {
        while (bothSidesHaveLivingUnits()) {
            performBattleRound();
        }
    }

    private boolean bothSidesHaveLivingUnits() {
        return (attackerSquad.isThereLivingUnits() &&
                defenderSquad.isThereLivingUnits());
    }

    private void performBattleRound() {
        performShootings();
        removeDeadUnits();
    }

    private void performShootings() {
        performAttackerShootingsForRound();
        performDefenderShootingsForRound();
    }

    void performAttackerShootingsForRound() {
        performShootingsForRound(attackerSquad, defenderSquad);
    }

    void performDefenderShootingsForRound() {
        performShootingsForRound(defenderSquad, attackerSquad);
    }

    private void performShootingsForRound(BattleSquad shooters, BattleSquad targets) {
        for (BattleUnit shooter : shooters.getUnits()) {
            BattleUnit target = targets.getRandomUnit(random);
            int damage = calculateDamage(shooter, target);
            target.takeDamage(damage);
        }
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
