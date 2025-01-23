package net.rimrim.rimmod.client.screen;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.IFluidTank;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.menu.TankMenu;

public class TankScreen extends AbstractContainerScreen<TankMenu> {
    private final ResourceLocation BACKGROUND_LOCATION = ResourceLocation.fromNamespaceAndPath(
            RimMod.MODID,
            "textures/gui/container/tank.png");

    private final Component label = Component.translatable(RimMod.MODID + ".container.inserter");

    public static final int imageWidth = 176;
    public static final int imageHeight = 166;

    public TankScreen(TankMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);

    }


    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        guiGraphics.blit(
                RenderType::guiTextured,
                BACKGROUND_LOCATION,
                this.leftPos, this.topPos,
                0, 0,
                this.imageWidth, this.imageHeight,
                256, 256);

        FluidTank tank = this.menu.getBlockEntity().getFluidTank();
        FluidStack fluidStack = tank.getFluid();

        if (fluidStack.isEmpty()) return;


        IClientFluidTypeExtensions fluidTypeExtensions = IClientFluidTypeExtensions.of(fluidStack.getFluid());
        ResourceLocation stillTexture = fluidTypeExtensions.getStillTexture();

//        TextureAtlasSprite sprite = this.minecraft.getTextureAtlas(TankMenu.BLOCK_ATLAS).apply(stillTexture);
        int tintColor = fluidTypeExtensions.getTintColor(fluidStack);


        int fluidHeight = getFluidHeight(tank);

        guiGraphics.blit(
                RenderType::guiTextured,
                stillTexture,
                this.leftPos + 100,
                this.topPos + 15 + (48 - fluidHeight),
                0,
                0,
                16,
                fluidHeight,
                16,
                16
        );
    }


    private static int getFluidHeight(IFluidTank tank) {
        return (48 * (tank.getFluidAmount() / tank.getCapacity()));
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        super.renderLabels(graphics, mouseX, mouseY);

        // Assume we have some Component 'label'
        // 'label' is drawn at 'labelX' and 'labelY'
        // The color is an ARGB value, if the alpha is less than 4, than the alpha is set to 255
        // The final boolean renders the drop shadow when true
        // graphics.drawString(this.font, this.label, this.labelX, this.labelY, 0x404040, false);

        // TEMPORARY STUFF TO SEE CONTENTS
        graphics.drawString(this.font, Component.literal("Fluid: %s".formatted(menu.getBlockEntity().getFluidTank().getFluidAmount())), 20, 20, 0x404040, false);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        this.renderBg(graphics, partialTick, mouseX, mouseY);
        super.render(graphics, mouseX, mouseY, partialTick);

        /*
         * This method is added by the container screen to render
         * the tooltip of the hovered slot.
         */
        this.renderTooltip(graphics, mouseX, mouseY);
    }

    @Override
    protected void containerTick() {
        super.containerTick();

        // Tick things here
    }

}
