package net.rimrim.rimmod.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.rimrim.rimmod.blockentity.InserterBlockEntity;
import org.joml.Vector3f;

public class InserterBER implements BlockEntityRenderer<InserterBlockEntity> {
    private final BlockEntityRendererProvider.Context context;

    public InserterBER(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(InserterBlockEntity blockEntity,
                       float partialTick,
                       PoseStack poseStack,
                       MultiBufferSource bufferSource,
                       int packedLight,
                       int packedOverlay) {
        ItemStack stack = blockEntity.getItem(0);
        if (stack.isEmpty()) return;

//        Minecraft minecraft = Minecraft.getInstance();
        Level level = blockEntity.getLevel();

        if (level == null) return;


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
}
