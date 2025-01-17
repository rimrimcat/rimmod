package net.rimrim.rimmod.init;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.client.screen.InserterScreen;

@EventBusSubscriber(modid = RimMod.MODID, bus = EventBusSubscriber.Bus.MOD)
public class ModRegistry {
    public static void register(IEventBus eventBus) {
        ModDataComponents.register(eventBus);

        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModBlockEntities.register(eventBus);

        ModMenus.register(eventBus);

        ModTabs.register(eventBus);
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

    @SubscribeEvent
    private static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.INSERTER_MENU.get(), InserterScreen::new);
    }
}
