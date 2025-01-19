package net.rimrim.rimmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.block.DebugInserterBlock;
import net.rimrim.rimmod.block.properties.InserterState;
import net.rimrim.rimmod.blockentity.DebugInserterBlockEntity;
import net.rimrim.rimmod.client.utils.TransformMap;
import net.rimrim.rimmod.client.utils.TransformValue;
import org.joml.Quaternionf;
import org.joml.Vector3f;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DebugInserterBER implements BlockEntityRenderer<DebugInserterBlockEntity> {
    private final BlockEntityRendererProvider.Context context;

    public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath(RimMod.MODID, "ber/inserter"), "");
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(RimMod.MODID, "textures/ber/inserter.png");
    private final ModelPart root;
    private final ModelPart base;
    private final ModelPart grabber_right;
    private final ModelPart grabber_left;
    private final ModelPart arm_1;
    private final ModelPart arm_2;

    public static float gr_tx = 0;
    public static float gr_ty = 0;
    public static float gr_tz = 0;
    public static float gr_qx = 0;
    public static float gr_qy = -0.2f;
    public static float gr_qz = 0;
    public static float gr_qw = 0.98f;
    public static float gr_px = 0f;
    public static float gr_py = 5 / 16f;
    public static float gr_pz = 0f;


    // Map for default values
    public static final TransformMap tmap = new TransformMap();

    public DebugInserterBER(BlockEntityRendererProvider.Context context) {
        this.context = context;

        this.root = context.bakeLayer(LAYER_LOCATION);
        this.base = root.getChild("base");
        this.grabber_right = root.getChild("grabber_right");
        this.grabber_left = root.getChild("grabber_left");
        this.arm_1 = root.getChild("arm_1");
        this.arm_2 = root.getChild("arm_2");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition base = partdefinition.addOrReplaceChild("base", CubeListBuilder.create().texOffs(16, 21).addBox(-4.0F, -2.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(22, 13).addBox(2.0F, -2.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(22, 17).addBox(2.0F, -2.0F, 2.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(24, 0).addBox(-4.0F, -2.0F, -4.0F, 2.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-3.0F, -2.0F, -3.0F, 6.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(2.0F, -3.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(24, 4).addBox(-2.0F, -3.0F, 2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                .texOffs(0, 14).addBox(-3.0F, -3.0F, -3.0F, 1.0F, 1.0F, 6.0F, new CubeDeformation(0.0F))
                .texOffs(24, 21).addBox(-2.0F, -3.0F, -3.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition grabber_right = partdefinition.addOrReplaceChild("grabber_right", CubeListBuilder.create(), PartPose.offset(-1.0F, 18.5F, -5.9473F));

        PartDefinition gright_r1 = grabber_right.addOrReplaceChild("gright_r1", CubeListBuilder.create().texOffs(8, 21).addBox(-1.0F, -0.5F, -2.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition grabber_left = partdefinition.addOrReplaceChild("grabber_left", CubeListBuilder.create(), PartPose.offset(1.0F, 18.5F, -5.9473F));

        PartDefinition gleft_r1 = grabber_left.addOrReplaceChild("gleft_r1", CubeListBuilder.create().texOffs(0, 21).addBox(0.0F, -0.5F, -2.5F, 1.0F, 1.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition arm_1 = partdefinition.addOrReplaceChild("arm_1", CubeListBuilder.create(), PartPose.offset(0.0F, 22.0F, 0.0F));

        PartDefinition arm_1_r1 = arm_1.addOrReplaceChild("arm_1_r1", CubeListBuilder.create().texOffs(14, 13).addBox(-1.0F, -5.5F, -1.0F, 2.0F, 6.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, 0.0F, 0.3927F, 0.0F, 0.0F));

        PartDefinition arm_2 = partdefinition.addOrReplaceChild("arm_2", CubeListBuilder.create(), PartPose.offset(0.0F, 16.4672F, 0.0F));

        PartDefinition arm_2_r1 = arm_2.addOrReplaceChild("arm_2_r1", CubeListBuilder.create().texOffs(14, 7).addBox(-1.0F, 0.0328F, -4.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.0F, -2.294F, 0.3927F, 0.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 64);
    }

    public void renderItem(
            ItemStack stack,
            DebugInserterBlockEntity blockEntity,
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            Level level
    ) {
        BlockPos pos = blockEntity.getBlockPos().above();
        int light_pack = LightTexture.pack(
                level.getBrightness(LightLayer.BLOCK, pos),
                level.getBrightness(LightLayer.SKY, pos)
        );

        float downscale_factor = 4f;
        Vector3f itemOffset = blockEntity.getItemOffsetFromCenter().mul(downscale_factor); // Multiply by factor since it affects translation

        poseStack.pushPose();
        poseStack.translate(0.5, 0.5, 0.5); // Translate to middle of block
        poseStack.scale(1 / downscale_factor, 1 / downscale_factor, 1 / downscale_factor); // Make smaller
        poseStack.translate(itemOffset.x, itemOffset.y, itemOffset.z); // Translate to inserter hand

        this.context.getItemRenderer().renderStatic(
                stack,
                ItemDisplayContext.FIXED,
                light_pack,
                OverlayTexture.NO_OVERLAY,
                poseStack,
                bufferSource,
                level,
                0
        );
        poseStack.popPose();
    }

    @Override
    public void render(DebugInserterBlockEntity blockEntity,
                       float partialTick,
                       PoseStack poseStack,
                       MultiBufferSource bufferSource,
                       int packedLight,
                       int packedOverlay) {

        Level level = blockEntity.getLevel();
        if (level == null) return;

        InserterState state = blockEntity.getInserterState();
        Direction dir = blockEntity.getBlockState().getValue(DebugInserterBlock.INSERT_DIRECTION);
        long lastUpdateInterval = level.getGameTime() - blockEntity.getLastUpdateTime();
//        RimMod.LOGGER.info("lastUpdateInterval: " + lastUpdateInterval);


        if (state.direction.getStep() == -1) {
            dir = dir.getOpposite();
        }

        BlockPos pos = blockEntity.getBlockPos().above();
        int light_pack = LightTexture.pack(
                level.getBrightness(LightLayer.BLOCK, pos),
                level.getBrightness(LightLayer.SKY, pos)
        );

        // Rotate according to blockstate (DO IT AFTER ALL TRANSFORMS)

        render_other(poseStack, bufferSource, dir, light_pack, packedOverlay, lastUpdateInterval);

        render_grabber_right(poseStack, bufferSource, dir, light_pack, packedOverlay, lastUpdateInterval, state);

        render_grabber_left(poseStack, bufferSource, dir, light_pack, packedOverlay, lastUpdateInterval, state);

        render_arm1(poseStack, bufferSource, dir, light_pack, packedOverlay, lastUpdateInterval, state);

        render_arm2(poseStack, bufferSource, dir, light_pack, packedOverlay, lastUpdateInterval, state);


    }

    private void renderModelPart(
            PoseStack poseStack,
            MultiBufferSource bufferSource,
            Direction dir,
            int packedLight,
            int packedOverlay,
            ModelPart modelPart,
            Runnable customTransformations
    ) {
        // Common setup logic
        poseStack.pushPose();

        poseStack.translate(0.5f, 0.5f, 0.5f); // Generic translation
        poseStack.rotateAround(new Quaternionf(0, 0, -1, 0), 0f, 0.5f, 0f); // Align to block space

        // Apply custom transformations unique to this model part
        if (customTransformations != null) {
            customTransformations.run();
        }

        // Direction-based rotations
        if (dir == Direction.EAST) {
            poseStack.rotateAround(new Quaternionf(0, -0.71f, 0, 0.71f), 0f, 0.5f, 0f);
        } else if (dir == Direction.WEST) {
            poseStack.rotateAround(new Quaternionf(0, 0.71f, 0, -0.71f), 0f, 0.5f, 0f);
        } else if (dir == Direction.SOUTH) {
            poseStack.rotateAround(new Quaternionf(0, 1f, 0, 0), 0f, 0.5f, 0f);
        }

        // Render the model part
        modelPart.render(
                poseStack,
                bufferSource.getBuffer(RenderType.entityCutout(TEXTURE)),
                packedLight,
                packedOverlay
        );

        // Common teardown logic
        poseStack.popPose();
    }

    public void render_grabber_right(PoseStack poseStack, MultiBufferSource bufferSource, Direction dir, int packedLight, int packedOverlay, long update_interval, InserterState state) {
        renderModelPart(poseStack, bufferSource, dir, packedLight, packedOverlay, grabber_right, () -> {
            // x and y messed up btw
            // Custom transformations specific to grabber_right
            TransformValue vals = tmap.get(state, "grabber_right");

            Quaternionf q1 = new Quaternionf(0, 0, 0, 1);
            Quaternionf q2 = new Quaternionf(vals.qx, vals.qy, vals.qz, vals.qw);
            Quaternionf q1_q2 = q1.nlerp(q2, (float) update_interval / 20f);
            poseStack.rotateAround(q1_q2, vals.px, vals.py, vals.pz);
        });
    }

    public void render_grabber_left(PoseStack poseStack, MultiBufferSource bufferSource, Direction dir, int packedLight, int packedOverlay, long update_interval, InserterState state) {
        renderModelPart(poseStack, bufferSource, dir, packedLight, packedOverlay, grabber_left, () -> {
            // x and y messed up btw
            // Custom transformations specific to grabber_right
            TransformValue vals = tmap.get(state, "grabber_left");

            Quaternionf q1 = new Quaternionf(0, 0, 0, 1);
            Quaternionf q2 = new Quaternionf(vals.qx, vals.qy, vals.qz, vals.qw);
            Quaternionf q1_q2 = q1.nlerp(q2, (float) update_interval / 20f);
            poseStack.rotateAround(q1_q2, vals.px, vals.py, vals.pz);
        });
    }

    public void render_arm1(PoseStack poseStack, MultiBufferSource bufferSource, Direction dir, int packedLight, int packedOverlay, long update_interval, InserterState state) {
        renderModelPart(poseStack, bufferSource, dir, packedLight, packedOverlay, arm_1, () -> {
            // x and y messed up btw
            // Custom transformations specific to grabber_right
            TransformValue vals = tmap.get(state, "arm_1");

            Quaternionf q1 = new Quaternionf(0, 0, 0, 1);
            Quaternionf q2 = new Quaternionf(vals.qx, vals.qy, vals.qz, vals.qw);
            Quaternionf q1_q2 = q1.nlerp(q2, (float) update_interval / 20f);
            poseStack.rotateAround(q1_q2, vals.px, vals.py, vals.pz);
        });
    }

    public void render_arm2(PoseStack poseStack, MultiBufferSource bufferSource, Direction dir, int packedLight, int packedOverlay, long update_interval, InserterState state) {
        renderModelPart(poseStack, bufferSource, dir, packedLight, packedOverlay, arm_2, () -> {
            // x and y messed up btw
            // Custom transformations specific to grabber_right
            TransformValue vals = tmap.get(state, "arm_2");

            Quaternionf q1 = new Quaternionf(0, 0, 0, 1);
            Quaternionf q2 = new Quaternionf(vals.qx, vals.qy, vals.qz, vals.qw);
            Quaternionf q1_q2 = q1.nlerp(q2, (float) update_interval / 20f);
            poseStack.rotateAround(q1_q2, vals.px, vals.py, vals.pz);
        });
    }

    public void render_other(PoseStack poseStack, MultiBufferSource bufferSource, Direction dir, int packedLight, int packedOverlay, long update_interval) {
        renderModelPart(poseStack, bufferSource, dir, packedLight, packedOverlay, base, null);
    }

}
