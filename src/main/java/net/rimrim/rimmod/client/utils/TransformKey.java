package net.rimrim.rimmod.client.utils;

import net.rimrim.rimmod.block.properties.InserterState;

import java.util.Objects;

public class TransformKey {
    private final InserterState state;
    private final String modelPart;

    public TransformKey(InserterState state, String modelPart) {
        this.state = state;
        this.modelPart = modelPart;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TransformKey key = (TransformKey) o;
        return state == key.state && Objects.equals(modelPart, key.modelPart);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state, modelPart);
    }
}
