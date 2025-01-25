package net.rimrim.rimmod.chem.unit;

import java.util.List;
import java.util.function.Function;

public record BaseFunction(
        int num_constants,
        Parameter[] inputs,
        Parameter output,
        Function<float[], Float> formula
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
        private Parameter[] inputs;
        private Parameter output;
        private Function<float[], Float> formula;

        public Builder constants(int num_constants) {
            this.num_constants = num_constants;
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

        public Builder formula(Function<float[], Float> formula) {
            this.formula = formula;
            return this;
        }


        public BaseFunction build() {
            return new BaseFunction(num_constants, inputs, output, formula);
        }


    }

}
