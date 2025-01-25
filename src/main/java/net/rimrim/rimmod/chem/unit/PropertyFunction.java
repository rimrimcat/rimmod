package net.rimrim.rimmod.chem.unit;

import java.util.function.Function;

public class PropertyFunction {
    private float[] constants;
    private Parameter[] inputs;
    private Parameter output;
    private Function<float[], Float> formula;


    public PropertyFunction(float[] constants,
                            Parameter[] inputs,
                            Parameter output,
                            Function<float[], Float> formula) {
        this.constants = constants;
        this.inputs = inputs;
        this.output = output;
        this.formula = formula;
    }

    public float evaluate(float...inputs) {
        int total_args_to_function = this.constants.length + this.inputs.length;
        float[] args = new float[total_args_to_function];
        System.arraycopy(this.constants, 0, args, 0, this.constants.length);
        System.arraycopy(inputs, 0, args, this.constants.length, this.inputs.length);

        return formula.apply(args);
    }

}
