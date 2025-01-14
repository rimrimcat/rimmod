package net.rimrim.rimmod.registry;

import net.neoforged.bus.api.IEventBus;

public class ModRegistry {
    public static void register(IEventBus eventBus) {
        ModItems.register(eventBus);
        ModBlocks.register(eventBus);
        ModTabs.register(eventBus);
        ModBlockEntities.register(eventBus);
        ModMenus.register(eventBus);
    }
}
