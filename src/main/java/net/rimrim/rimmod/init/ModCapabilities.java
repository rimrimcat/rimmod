package net.rimrim.rimmod.init;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.items.IItemHandler;
import net.rimrim.rimmod.RimMod;

@EventBusSubscriber(modid = RimMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModCapabilities {

    public static final BlockCapability<IItemHandler, Void> ITEM_HANDLER_NO_CONTEXT =
            BlockCapability.createVoid(
                    ResourceLocation.fromNamespaceAndPath(RimMod.MODID, "item_handler_no_context"),
                    IItemHandler.class);


    @SubscribeEvent
    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.INSERTER.get(),
                (be, side) -> be.getItemHandler());

        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.TANK.get(),
                (be, side) -> be.getItemHandler());

        event.registerBlockEntity(
                Capabilities.FluidHandler.BLOCK,
                ModBlockEntities.TANK.get(),
                (be, side) -> be.getFluidTank());


    }
}
