package net.rimrim.rimmod.datagen;

import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.data.PackOutput;
import net.rimrim.rimmod.RimMod;
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
        blockModels.createGenericCube(ModBlocks.TANK.get());
        blockModels.createGenericCube(ModBlocks.EXAMPLE_MENU.get());
        blockModels.createGenericCube(ModBlocks.EXAMPLE_ORE.get());
        blockModels.createGenericCube(ModBlocks.DEEPSLATE_EXAMPLE_ORE.get());
    }
}
