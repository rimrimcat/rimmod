package net.rimrim.rimmod.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Position;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.block.InserterBlock;
import net.rimrim.rimmod.init.ModBlockEntities;
import net.rimrim.rimmod.menu.InserterMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.Optional;

public class InserterBlockEntity extends BlockEntity implements MenuProvider {
    public static final BlockCapability<IItemHandler, Void> ITEM_HANDLER_NO_CONTEXT =
            BlockCapability.createVoid(
                    ResourceLocation.fromNamespaceAndPath(RimMod.MODID, "item_handler_no_context"),
                    IItemHandler.class);

    public static final int SIZE = 1;

    public static final int TAKING_MAX_PROGRESS = 20; // Duration in ticks needed
    public static final int TRANSFER1_MAX_PROGRESS = 40;
    public static final int TRANSFER2_MAX_PROGRESS = 40;
    public static final int INSERTING_MAX_PROGRESS = 20;
    public static final int RETURNING1_MAX_PROGRESS = 40;
    public static final int RETURNING2_MAX_PROGRESS = 40;

    private int move_speed; // how much progress increments per tick

    private InserterBlock.InserterState state;
    private int progress;

    private final ItemStackHandler itemHandler = new ItemStackHandler(SIZE) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            InserterBlockEntity.this.setChanged();
        }
    };
    private static final Component TITLE = Component.translatable("container." + RimMod.MODID + ".inserter");

    public InserterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.INSERTER.get(), pos, state);

        this.progress = 0;
        this.state = InserterBlock.InserterState.WAIT_SOURCE;
        this.move_speed = 1;
    }

    public int getContainerSize() {
        return SIZE;
    }

    public boolean isEmpty() {
        return this.itemHandler.getStackInSlot(0).isEmpty();
    }

    // Return the item stack in the specified slot.
    public ItemStack getItem(int slot) {
        return this.itemHandler.getStackInSlot(slot);
    }

    public void setItem(int slot, ItemStack item) {
        this.itemHandler.setStackInSlot(slot, item);
        this.setChanged();
    }


    // The display name of the menu. Don't forget to add a translation!
    protected Component getDefaultName() {
        return Component.translatable("container.rimmod.inserter");
    }

    // Read values from the passed CompoundTag here.
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        itemHandler.deserializeNBT(registries, tag.getCompound("Inventory"));
        this.state = InserterBlock.InserterState.values()[tag.getInt("state")];
        this.progress = tag.getInt("progress");
    }

    // Save values into the passed CompoundTag here.
    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Inventory", itemHandler.serializeNBT(registries));
        tag.putInt("state", this.state.stateIndex);
        tag.putInt("progress", this.progress);
    }

    // SYNC ON CHUNK LOAD
    // Create an update tag here. For block entities with only a few fields, this can just call #saveAdditional.
    @Override
    public @NotNull CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }


    // Handle a received update tag here. The default implementation calls #loadAdditional here,
    // so you do not need to override this method if you don't plan to do anything beyond that.
    @Override
    public void handleUpdateTag(CompoundTag tag, HolderLookup.Provider registries) {
        super.handleUpdateTag(tag, registries);
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }
//
//    // SYNC ON BLOCK UPDATE
//    // Return our packet here. This method returning a non-null result tells the game to use this packet for syncing.
//    @Override
//    public Packet<ClientGamePacketListener> getUpdatePacket() {
//        // The packet uses the CompoundTag returned by #getUpdateTag. An alternative overload of #create exists
//        // that allows you to specify a custom update tag, including the ability to omit data the client might not need.
//        return ClientboundBlockEntityDataPacket.create(this);
//    }
//
//    // Optionally: Run some custom logic when the packet is received.
//    // The super/default implementation forwards to #loadAdditional.
//    @Override
//    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket packet, HolderLookup.Provider registries) {
//        super.onDataPacket(connection, packet, registries);
//        // Do whatever you need to do here.
//    }

    @Override
    public Component getDisplayName() {
        return TITLE;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new InserterMenu(containerId, playerInventory, this);
    }

    // TODO
    public Vector3f getItemOffsetFromCenter() {
        switch (this.getBlockState().getValue(BlockStateProperties.HORIZONTAL_FACING)) {
            case NORTH:
                return new Vector3f(0f, -0.1875f, -0.5f);
            case SOUTH:
                return new Vector3f(0f, -0.1875f, 0.5f);
            case WEST:
                return new Vector3f(-0.5f, -0.1875f, 0f);
            case EAST:
                return new Vector3f(0.5f, -0.1875f, 0f);
            default:
                return new Vector3f(0.0f, 0.0f, 0.0f);
        }
    }

    public InserterBlock.InserterState getState() {
        return this.state;
    }

    public int getProgress() {
        return this.progress;
    }

    private static class GetterResult {
        private boolean success;
        private @Nullable ItemStack outputItem;

        public GetterResult(boolean success, @Nullable ItemStack outputItem) {
            this.success = success;
            this.outputItem = outputItem;
        }
    }

    private static GetterResult getItem(BlockPos pos, Direction dir, Level level, boolean takeItem) {
        BlockEntity source_entity = level.getBlockEntity(pos);

        if (source_entity != null) {
            // Check for capability and try to take item
            IItemHandler item_cap = level.getCapability(Capabilities.ItemHandler.BLOCK, pos, dir.getOpposite());

            if (item_cap != null) {
                int max_slot = item_cap.getSlots();

                for (int i = 0; i < max_slot; i++) {
                    ItemStack stackInSlot = item_cap.getStackInSlot(i);
                    if (!stackInSlot.isEmpty()) {
                        // Found the first slot with a valid item

                        return (takeItem) ?
                                new GetterResult(true, item_cap.extractItem(i, stackInSlot.getCount(), false)) :
                                new GetterResult(true, null);
                    }
                }
            }
        }
        return new GetterResult(false, null);
    }

    private static int putItem(BlockPos pos, Direction dir, Level level, @Nullable ItemStack item) {
        BlockEntity dest_entity = level.getBlockEntity(pos);

        if (dest_entity != null) {
            // Check for capability and try to take item
            IItemHandler item_cap = level.getCapability(Capabilities.ItemHandler.BLOCK, pos, dir.getOpposite());

            if (item_cap != null) {
                int max_slot = item_cap.getSlots();

                for (int i = 0; i < max_slot; i++) {
                    ItemStack stackInSlot = item_cap.getStackInSlot(i);
                    if (stackInSlot.isEmpty()) {
                        // Found the first slot with empty slot
                        if (item == null) {
                            return i;
                        } else {
                            ItemStack ret = item_cap.insertItem(i, item, false);
                            if (ret.isEmpty()) {
                                return i;
                            }
                        }
                    }
                }
            }
        }
        return -1;
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        Direction dir = blockState.getValue(InserterBlock.INSERT_DIRECTION);
        InserterBlockEntity inserter = (InserterBlockEntity) level.getBlockEntity(blockPos);
        if (inserter == null) {
            return;
        }

        if (inserter.state.direction.getStep() == -1) {
            dir = dir.getOpposite();
        }
        RimMod.LOGGER.info("Inserter " + blockPos + " " + inserter.state.name() + " " + inserter.progress);

        switch (inserter.state) {
            case WAIT_SOURCE: {
                BlockPos source_pos = blockPos.relative(dir, 1);
                GetterResult res = getItem(source_pos, dir, level, false);
                if (res.success) {
                    // There is one item to take in chest
                    inserter.state = InserterBlock.InserterState.TAKING;
                } else if (!inserter.getItemHandler().getStackInSlot(0).isEmpty()) {
                    // Someone gave the inserter an item through menu
                    inserter.state = InserterBlock.InserterState.TRANSFERRING_1;
                }

                break;
            }
            case TAKING: {
                if (inserter.progress < TAKING_MAX_PROGRESS) {
                    inserter.progress += inserter.move_speed;
                } else {
                    if (!inserter.getItemHandler().getStackInSlot(0).isEmpty()) {
                        // Someone gave the inserter an item through menu
                        inserter.state = InserterBlock.InserterState.TRANSFERRING_1;
                        inserter.progress = 0;
                        break;
                    }

                    BlockPos source_pos = blockPos.relative(dir, 1);
                    GetterResult res = getItem(source_pos, dir, level, true);
                    if (res.success) {
                        // The Inserter was able to take item from inventory
                        assert res.outputItem != null;
                        inserter.getItemHandler().insertItem(0, res.outputItem, false);

                        inserter.state = InserterBlock.InserterState.TRANSFERRING_1;
                        inserter.progress = 0;
                    }
                    else {
                        // The Inserter wasn't able to successfully take item
                        inserter.state = InserterBlock.InserterState.WAIT_SOURCE;
                        inserter.progress = 0;
                    }
                }
                break;
            }
            case TRANSFERRING_1: {
                if (inserter.progress < TRANSFER1_MAX_PROGRESS) {
                    inserter.progress += inserter.move_speed;
                } else {
                    inserter.progress = 0;

                    if (inserter.getItemHandler().getStackInSlot(0).isEmpty()) {
                        inserter.state = InserterBlock.InserterState.WAIT_SOURCE;
                    } else {
                        inserter.state = InserterBlock.InserterState.TRANSFERRING_2;
                    }
                }
                break;
            }
            case TRANSFERRING_2: {
                if (inserter.progress < TRANSFER2_MAX_PROGRESS) {
                    inserter.progress += inserter.move_speed;
                } else {
                    inserter.progress = 0;

                    if (inserter.getItemHandler().getStackInSlot(0).isEmpty()) {
                        inserter.state = InserterBlock.InserterState.RETURNING_1;
                    } else {
                        inserter.state = InserterBlock.InserterState.WAIT_DESTINATION;
                    }

                }
                break;
            }
            case WAIT_DESTINATION: {
                if (inserter.getItemHandler().getStackInSlot(0).isEmpty()) {
                    // Someone took the item from the inserter through menu
                    inserter.state = InserterBlock.InserterState.RETURNING_1;
                    inserter.progress = 0;
                    break;
                }

                BlockPos dest_pos = blockPos.relative(dir, 1);
                int res = putItem(dest_pos, dir, level, null);
                if (res != -1) {
                    // The Inserter can put item since inventory has empty slot
                    inserter.state = InserterBlock.InserterState.INSERTING;
                    inserter.progress = 0;
                }
                break;
            }
            case INSERTING: {
                if (inserter.progress < INSERTING_MAX_PROGRESS) {
                    inserter.progress += inserter.move_speed;
                } else {
                    if (inserter.getItemHandler().getStackInSlot(0).isEmpty()) {
                        // Someone took the item from the inserter through menu
                        inserter.state = InserterBlock.InserterState.RETURNING_1;
                        inserter.progress = 0;
                        break;
                    }

                    BlockPos dest_pos = blockPos.relative(dir, 1);
                    ItemStack item = inserter.itemHandler.extractItem(0, inserter.itemHandler.getStackInSlot(0).getCount(), false);
                    int res = putItem(dest_pos, dir, level, item);

                    inserter.progress = 0;
                    inserter.state = InserterBlock.InserterState.RETURNING_1;

                    if (res == -1) {
                        ItemEntity itemEntity = new ItemEntity(level, dest_pos.getX() + 0.5, dest_pos.getY() + 0.5, dest_pos.getZ() + 0.5, item);
                        level.addFreshEntity(itemEntity);
                    }
                }
                break;
            }
            case RETURNING_1: {
                if (inserter.progress < RETURNING1_MAX_PROGRESS) {
                    inserter.progress += inserter.move_speed;
                } else {
                    inserter.progress = 0;
                    inserter.state = InserterBlock.InserterState.RETURNING_2;
                }
                break;
            }
            case RETURNING_2: {
                if (inserter.progress < RETURNING2_MAX_PROGRESS) {
                    inserter.progress += inserter.move_speed;
                } else {
                    inserter.progress = 0;
                    inserter.state = InserterBlock.InserterState.WAIT_SOURCE;
                }
                break;
            }

        }
    }
}
