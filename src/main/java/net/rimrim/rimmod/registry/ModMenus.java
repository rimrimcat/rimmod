package net.rimrim.rimmod.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.menu.ExampleMenu;
import net.rimrim.rimmod.menu.TankMenu;

import java.util.function.Supplier;

public class ModMenus {

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(Registries.MENU, RimMod.MODID);

    public static final Supplier<MenuType<TankMenu>> TANK_MENU = MENU_TYPES.register(
            "tank_menu",
            () -> new MenuType<>(TankMenu::new, FeatureFlags.DEFAULT_FLAGS)
    );

    public static final Supplier<MenuType<ExampleMenu>> EXAMPLE_MENU = MENU_TYPES.register(
            "example_menu",
            () -> new MenuType<>(ExampleMenu::new, FeatureFlags.DEFAULT_FLAGS)
    );

    public static void register(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }
}
