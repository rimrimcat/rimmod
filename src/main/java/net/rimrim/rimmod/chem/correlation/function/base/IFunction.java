package net.rimrim.rimmod.chem.correlation.function.base;

import net.rimrim.rimmod.chem.enums.VariableType;

import java.util.EnumMap;

public interface IFunction {

    float evaluate(EnumMap<VariableType, Float> processVars);
}
