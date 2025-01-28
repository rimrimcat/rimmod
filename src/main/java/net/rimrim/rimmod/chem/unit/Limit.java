package net.rimrim.rimmod.chem.unit;

public class Limit {
    public static final Limit NONE = new Limit(Type.NONE, 0);

    private final Type type;
    private final float value1;
    private final float value2;

    public Limit(Type type, float value1, float value2) {
        this.type = type;
        this.value1 = value1;
        this.value2 = value2;
    }

    public Limit(Type type, float value1) {
        this.type = type;
        this.value1 = value1;
        this.value2 = 0;
    }


    public boolean check(float otherValue) {
        return switch (this.type) {
            case GREATER_THAN -> otherValue > this.value1;
            case LESS_THAN -> otherValue < this.value1;
            case WITHIN -> otherValue >= this.value1 && otherValue <= this.value2;
            case NONE -> true;
        };
    }

    public enum Type {
        GREATER_THAN(1),
        LESS_THAN(-1),
        WITHIN(0),
        NONE(69)
        ;


        private final int id;

        private Type(int id) {
            this.id = id;
        }


    }

}
