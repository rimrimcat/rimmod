package net.rimrim.rimmod.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.blockstates.Variant;
import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureSlot;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.core.Direction;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.block.InserterBlock;
import net.rimrim.rimmod.registry.ModBlocks;
import net.rimrim.rimmod.registry.ModItems;

public class ModBlockModelProvider extends ModelProvider {
    public ModBlockModelProvider(PackOutput output) {
        super(output, RimMod.MODID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.EXAMPLE_ITEM.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.EXAMPLE_RAW.get(), ModelTemplates.FLAT_ITEM);
        // Generate files


        blockModels.createTrivialCube(ModBlocks.EXAMPLE_BLOCK.get());

//        blockModels.createTrivialCube(ModBlocks.TANK.get());
        blockModels.createHorizontallyRotatedBlock(ModBlocks.TANK.get(),
                TexturedModel.ORIENTABLE_ONLY_TOP.updateTexture(
                        mapping ->
                                mapping.put(TextureSlot.SIDE, this.modLocation("block/tank_side"))
                                        .put(TextureSlot.FRONT, this.modLocation("block/tank_side"))
                                        .put(TextureSlot.TOP, this.modLocation("block/tank_top"))
                                        .put(TextureSlot.BOTTOM, this.modLocation("block/tank_bottom"))
                )
        );


        blockModels.createGenericCube(ModBlocks.EXAMPLE_MENU.get());

        blockModels.createTrivialCube(ModBlocks.EXAMPLE_ORE_ON.get());
        ResourceLocation example_ore_off = TexturedModel.CUBE.create(ModBlocks.EXAMPLE_ORE.get(), blockModels.modelOutput);
        ResourceLocation example_ore_on = example_ore_off.withSuffix("_on");
        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.multiVariant(ModBlocks.EXAMPLE_ORE.get())
                        .with(
                                PropertyDispatch.property(BlockStateProperties.LIT)
                                        .select(false, Variant.variant().with(VariantProperties.MODEL, example_ore_off))
                                        .select(true, Variant.variant().with(VariantProperties.MODEL, example_ore_on))
                        )
        );


        blockModels.createTrivialCube(ModBlocks.DEEPSLATE_EXAMPLE_ORE.get());

//        RimMod.LOGGER.error("WILL START GENERATING BLOCKSTATE FOR INSERTER");
//        ResourceLocation inserter = ResourceLocation.fromNamespaceAndPath(RimMod.MODID, "block/inserter");
//        blockModels.blockStateOutput.accept(
//                MultiVariantGenerator.multiVariant(ModBlocks.INSERTER.get(),
//                                Variant.variant().with(VariantProperties.MODEL, inserter))
//                        .with(
//                                PropertyDispatch.property(InserterBlock.FACING)
//                                .select(Direction.NORTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R0))
//                                .select(Direction.SOUTH, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
//                                .select(Direction.WEST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
//                                .select(Direction.EAST, Variant.variant().with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
//                        ));
//        RimMod.LOGGER.error("DONE GENERATING BLOCKSTATE FOR INSERTER");
        blockModels.createGenericCube(ModBlocks.INSERTER.get());

    }


}
