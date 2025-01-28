package net.rimrim.rimmod.chem.correlation.function;

import net.rimrim.rimmod.chem.correlation.function.base.IFunction;
import net.rimrim.rimmod.chem.enums.VariableType;

import java.util.EnumMap;

public class ConstantProperty implements IFunction {
    private final float value;
    private String source = "no ref";

    public ConstantProperty(float value) {
        this.value = value;
    }

    public ConstantProperty withReference(String ref) {
        this.source = ref;
        return this;
    }


    public float evaluate(EnumMap<VariableType, Float> processVars) {
        return value;
    }
}
