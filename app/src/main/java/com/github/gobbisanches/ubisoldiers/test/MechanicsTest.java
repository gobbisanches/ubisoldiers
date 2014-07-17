package com.github.gobbisanches.ubisoldiers.test;

import android.location.Location;
import android.test.InstrumentationTestCase;
import android.util.Log;
import android.widget.Toast;
import com.github.gobbisanches.ubisoldiers.app.UosManager;
import com.github.gobbisanches.ubisoldiers.mechanics.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import static com.github.gobbisanches.ubisoldiers.mechanics.BattleLogEntry.BattleResultType.*;
import static com.github.gobbisanches.ubisoldiers.mechanics.BattleLogEntry.ShootingDirection;
import static com.github.gobbisanches.ubisoldiers.mechanics.BattleLogEntry.ShootingDirection.*;
import static com.github.gobbisanches.ubisoldiers.mechanics.ItemCollection.*;
import static com.github.gobbisanches.ubisoldiers.mechanics.ItemCollection.CM901;
import static com.github.gobbisanches.ubisoldiers.mechanics.ItemCollection.KEVLAR;

/**
 * Created by Sanches on 29/06/2014.
 */
public class MechanicsTest extends InstrumentationTestCase {
    public void testBattle() throws Exception {
        ArrayList<Unit> units = new ArrayList<Unit>();
        units.add(new Unit(PRIVATE, CM901, KEVLAR));
        units.add(new Unit(PRIVATE_FIRST_CLASS, CM901, KEVLAR));
        units.add(new Unit(SPECIALIST, CM901, KEVLAR));
        Squad attacker = new Squad(units);
        Squad defender = new Squad(units);

        BattleLog actual = Battle.performBattleAndReturnLog(new Random(0), attacker, defender);

        BattleSquad a = new BattleSquad(attacker);
        BattleSquad d = new BattleSquad(defender);
        BattleLog expected = new BattleLog();

        // Round 1
        expected.addShootingEntry(1, FROM_ATTACKER, a.getUnits().get(0), d.getUnits().get(0), 7);
        expected.addShootingEntry(1, FROM_ATTACKER, a.getUnits().get(1), d.getUnits().get(2), 9);
        expected.addShootingEntry(1, FROM_ATTACKER, a.getUnits().get(2), d.getUnits().get(2), 8);

        expected.addShootingEntry(1, FROM_DEFENDER, a.getUnits().get(0), d.getUnits().get(2), 5);
        expected.addShootingEntry(1, FROM_DEFENDER, a.getUnits().get(1), d.getUnits().get(2), 9);
        expected.addShootingEntry(1, FROM_DEFENDER, a.getUnits().get(2), d.getUnits().get(2), 15);

        // Round 2
        expected.addShootingEntry(2, FROM_ATTACKER, a.getUnits().get(0), d.getUnits().get(2), 4);
        expected.addShootingEntry(2, FROM_ATTACKER, a.getUnits().get(1), d.getUnits().get(0), 6);
        expected.addShootingEntry(2, FROM_ATTACKER, a.getUnits().get(2), d.getUnits().get(2), 13);

        expected.addShootingEntry(2, FROM_DEFENDER, a.getUnits().get(0), d.getUnits().get(1), 6);
        expected.addShootingEntry(2, FROM_DEFENDER, a.getUnits().get(1), d.getUnits().get(2), 9);
        expected.addShootingEntry(2, FROM_DEFENDER, a.getUnits().get(2), d.getUnits().get(2), 13);

        // Round 3
        expected.addShootingEntry(3, FROM_ATTACKER, a.getUnits().get(0), d.getUnits().get(0), 4);
        expected.addShootingEntry(3, FROM_ATTACKER, a.getUnits().get(1), d.getUnits().get(0), 11);

        expected.addShootingEntry(3, FROM_DEFENDER, a.getUnits().get(0), d.getUnits().get(0), 7);
        expected.addShootingEntry(3, FROM_DEFENDER, a.getUnits().get(1), d.getUnits().get(1), 10);
        expected.addShootingEntry(3, FROM_DEFENDER, a.getUnits().get(2), d.getUnits().get(1), 8);

        // Round 4
        expected.addShootingEntry(4, FROM_ATTACKER, a.getUnits().get(0), d.getUnits().get(0), 6);
        expected.addShootingEntry(4, FROM_ATTACKER, a.getUnits().get(1), d.getUnits().get(0), 11);

        expected.addShootingEntry(4, FROM_DEFENDER, a.getUnits().get(0), d.getUnits().get(1), 4);
        expected.addShootingEntry(4, FROM_DEFENDER, a.getUnits().get(1), d.getUnits().get(1), 7);
        expected.addShootingEntry(4, FROM_DEFENDER, a.getUnits().get(2), d.getUnits().get(1), 13);

        // Round 5
        expected.addShootingEntry(5, FROM_ATTACKER, a.getUnits().get(0), d.getUnits().get(1), 5);

        expected.addShootingEntry(5, FROM_DEFENDER, a.getUnits().get(1), d.getUnits().get(0), 6);
        expected.addShootingEntry(5, FROM_DEFENDER, a.getUnits().get(2), d.getUnits().get(0), 13);

        // Round 6
        expected.addShootingEntry(6, FROM_ATTACKER, a.getUnits().get(0), d.getUnits().get(2), 5);

        expected.addShootingEntry(6, FROM_DEFENDER, a.getUnits().get(1), d.getUnits().get(0), 11);
        expected.addShootingEntry(6, FROM_DEFENDER, a.getUnits().get(2), d.getUnits().get(0), 14);

        expected.addBattleResultEntry(DEFENDER_WON);

        assertEquals(expected, actual);
    }

    public void testSearch() throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2014, Calendar.JULY, 15);

        Location location = new Location("");
        location.setLatitude(-15.79446);
        location.setLongitude(-47.88267);
        location.setTime(calendar.getTimeInMillis());

        long wifiLevel = 57;

        ArrayList<Unit> units = new ArrayList<Unit>();
        units.add(new Unit(PRIVATE, CM901, KEVLAR));
        units.add(new Unit(PRIVATE_FIRST_CLASS, CM901, KEVLAR));
        units.add(new Unit(SPECIALIST, CM901, KEVLAR));
        General.setPlayerGeneral(new General(1, "Test General", new Squad(units)));

        SearchEngine searchEngine = new SearchEngine();
        Item actualItem = searchEngine.performSearch(new Random(0), 1.1);
        Item expectedItem = USAS12;

        assertEquals(expectedItem, actualItem);
    }
}
