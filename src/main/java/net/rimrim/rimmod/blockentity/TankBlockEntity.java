package net.rimrim.rimmod.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.fluids.capability.IFluidHandlerItem;
import net.neoforged.neoforge.fluids.capability.templates.FluidTank;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.init.ModBlockEntities;
import net.rimrim.rimmod.menu.TankMenu;
import net.rimrim.rimmod.menu.itemhandler.TankItemHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class TankBlockEntity extends BlockEntity implements MenuProvider {
    public static final int SIZE = 2;

    private final TankItemHandler itemHandler = new TankItemHandler(){
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            TankBlockEntity.this.setChanged();
        }
    };


    private final FluidTank fluidTank = new FluidTank(10000) {
        @Override
        protected void onContentsChanged() {
            super.onContentsChanged();
            TankBlockEntity.this.setChanged();
        }
    };


    private static final Component TITLE = Component.translatable("container." + RimMod.MODID + ".tank");


    public TankBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TANK.get(), pos, state);

    }

    public int getContainerSize() {
        return SIZE;
    }


    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }


    public boolean isEmpty() {
        return this.itemHandler.getStackInSlot(0).isEmpty() && this.itemHandler.getStackInSlot(1).isEmpty();
    }

    public @NotNull ItemStack getItem() {
        return this.itemHandler.getStackInSlot(0);
    }

    public @NotNull ItemStack getFluidItem() {
        return this.itemHandler.getStackInSlot(1);
    }

    public FluidTank getFluidTank() {
        return this.fluidTank;
    }

    public FluidStack getFluid() {
        return this.fluidTank.getFluid();
    }

    public int getAvailableFluidCapacity() {
        return this.fluidTank.getCapacity() - this.fluidTank.getFluidAmount();
    }


    public Component getDisplayName() {
        return TITLE;
    }


    // Read values from the passed CompoundTag here.
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);

        itemHandler.deserializeNBT(registries, tag.getCompound("Inventory"));
        fluidTank.readFromNBT(registries, tag.getCompound("FluidTank"));
    }

    // Save values into the passed CompoundTag here.
    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);

        tag.put("Inventory", itemHandler.serializeNBT(registries));
        tag.put("FluidTank", fluidTank.writeToNBT(registries, new CompoundTag()));
    }

    // SYNC ON CHUNK LOAD
    // Create an update tag here. For block entities with only a few fields, this can just call #saveAdditional.
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }


    // SYNC ON BLOCK UPDATE
    // Return our packet here. This method returning a non-null result tells the game to use this packet for syncing.
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        // The packet uses the CompoundTag returned by #getUpdateTag. An alternative overload of #create exists
        // that allows you to specify a custom update tag, including the ability to omit data the client might not need.
        return ClientboundBlockEntityDataPacket.create(this);
    }

    private void sendUpdate() {
        setChanged();

        if (this.level != null) {
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), Block.UPDATE_ALL);
        }
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        if (level == null || level.isClientSide()) return;

        TankBlockEntity tank = (TankBlockEntity) level.getBlockEntity(blockPos);
        if (tank == null) return;

        ItemStack stack = tank.getFluidItem();
        if (stack.isEmpty()) return;

        FluidTank fluidTank = tank.getFluidTank();
        if (tank.getAvailableFluidCapacity() < 1000) return;

        // Get Item capability
        IFluidHandlerItem itemFluidHandler = stack.getCapability(Capabilities.FluidHandler.ITEM, null);
        if (itemFluidHandler == null) return;

        // Drain item content if possible
        int acceptableCapacity = tank.getFluidTank().getCapacity() - tank.getFluidTank().getFluidAmount();
        int drainable = itemFluidHandler.drain(acceptableCapacity, IFluidHandler.FluidAction.SIMULATE).getAmount();


        if (drainable > 0) {
            tank.getFluidTank().fill(itemFluidHandler.drain(drainable, IFluidHandler.FluidAction.EXECUTE), IFluidHandler.FluidAction.EXECUTE);
            RimMod.LOGGER.info("Filled fluid tank");

            if (drainable <= acceptableCapacity) {
                tank.getItemHandler().setStackInSlot(1, itemFluidHandler.getContainer());
            }

            tank.sendUpdate();
        }

    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new TankMenu(containerId, playerInventory, this);
    }
}
