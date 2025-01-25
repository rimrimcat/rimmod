package net.rimrim.rimmod.chem.unit;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Operation {
    private final int[] ops;
    // 0 = add
    // 1 = multiply
    private final float[] values;
    private int index;


    public Operation(int num_ops) {
        ops = new int[num_ops];
        values = new float[num_ops];
        index = 0;
    }

    public Operation() {
        this(5);
    }

    public Operation add(float value) {
        ops[index] = 0;
        values[index] = value;
        index += 1;
        return this;
    }


    public Operation multiply(float value) {
        ops[index] = 1;
        values[index] = value;
        index += 1;
        return this;
    }

    public Operation invert() {
        int[] invertedOps = new int[ops.length];
        float[] invertedValues = new float[values.length];

        for (int i = ops.length - 1; i >= 0; i--) {
            switch (ops[i]) {
                case 0:
                    invertedOps[ops.length - 1 - i] = 0;
                    invertedValues[ops.length - 1 - i] = -values[i];
                    break;
                case 1:
                    invertedOps[ops.length - 1 - i] = 1;
                    invertedValues[ops.length - 1 - i] = 1 / values[i];
                    break;
            }
        }

        System.arraycopy(invertedOps, 0, ops, 0, ops.length);
        System.arraycopy(invertedValues, 0, values, 0, values.length);

        return this;
    }

    public Function<Float, Float> asFunction() {
        return x -> {
            float result = x;
            for (int i = 0; i < index; i++) {
                result = (ops[i] == 0) ? result + values[i] : result * values[i];
            }
            return result;
        };
    }


//    private final List<Function<Float, Float>> functions = new ArrayList<>();

//
//    public Operation ADD(float value) {
//        functions.add(x -> x + value);
//        return this;
//    }
//
//    public Operation MULTIPLY(float value) {
//        functions.add(x -> x * value);
//        return this;
//    }

//    public Function<Float, Float> asFunction() {
//        return functions.stream()
//                .reduce(Function.identity(), Function::andThen);
//    }

}