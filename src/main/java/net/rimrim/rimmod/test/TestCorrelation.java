package net.rimrim.rimmod.test;

import net.rimrim.rimmod.chem.correlation.Correlations;
import net.rimrim.rimmod.chem.correlation.type.PropertyCorrelation;

public class TestCorrelation {

    public static void main(String[] args) {
        System.out.println("=================================");
        System.out.println("        PERFORMING TESTS         ");
        System.out.println("=================================");

        // Try to evaluate PSAT
        System.out.println("");
        PropertyCorrelation buck = Correlations.WATER_PSAT_ARDEN_BUCK;
        PropertyCorrelation antoine = Correlations.WATER_PSAT_ANTOINE;

        for (int i = 0; i <= 20; i++) {
            float T = i * 10f;
            System.out.println("T = " + T + " C | BUCK = " + buck.evaluate(T + 273.15f) + " | ANTOINE = " + antoine.evaluate(T + 273.15f) );
        }


        System.out.println("=================================");
        System.out.println("             DONE!               ");
        System.out.println("=================================");

    }
}
