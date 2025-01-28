package net.rimrim.rimmod.chem.correlation.function;

import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.chem.correlation.function.base.IFunction;
import net.rimrim.rimmod.chem.enums.VariableType;

import java.util.EnumMap;

public class UnsetFunction implements IFunction {

    public UnsetFunction() {
    }

    public float evaluate(EnumMap<VariableType, Float> processVars) {
        RimMod.LOGGER.error("Unset Function has been called!");
        return 0;
    }
}
