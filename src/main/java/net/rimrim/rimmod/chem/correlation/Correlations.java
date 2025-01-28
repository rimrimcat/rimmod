package net.rimrim.rimmod.chem.correlation;

import net.rimrim.rimmod.chem.correlation.type.PropertyCorrelation;
import net.rimrim.rimmod.chem.enums.VariableType;
import net.rimrim.rimmod.chem.unit.Limit;
import net.rimrim.rimmod.chem.unit.Parameter;
import net.rimrim.rimmod.chem.unit.Unit;

public class Correlations {
    public static PropertyCorrelation WATER_PSAT_ARDEN_BUCK = new PropertyCorrelation.Builder(Forms.ARDEN_BUCK)
            .constants(6.1121, 18.678, 234.5, 254.14)
            .input(new Parameter(VariableType.TEMPERATURE,
                    Unit.TEMPERATURE.C,
                    Limit.GREATER_THAN(0)))
            .output(new Parameter(VariableType.PRESSURE_SATURATION,
                    Unit.GENERAL.HECTO.attach(Unit.PRESSURE.PA)))
            .reference("https://en.wikipedia.org/wiki/Arden_Buck_equation")
            .build();


    public static PropertyCorrelation ICE_PSAT_ARDEN_BUCK = new PropertyCorrelation.Builder(Forms.ARDEN_BUCK)
            .constants(6.1115, 23.036, 333.7, 279.82)
            .input(new Parameter(VariableType.TEMPERATURE,
                    Unit.TEMPERATURE.C,
                    Limit.LESS_THAN(0)))
            .output(new Parameter(VariableType.PRESSURE_SATURATION,
                    Unit.GENERAL.HECTO.attach(Unit.PRESSURE.PA)))
            .reference("https://en.wikipedia.org/wiki/Arden_Buck_equation")
            .build();

    public static PropertyCorrelation WATER_PSAT_ANTOINE = new PropertyCorrelation.Builder(Forms.ANTOINE)
            .constants(16.3872, 3885.70, 230.170)
            .input(new Parameter(VariableType.TEMPERATURE,
                    Unit.TEMPERATURE.C,
                    Limit.WITHIN(0, 200)))
            .output(new Parameter(VariableType.PRESSURE_SATURATION,
                    Unit.GENERAL.KILO.attach(Unit.PRESSURE.PA)))
            .reference("SVAS 9th ed")
            .build();


}
