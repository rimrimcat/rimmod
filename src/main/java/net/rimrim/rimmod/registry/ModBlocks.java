package net.rimrim.rimmod.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.block.ExampleMenuBlock;
import net.rimrim.rimmod.block.TankBlock;

public class ModBlocks {

    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(RimMod.MODID);

    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block",
            BlockBehaviour.Properties.of().mapColor(MapColor.STONE));

//    public static final DeferredBlock<Block> TANK = BLOCKS.registerBlock(
//            "tank",
//            Block::new, // The factory that the properties will be passed into.
//            BlockBehaviour.Properties.of() // The properties to use.
//                    .strength(4f)
//                    .requiresCorrectToolForDrops()
//                    .sound(SoundType.STONE)
//    );


    public static final DeferredBlock<TankBlock> TANK = BLOCKS.register("tank",
            registryName -> new TankBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .strength(4f)
                            .requiresCorrectToolForDrops()
                            .sound(SoundType.STONE)
            ));

    public static final DeferredBlock<ExampleMenuBlock> EXAMPLE_MENU = BLOCKS.register("example_menu",
            registryName -> new ExampleMenuBlock(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .sound(SoundType.CALCITE)
            ));

    public static final DeferredBlock<Block> EXAMPLE_ORE = BLOCKS.register("example_ore", registryName ->
            new Block(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .destroyTime(3.0f)
                            .explosionResistance(10.0f)
                            .sound(SoundType.METAL)
                            .requiresCorrectToolForDrops()

            ));

    public static final DeferredBlock<Block> DEEPSLATE_EXAMPLE_ORE = BLOCKS.register("deepslate_example_ore", registryName ->
            new Block(
                    BlockBehaviour.Properties.of()
                            .setId(ResourceKey.create(Registries.BLOCK, registryName))
                            .destroyTime(3.0f)
                            .explosionResistance(10.0f)
                            .sound(SoundType.METAL)
                            .requiresCorrectToolForDrops()

            ));
    // Helper


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }


}
