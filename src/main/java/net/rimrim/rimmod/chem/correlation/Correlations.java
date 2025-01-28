package net.rimrim.rimmod.chem.correlation;


import net.rimrim.rimmod.chem.correlation.function.base.BaseFunction;
import net.rimrim.rimmod.chem.correlation.function.base.FunctionInput;
import net.rimrim.rimmod.chem.enums.VariableType;
import net.rimrim.rimmod.chem.unit.Parameter;
import net.rimrim.rimmod.chem.correlation.function.PropertyFunction;
import net.rimrim.rimmod.chem.unit.Unit;

import java.util.function.Function;

public class Correlations {


//    public static PropertyFunction ARDEN_BUCK(float... params) {
//        Function<FunctionInput, Float> fcn = (F) -> {
//            return F.c(0) * (float) Math.exp((F.c(1) - F.T() / F.c(2)) * (F.T() / (F.c(3) + F.T())));
//        };
//
//        return new BaseFunction.Builder()
//                .constants(4)
//                .input(new Parameter(VariableType.TEMPERATURE, Unit.TEMPERATURE.C))
//                .output(new Parameter(VariableType.PRESSURE_SATURATION, Unit.GENERAL.KILO.attach(Unit.PRESSURE.PA)))
//                .formula(fcn)
//                .build()
//                .withParameters(params);
//    }


}
