package net.rimrim.rimmod.chem.enums;

import net.rimrim.rimmod.chem.unit.CompositeUnit;
import net.rimrim.rimmod.chem.unit.IUnit;
import net.rimrim.rimmod.chem.unit.Unit;

public enum VariableType {
    MOLECULAR_WEIGHT(0, CompositeUnit.G_PER_MOLE),


    TEMPERATURE(1000, Unit.TEMPERATURE.K),
    TEMPERATURE_SATURATION(1100, Unit.TEMPERATURE.K),
    PRESSURE(2000, Unit.PRESSURE.BAR),
    PRESSURE_SATURATION(2100, Unit.PRESSURE.BAR),
    ;

    public int id;
    public final IUnit defaultUnit;

    private VariableType(int id, IUnit defaultUnit) {
        this.id = id;
        this.defaultUnit = defaultUnit;
    }

    public VariableType renumber(int ind) {
        this.id += ind;
        return this;
    }

    public int id() {
        return this.id;
    }

    public IUnit defaultUnit() {
        return this.defaultUnit;
    }

}
