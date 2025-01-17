package net.rimrim.rimmod.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.init.ModBlockEntities;
import net.rimrim.rimmod.menu.InserterMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class InserterBlockEntity extends BlockEntity implements MenuProvider {
    public static final BlockCapability<IItemHandler, Void> ITEM_HANDLER_NO_CONTEXT =
            BlockCapability.createVoid(
                    // Provide a name to uniquely identify the capability.
                    ResourceLocation.fromNamespaceAndPath(RimMod.MODID, "item_handler_no_context"),
                    // Provide the queried type. Here, we want to look up `IItemHandler` instances.
                    IItemHandler.class);

    public static final int SIZE = 1;

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
    }

    // Save values into the passed CompoundTag here.
    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("Inventory", itemHandler.serializeNBT(registries));
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

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        //

    }


    @Override
    public Component getDisplayName() {
        return TITLE;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new InserterMenu(containerId, playerInventory, this);
    }
}
