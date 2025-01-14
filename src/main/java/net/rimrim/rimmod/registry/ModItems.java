package net.rimrim.rimmod.registry;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rimrim.rimmod.RimMod;

public class ModItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(RimMod.MODID);

    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem("example_item",
            new Item.Properties().food(
                    new FoodProperties.Builder()
                            .alwaysEdible()
                            .nutrition(1)
                            .saturationModifier(2f)
                            .build()));

    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", ModBlocks.EXAMPLE_BLOCK);
    public static final DeferredItem<BlockItem> TANK = ITEMS.registerSimpleBlockItem("tank", ModBlocks.TANK);
    public static final DeferredItem<BlockItem> EXAMPLE_MENU = ITEMS.registerSimpleBlockItem("example_menu", ModBlocks.EXAMPLE_MENU);
    public static final DeferredItem<BlockItem> EXAMPLE_ORE = ITEMS.registerSimpleBlockItem("example_ore", ModBlocks.EXAMPLE_ORE);
    public static final DeferredItem<BlockItem> DEEPSLATE_EXAMPLE_ORE = ITEMS.registerSimpleBlockItem("deepslate_example_ore", ModBlocks.DEEPSLATE_EXAMPLE_ORE);

    public static final DeferredItem<Item> EXAMPLE_RAW = ITEMS.registerSimpleItem("example_raw");

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
