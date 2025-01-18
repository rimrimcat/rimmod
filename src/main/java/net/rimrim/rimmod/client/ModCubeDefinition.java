package net.rimrim.rimmod.client;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDefinition;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.UVPair;
import net.minecraft.core.Direction;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.Set;

@OnlyIn(Dist.CLIENT)
public final class ModCubeDefinition {
    @Nullable
    private final String comment;
    private final Vector3f origin;
    private final Vector3f dimensions;
    private final CubeDeformation grow;
    private final boolean mirror;
    private final UVPair texCoord;
    private final UVPair texScale;
    private final Set<Direction> visibleFaces;

    public ModCubeDefinition(
            @Nullable String comment,
            float texCoordU,
            float texCoordV,
            float originX,
            float originY,
            float originZ,
            float dimensionX,
            float dimensionY,
            float dimensionZ,
            CubeDeformation grow,
            boolean mirror,
            float texScaleU,
            float texScaleV,
            Set<Direction> visibleFaces
    ) {
        this.comment = comment;
        this.texCoord = new UVPair(texCoordU, texCoordV);
        this.origin = new Vector3f(originX, originY, originZ);
        this.dimensions = new Vector3f(dimensionX, dimensionY, dimensionZ);
        this.grow = grow;
        this.mirror = mirror;
        this.texScale = new UVPair(texScaleU, texScaleV);
        this.visibleFaces = visibleFaces;
    }

}
