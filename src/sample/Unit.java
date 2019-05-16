package sample;

import java.io.Serializable;
import java.util.ArrayList;

public class Unit implements Serializable, Comparable<Unit> {
    private static final long serialVersionUID = 5092453297359533075L;
    private String unitName, unitDescription;
    private ArrayList<InOut> inOuts;
    public Unit(String unitName) {
        this.unitName = unitName;
        inOuts = new ArrayList<>();
    }
    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public String getUnitDescription() {
        return unitDescription;
    }

    public void setUnitDescription(String unitDescription) {
        this.unitDescription = unitDescription;
    }

    public ArrayList<InOut> getInOuts() {
        return inOuts;
    }

    @Override
    public String toString() { return unitName;}

    @Override
    public int compareTo(Unit compare) {
        return this.unitName.compareTo(compare.unitName);
    }
}

