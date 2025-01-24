package net.rimrim.rimmod.props;

import net.minecraft.core.Direction;

public class PhysicalPropertyHandler /*implements ITemperatureHandler */ /*, INBTSerializable<CompoundTag>*/ {
    protected float energy; // internal
    protected float temperature;
    protected float pressure;
    protected float mass;

    public PhysicalPropertyHandler() {
        this(298, 1);
    }

    public PhysicalPropertyHandler(float initTemp) {
        this(initTemp, 1);
    }

    public PhysicalPropertyHandler(float initTemp, float initPress) {
        // Back calculate energy from initial temp
        // U = rho*V*C*T

        this.mass = density(initTemp, initPress) * 1; // rho*V
        this.energy = mass * heat_capacity(initTemp, initPress) * initTemp;
        this.temperature = initTemp;
        this.pressure = initPress;
    }

    // Process Variables
    public float energy() {
        return this.energy;
    }

    public float temperature() {
        return this.temperature;
    }

    public float pressure() {
        return this.pressure;
    }

    public float volume() {
        return mass / density();
    }

    public float mass() {
        return mass;
    }

    public float area(Direction side) {
        return 1f; // m2
    }

    // Properties
    public float density() {
        return this.density(temperature, pressure);
    }

    public float heat_capacity() {
        return this.heat_capacity(temperature, pressure);
    }

    public float thermal_conductivity() {
        return this.thermal_conductivity(temperature, pressure);
    }

    public float density(float temp, float press) {
        return 7850f; // in kg/m3
    }

    public float heat_capacity(float temp, float press) {
        return 554f; // in J/kg-K
    }

    public float thermal_conductivity(float temp, float press) {
        return 80.2f; // in W/m-K
    }

    // Transfer Mechanisms
    public void updateTemperature(float newTemperature) {
        this.temperature = newTemperature;
        this.energy = mass * heat_capacity() * newTemperature;
    }

    public void conduct(PhysicalPropertyHandler otherHandler) {
        // Conduction should only work from high temp to low temp
        float T_A = this.temperature();
        float T_B = otherHandler.temperature();
        if (T_B > T_A) {
            otherHandler.conduct(this);
            return;
        }

        float thermal_res = 0.5f / this.thermal_conductivity() + 0.5f / otherHandler.thermal_conductivity();
        float flux = -(T_B - T_A) / thermal_res;

        float mC_A = this.mass() * this.heat_capacity();
        float mC_B = otherHandler.mass() * otherHandler.heat_capacity();

        this.updateTemperature(-flux * 0.1f / mC_A + T_A);
        otherHandler.updateTemperature(flux * 0.1f / mC_B + T_B);
    }

}
