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
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.block.InserterBlock;
import net.rimrim.rimmod.blockentity.InserterBlockEntity;
import org.joml.Quaternionf;
import org.joml.Vector3f;

public class InserterBER implements BlockEntityRenderer<InserterBlockEntity> {
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

    public InserterBER(BlockEntityRendererProvider.Context context) {
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
            InserterBlockEntity blockEntity,
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
    public void render(InserterBlockEntity blockEntity,
                       float partialTick,
                       PoseStack poseStack,
                       MultiBufferSource bufferSource,
                       int packedLight,
                       int packedOverlay) {
        ItemStack stack = blockEntity.getItem(0);
//        if (stack.isEmpty()) return;

//        Minecraft minecraft = Minecraft.getInstance();
        Level level = blockEntity.getLevel();

        if (level == null) return;


        poseStack.pushPose();

        // Center
        poseStack.translate(0.5f, 0.5f, 0.5f);
        // Rotate upright to NORTH
        poseStack.rotateAround(new Quaternionf(0, 0, -1, 0), 0f, 0.5f, 0f);

        // Rotations will mess up X and Y!
        // Will make use of these factors when transforming
        float xFactor = -1;
        float yFactor = -1;
        float zFactor = 1;

        // Rotate according to blockstate
        InserterBlock.InserterState state = blockEntity.getState();
        Direction dir = blockEntity.getBlockState().getValue(InserterBlock.INSERT_DIRECTION);

        if (state.direction.getStep() == -1) {
            dir = dir.getOpposite();
        }

        if (dir == Direction.NORTH) {
            //
        }
        else if (dir == Direction.EAST) {
            poseStack.rotateAround(new Quaternionf(0, -0.71, 0, 0.71), 0f, 0.5f, 0f);
        }
        else if (dir == Direction.WEST) {
            poseStack.rotateAround(new Quaternionf(0, 0.71, 0, -0.71), 0f, 0.5f, 0f);
        }
        else if (dir == Direction.SOUTH) {
            poseStack.rotateAround(new Quaternionf(0, 1, 0, 0), 0f, 0.5f, 0f);
        }




        base.render(poseStack, bufferSource.getBuffer(RenderType.entityCutout(TEXTURE)), packedLight, packedOverlay);
        grabber_right.render(poseStack, bufferSource.getBuffer(RenderType.entityCutout(TEXTURE)), packedLight, packedOverlay);
        grabber_left.render(poseStack, bufferSource.getBuffer(RenderType.entityCutout(TEXTURE)), packedLight, packedOverlay);
        arm_1.render(poseStack, bufferSource.getBuffer(RenderType.entityCutout(TEXTURE)), packedLight, packedOverlay);
        arm_2.render(poseStack, bufferSource.getBuffer(RenderType.entityCutout(TEXTURE)), packedLight, packedOverlay);



        poseStack.popPose();

    }
}
