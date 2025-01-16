package net.rimrim.rimmod.menu;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.rimrim.rimmod.blockentity.TankBlockEntity;
import net.rimrim.rimmod.init.ModBlocks;
import net.rimrim.rimmod.init.ModMenus;

public class TankMenu extends AbstractContainerMenu {
    private final TankBlockEntity blockEntity;
    private final Level level;

    public TankMenu(int containerId, Inventory inventory, BlockPos pos, Level level) {
        super(ModMenus.TANK_MENU.get(), containerId);  // You'll need to register this menu type
        this.blockEntity = (TankBlockEntity) level.getBlockEntity(pos);
        this.level = level;

    }

    public TankMenu(int containerId, Inventory inventory) {
        this(containerId, inventory, BlockPos.ZERO, inventory.player.level());
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, ModBlocks.TANK.get());
    }

    // Handle shift-clicking items
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack stack = slot.getItem();
            itemstack = stack.copy();

            if (index < TankBlockEntity.SIZE) {
                if (!this.moveItemStackTo(stack, TankBlockEntity.SIZE, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(stack, 0, TankBlockEntity.SIZE, false)) {
                return ItemStack.EMPTY;
            }

            if (stack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }
}
