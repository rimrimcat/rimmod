package net.rimrim.rimmod.chem;

import net.rimrim.rimmod.chem.enums.MatterState;

public abstract class PureDependentProperty {


    public float boiling_point(float pressure) {
        return -1;
    }

    public MatterState state(float temperature, float pressure) {
        return null;
    }


}
