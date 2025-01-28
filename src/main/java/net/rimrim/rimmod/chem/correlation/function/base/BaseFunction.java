package net.rimrim.rimmod.chem.correlation.function.base;

import net.rimrim.rimmod.chem.correlation.function.PropertyFunction;
import net.rimrim.rimmod.chem.unit.Parameter;

import java.util.List;
import java.util.function.Function;

public record BaseFunction(
        int num_constants,
        Parameter[] inputs,
        Parameter output,
        Function<FunctionInput, Float> formula
) {


    public PropertyFunction withParameters(float[] consts) {
        float[] final_consts = new float[num_constants];

        for (int i = 0; i < final_consts.length; i++) {
            final_consts[i] = i < consts.length ? consts[i] : 0;
        }

        return new PropertyFunction(
                final_consts,
                inputs,
                output,
                formula
        );
    }


    public PropertyFunction withParameters(List<Float> consts) {
        float[] final_consts = new float[num_constants];

        for (int i = 0; i < final_consts.length; i++) {
            final_consts[i] = i < consts.size() ? consts.get(i) : 0;
        }

        return new PropertyFunction(
                final_consts,
                inputs,
                output,
                formula
        );
    }


    public static class Builder {
        private int num_constants;
        private Parameter[] inputs = null;
        private Parameter output;
        private Function<FunctionInput, Float> formula;

        public Builder constants(int num_constants) {
            this.num_constants = num_constants;
            return this;
        }

        public Builder input(Parameter param) {
            if (this.inputs == null) {
                this.inputs = new Parameter[]{param};
            }
            else {
                Parameter[] new_inputs = new Parameter[this.inputs.length + 1];
                System.arraycopy(this.inputs, 0, new_inputs, 0, this.inputs.length);
                new_inputs[this.inputs.length] = param;

                this.inputs = new_inputs;
            }
            return this;
        }

        public Builder inputs(Parameter... params) {
            this.inputs = new Parameter[params.length];
            System.arraycopy(params, 0, this.inputs, 0, params.length);
            return this;
        }

        public Builder inputs(List<Parameter> params) {
            this.inputs = new Parameter[params.size()];
            int i = 0;
            for (Parameter p : params) {
                this.inputs[i++] = p;
            }
            return this;
        }

        public Builder output(Parameter output) {
            this.output = output;
            return this;
        }

        public Builder formula(Function<FunctionInput, Float> formula) {
            this.formula = formula;
            return this;
        }


        public BaseFunction build() {
            return new BaseFunction(num_constants, inputs, output, formula);
        }


    }

}
