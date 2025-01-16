package net.rimrim.rimmod.registry;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.rimrim.rimmod.RimMod;

@EventBusSubscriber(modid = RimMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModRegistry {
    public static void register(IEventBus eventBus) {
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModTabs.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModMenus.register(eventBus);
    }

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
//        event.registerBlockEntityRenderer(
//                // The block entity type to register the renderer for.
//                ModBlockEntities.INSERTER.get(),
//                // A function of BlockEntityRendererProvider.Context to BlockEntityRenderer.
//                InserterBlockEntityRenderer::new
//        );
    }
}
