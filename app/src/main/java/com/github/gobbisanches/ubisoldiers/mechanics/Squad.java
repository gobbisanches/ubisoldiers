package com.github.gobbisanches.ubisoldiers.mechanics;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sanches on 29/06/2014.
 */
public class Squad {
    private final ArrayList<Unit> units;

    public Squad(List<Unit> units) {
        if (units.size() != 3) {
            throw new RuntimeException("Squad must have exactly three units, but " + units.size() + " were provided");
        }

        for (Unit u : units) {
            if (u == null) {
                throw new RuntimeException("Squad must not have any units which are null");
            }
        }

        this.units = new ArrayList(units);
    }

    public Squad(Squad other) {
        this(other.units);
    }

    public void setUnit(int index, Unit unit) {
        if ((index < 0) || (index > 2)) {
            throw new RuntimeException("Invalid index for unit in squad: " + index);
        }

        this.units.set(index, unit);
    }

    public final Unit getUnit(int index) {
        if ((index < 0) || (index > 2)) {
            throw new RuntimeException("Invalid index for unit in squad: " + index);
        }

        return this.units.get(index);
    }
}
