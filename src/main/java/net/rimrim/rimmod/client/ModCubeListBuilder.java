package net.rimrim.rimmod.client;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.core.Direction;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;
import java.util.List;
import java.util.Set;

@OnlyIn(Dist.CLIENT)
public class ModCubeListBuilder extends CubeListBuilder {
    private static final Set<Direction> ALL_VISIBLE = EnumSet.allOf(Direction.class);
    private final List<CubeDefinition> cubes = Lists.newArrayList();
    private float xTexOffs;
    private float yTexOffs;
    private boolean mirror;


    public ModCubeListBuilder texOffs(float xTexOffs, float yTexOffs) {
        this.xTexOffs = xTexOffs;
        this.yTexOffs = yTexOffs;
        return this;
    }

    public ModCubeListBuilder texOffs(double xTexOffs, double yTexOffs) {
        this.xTexOffs = (float) xTexOffs;
        this.yTexOffs = (float) yTexOffs;
        return this;
    }

    @Override
    public @NotNull ModCubeListBuilder texOffs(int xTexOffs, int yTexOffs) {
        this.xTexOffs = xTexOffs;
        this.yTexOffs = yTexOffs;
        return this;
    }

    public @NotNull List<CubeDefinition> getCubes() {
        return ImmutableList.copyOf(this.cubes);
    }

    public static ModCubeListBuilder create() {
        return new ModCubeListBuilder();
    }

    public @NotNull ModCubeListBuilder addBox(float originX, float originY, float originZ, float dimensionX, float dimensionY, float dimensionZ) {
        return (ModCubeListBuilder) super.addBox(originX, originY, originZ, dimensionX, dimensionY, dimensionZ);
    }
}

