package net.rimrim.rimmod.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.rimrim.rimmod.RimMod;
import net.rimrim.rimmod.menu.ExampleMenu;
import net.rimrim.rimmod.init.ModBlockEntities;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ExampleMenuBlockEntity extends BlockEntity implements MenuProvider {
    private static final Component TITLE =
            Component.translatable("container." + RimMod.MODID + ".example_menu_block");

    private final ItemStackHandler inventory = new ItemStackHandler(27) {
        @Override
        protected void onContentsChanged(int slot) {
            super.onContentsChanged(slot);
            ExampleMenuBlockEntity.this.setChanged();
        }
    };

//    private final LazyOptional<ItemStackHandler> optional = LazyOptional.of(() -> this.inventory);

    public ExampleMenuBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.EXAMPLE_MENU.get(), pos, state);
    }

//    @Override
//    public void load(@NotNull CompoundTag nbt) {
//        super.load(nbt);
//        CompoundTag tutorialmodData = nbt.getCompound(RimMod.MODID);
//        this.inventory.deserializeNBT(tutorialmodData.getCompound("Inventory"));
//    }

    @Override
    public void loadAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.loadAdditional(nbt, registries);
        // Will default to 0 if absent. See the NBT article for more information.
        CompoundTag tutorialmodData = nbt.getCompound(RimMod.MODID);
        this.inventory.deserializeNBT(registries, tutorialmodData.getCompound("Inventory"));
    }

    
//    @Override
//    protected void saveAdditional(@NotNull CompoundTag nbt) {
//        super.saveAdditional(nbt, regi);
//        var tutorialmodData = new CompoundTag();
//        tutorialmodData.put("Inventory", this.inventory.serializeNBT());
//        nbt.put(RimMod.MODID, tutorialmodData);
//    }

    // Save values into the passed CompoundTag here.
    @Override
    public void saveAdditional(CompoundTag nbt, HolderLookup.Provider registries) {
        super.saveAdditional(nbt, registries);
        var tutorialmodData = new CompoundTag();

        tutorialmodData.put("Inventory", this.inventory.serializeNBT(registries));
        nbt.put(RimMod.MODID, tutorialmodData);
    }

//    @Override
//    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
//        return cap == ForgeCapabilities.ITEM_HANDLER ? this.optional.cast() : super.getCapability(cap);
//    }
//
//    @Override
//    public void invalidateCaps() {
//        super.invalidateCaps();
//        this.optional.invalidate();
//    }
//
//    public LazyOptional<ItemStackHandler> getOptional() {
//        return this.optional;
//    }

    public ItemStackHandler getInventory() {
        return this.inventory;
    }

    @Override
    public @NotNull Component getDisplayName() {
        return TITLE;
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, @NotNull Inventory pPlayerInventory, Player pPlayer) {
        return new ExampleMenu(pContainerId, pPlayerInventory);
    }
}
