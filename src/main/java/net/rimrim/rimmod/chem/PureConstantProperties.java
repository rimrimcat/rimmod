package net.rimrim.rimmod.chem;

public abstract class PureConstantProperties {

    private float molecular_weight;
    private float accentric_factor;
    private float critical_temperature;
    private float critical_pressure;
    private float critical_molar_volume;
    private float critical_compressibility_factor;

    public float MW() {
        return this.molecular_weight;
    }

    public float molecular_weight() {
        return this.molecular_weight;
    }

    public float w() {
        return this.accentric_factor;
    }

    public float accentric_factor() {
        return this.accentric_factor;
    }

    public float Tc() {
        return this.critical_temperature;
    }

    public float critical_temperature() {
        return this.critical_temperature;
    }

    public float Pc() {
        return this.critical_pressure;
    }

    public float critical_pressure() {
        return this.critical_pressure;
    }

    public float Vc() {
        return this.critical_molar_volume;
    }

    public float critical_molar_volume() {
        return this.critical_molar_volume;
    }

    public float Zc() {
        return this.critical_compressibility_factor;
    }

    public float critical_compressibility_factor() {
        return this.critical_compressibility_factor;
    }

}
