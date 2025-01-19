package net.rimrim.rimmod.block.properties;

import net.minecraft.core.Direction;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum InserterState implements StringRepresentable {
    WAIT_SOURCE(0, Direction.AxisDirection.NEGATIVE, "wait_source"), // wait for item
    TAKING(1, Direction.AxisDirection.NEGATIVE, "taking"),
    TRANSFERRING_1(2, Direction.AxisDirection.NEGATIVE, "transferring_1"), //
    TRANSFERRING_2(3, Direction.AxisDirection.POSITIVE, "transferring_2"),
    WAIT_DESTINATION(4, Direction.AxisDirection.POSITIVE, "wait_destination"),
    INSERTING(5, Direction.AxisDirection.POSITIVE, "inserting"),
    RETURNING_1(6, Direction.AxisDirection.POSITIVE, "returning_1"), //
    RETURNING_2(7, Direction.AxisDirection.NEGATIVE, "returning_2");

    public final int stateIndex;
    public final String name;
    public final Direction.AxisDirection direction;

    private InserterState(
            int stateIndex, Direction.AxisDirection direction, String name
    ) {
        this.stateIndex = stateIndex;
        this.direction = direction;
        this.name = name;
    }

    @Override
    public @NotNull String getSerializedName() {
        return this.name;
    }

    public boolean equals(InserterState other) {
        return this.stateIndex == other.stateIndex;
    }
}
