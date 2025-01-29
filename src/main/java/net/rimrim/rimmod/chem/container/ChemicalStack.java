package net.rimrim.rimmod.chem.container;

import net.rimrim.rimmod.chem.enums.VariableType;
import net.rimrim.rimmod.chem.props.PureSpecies;
import net.rimrim.rimmod.chem.species.ModSpecies;

import java.util.EnumMap;

public class ChemicalStack {
    public static final ChemicalStack EMPTY = new ChemicalStack(ModSpecies.NONE, 0);

    private final PureSpecies chemical;
    private final EnumMap<VariableType, Float> processVars;

    public ChemicalStack(PureSpecies chemical, VariableType var, float value) {
        this.chemical = chemical;
        this.processVars = new EnumMap<>(VariableType.class);

        switch (var) {
            case MASS -> addMass(value);
            case MOLE ->  addMole(value);
            case VOLUME -> addVolume(value);
        }
    }

    public ChemicalStack(PureSpecies chemical, float mass) {
        this(chemical, VariableType.MASS, mass);
    }

    private float T() {
        return this.processVars.get(VariableType.TEMPERATURE);
    }

    private float P() {
        return this.processVars.get(VariableType.PRESSURE);
    }

    private float m() {
        return this.processVars.get(VariableType.MASS);
    }

    private float MW() {
        return this.chemical.MW();
    }

    private float rho() {
        return chemical.density(T(), P());
    }

    private void mapIncrement(VariableType varType, float value) {
        this.processVars.put(varType, this.processVars.get(varType) + value);
    }

    private void addMass(float mass) {
        // by default, kg
        mapIncrement(VariableType.MASS, mass);
        // Update other vars
        float new_m = m();
        processVars.put(VariableType.MOLE, new_m / MW());
        processVars.put(VariableType.VOLUME, new_m / rho());
    }

    private void addMole(float mole) {
        this.addMass(mole * MW());
    }

    private void addVolume(float volume) {
        this.addMass(volume * rho());
    }



}
