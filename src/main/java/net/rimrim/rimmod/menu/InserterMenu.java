package net.rimrim.rimmod.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.rimrim.rimmod.init.ModMenus;

public class InserterMenu extends AbstractContainerMenu {
    public InserterMenu(int containerId, Inventory playerInventory) {
        super(ModMenus.INSERTER_MENU.get(), containerId);
    }


    @Override
    public ItemStack quickMoveStack(Player player, int quickMovedSlotIndex) {
        // The quick moved slot stack
        ItemStack quickMovedStack = ItemStack.EMPTY;
        // The quick moved slot
        Slot quickMovedSlot = this.slots.get(quickMovedSlotIndex);


        return ItemStack.EMPTY; // TODO:
    }


    @Override
    public boolean stillValid(Player player) {
        return true;
    }
}
