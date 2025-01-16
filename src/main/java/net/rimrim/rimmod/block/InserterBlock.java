package net.rimrim.rimmod.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.rimrim.rimmod.blockentity.InserterBlockEntity;
import net.rimrim.rimmod.menu.InserterMenu;
import org.jetbrains.annotations.Nullable;

public class InserterBlock extends BaseEntityBlock {
    public static final MapCodec<InserterBlock> CODEC = simpleCodec(InserterBlock::new);
    public static final EnumProperty<Direction> FACING = BlockStateProperties.HORIZONTAL_FACING;
    protected static final VoxelShape SHAPE = Block.box(3.0, 0.0, 3.0, 12.0, 2.0, 12.0);

    public InserterBlock(BlockBehaviour.Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState()
                .setValue(FACING, Direction.NORTH));
    }


    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection());
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new InserterBlockEntity(pos, state);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rot) {
        return state.setValue(FACING, rot.rotate(state.getValue(FACING)));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.rotate(mirror.getRotation(state.getValue(FACING)));
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    public static VoxelShape getSHAPE() {
        return SHAPE;
    }

    @Override
    protected VoxelShape getOcclusionShape(BlockState state) {
        return SHAPE;
    }

    @Override
    protected VoxelShape getInteractionShape(BlockState state, BlockGetter level, BlockPos pos) {
        return SHAPE;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (!level.isClientSide() && player instanceof ServerPlayer sPlayer) {
            sPlayer.openMenu(
                    new SimpleMenuProvider(
                            (containerId, playerInventory, playerEntity) -> new InserterMenu(containerId, playerInventory),
                            Component.translatable("menu.title.rimmod.inserter_menu")
                    )
            );

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }

    @Override
    protected InteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        if (!level.isClientSide() && player instanceof ServerPlayer sPlayer) {
            BlockEntity be = level.getBlockEntity(pos);

            if (be instanceof InserterBlockEntity inserterEntity) {
                ItemStack istack = player.getItemInHand(hand);

                if (istack.isEmpty()) {
                    // Player is not holding item::
                    // Get item from block entity to player
                    ItemStack inserter_item = ((InserterBlockEntity) be).getItem(0);
                    int inserter_item_count = inserter_item.getCount();

                    if (inserter_item_count > 0) {
                        player.setItemInHand(hand, inserter_item);
                        inserterEntity.setItem(0, ItemStack.EMPTY);
                        inserterEntity.setChanged();
                    }
                } else {
                    // Player is holding item ::
                    // Set item from player to block entity

                    if (inserterEntity.getItem(0).isEmpty()) {
                        inserterEntity.setItem(0, istack);
                        player.setItemInHand(hand, ItemStack.EMPTY);
                        inserterEntity.setChanged();
                    }
                }

            }

            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;


    }
}
