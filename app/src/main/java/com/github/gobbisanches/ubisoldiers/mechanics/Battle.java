package com.github.gobbisanches.ubisoldiers.mechanics;

import java.util.List;
import java.util.Random;

/**
 * Created by Sanches on 29/06/2014.
 */
public class Battle {
    Random random;
    private final BattleSquad attacker;
    private final BattleSquad defender;

    public Battle(Random random, Squad attacker, Squad defender) {
        this.random = random;
        this.attacker = new BattleSquad(attacker);
        this.defender = new BattleSquad(defender);
    }

    private Unit getRandomUnitFromList(List<Unit> units) {
        units.get(random.nextInt(units.size()));
    }

    private void performBattleRound() {
        List<Unit> aliveAttackers = attacker.getAliveUnits();
        List<Unit> aliveDefenders = defender.getAliveUnits();

        for(Unit attackerUnit : aliveAttackers) {
            Unit defenderUnit = getRandomUnitFromList(aliveDefenders);

            Integer damage = PolicyManager.getMechanicsPolicy().calculateDamage(
                                   attackerUnit,
                                   defenderUnit);
            )
        }
    }
}
