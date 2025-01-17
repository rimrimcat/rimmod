package net.rimrim.rimmod.init;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.rimrim.rimmod.RimMod;

@EventBusSubscriber(modid = RimMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModCapabilities {


    @SubscribeEvent
    private static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerBlockEntity(
                Capabilities.ItemHandler.BLOCK,
                ModBlockEntities.INSERTER.get(),
                (be, side) -> be.getItemHandler());


    }
}
