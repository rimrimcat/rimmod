package net.rimrim.rimmod.client;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.client.renderer.InserterBER;
import net.rimrim.rimmod.client.screen.InserterScreen;
import net.rimrim.rimmod.init.ModBlockEntities;
import net.rimrim.rimmod.init.ModMenus;

@EventBusSubscriber(modid = RimMod.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientHandler {

    @SubscribeEvent
    public static void registerEntityRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(
                ModBlockEntities.INSERTER.get(),
                InserterBER::new
        );
        
    }

    @SubscribeEvent
    private static void registerScreens(RegisterMenuScreensEvent event) {
        event.register(ModMenus.INSERTER_MENU.get(), InserterScreen::new);
    }
}
