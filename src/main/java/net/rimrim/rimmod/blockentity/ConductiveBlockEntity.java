package net.rimrim.rimmod.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.rimrim.rimmod.init.ModBlockEntities;

public class ConductiveBlockEntity extends BlockEntity {

    public ConductiveBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CONDUCTIVE_BLOCK.get(), pos, state);
    }
}
