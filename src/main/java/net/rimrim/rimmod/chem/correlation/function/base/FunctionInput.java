package net.rimrim.rimmod.chem.correlation.function.base;

import net.rimrim.rimmod.chem.enums.VariableType;
import net.rimrim.rimmod.chem.unit.Parameter;

import java.util.EnumMap;
import java.util.function.Function;

public class FunctionInput {
    public final float[] c;

    public final EnumMap<VariableType, Float> convertedProcessVars;

    public FunctionInput(float[] c, EnumMap<VariableType, Float> processVars, Parameter[] inputs) {
        this.c = c;
        this.convertedProcessVars = new EnumMap<>(VariableType.class);

        for (Parameter input : inputs) {
            VariableType varType = input.variable();
            float processVarValue = processVars.get(varType);
            Function<Float, Float> converter = varType.defaultUnit.converter(input.correlationUnit());

            this.convertedProcessVars.put(varType, converter.apply(processVarValue));
        }
    }

    public float[] c() {
        return this.c;
    }

    public float c(int i) {
        return this.c[i];
    }

    public float T() {
        return convertedProcessVars.get(VariableType.TEMPERATURE);
    }

    public float P() {
        return convertedProcessVars.get(VariableType.PRESSURE);
    }


}
