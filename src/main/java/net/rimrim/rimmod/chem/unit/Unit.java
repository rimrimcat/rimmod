package net.rimrim.rimmod.chem.unit;

import java.util.function.Function;

public class Unit {

    public enum TEMPERATURE {
        K(1, "Kelvin"),
        R(2, "Rankine"),
        C(3, "Celsius"),
        F(4, "Fahrenheit");

        public final int id;
        public final String name;

        private TEMPERATURE(int id, String name) {
            this.id = id;
            this.name = name;
        }

        private static int combine(int a, int b) {
            return a * 10 + b;
        }

        private static Function<Float, Float> MAP(int ab) {
            return switch (ab) {
                case 21 -> (x) -> {
                    return x / 1.8f;
                };
                case 31 -> (x) -> {
                    return x + 273.15f;
                };
                case 41 -> (x) -> {
                    return (x + 459.67f) / 1.8f;
                };
                case 12 -> (x) -> {
                    return 1.8f * x;
                };
                case 13 -> (x) -> {
                    return x - 273.15f;
                };
                case 14 -> (x) -> {
                    return x * 1.8f - 459.67f;
                };
                default -> null;
            };
        }


        public Function<Float, Float> convert(TEMPERATURE unit) {
            int a = this.id;
            int b = unit.id;

            // same unit
            if (a == b) return (x) -> {
                return x;
            };
            // one unit involves kelvin
            if (a == 1 || b == 1) return MAP(combine(a, b));
            // no kelvin
            return MAP(combine(1, b)).compose(MAP(combine(a, 1)));
        }


    }


}
