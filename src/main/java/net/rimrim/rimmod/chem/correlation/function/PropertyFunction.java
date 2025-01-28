package net.rimrim.rimmod.chem.correlation.function;

import net.rimrim.rimmod.chem.correlation.function.base.BaseFunction;
import net.rimrim.rimmod.chem.correlation.function.base.FunctionInput;
import net.rimrim.rimmod.chem.correlation.function.base.IFunction;
import net.rimrim.rimmod.chem.enums.VariableType;
import net.rimrim.rimmod.chem.unit.Parameter;

import java.util.EnumMap;
import java.util.List;
import java.util.function.Function;

public class PropertyFunction implements IFunction {
    private final float[] constants;
    private final Parameter[] inputs;
    private final Parameter output;
    private final Function<FunctionInput, Float> formula;

    private String source = "no ref";


    public PropertyFunction(float[] constants,
                            Parameter[] inputs,
                            Parameter output,
                            Function<FunctionInput, Float> formula) {
        this.constants = constants;
        this.inputs = inputs;
        this.output = output;
        this.formula = formula;
    }

    public PropertyFunction withReference(String ref) {
        this.source = ref;
        return this;
    }



    private void validateInputOutput(FunctionInput fInp, float output) {

    }

    public float evaluate(EnumMap<VariableType, Float> processVars) {
        FunctionInput inp = new FunctionInput(this.constants, processVars, this.inputs);

        float corrOutput = formula.apply(inp);
        Function<Float,Float> outputConv = output.correlationUnit().converter(output.variable().defaultUnit());

        return outputConv.apply(corrOutput);
    }

}
