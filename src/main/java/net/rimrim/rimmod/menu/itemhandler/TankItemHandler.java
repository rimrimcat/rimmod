package net.rimrim.rimmod.menu.itemhandler;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.ItemStackHandler;

public class TankItemHandler extends ItemStackHandler {
    public final int fluidSlot;

    public TankItemHandler(int size) {
        super(size);

        this.fluidSlot = size - 1;
    }


    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return (slot == fluidSlot) == (stack.getCapability(Capabilities.FluidHandler.ITEM, null) != null);
    }

    public void setStackInSlotNoUpdate(int slot, ItemStack stack) {
        validateSlotIndex(slot);
        this.stacks.set(slot, stack);
    }

    @Override
    protected int getStackLimit(int slot, ItemStack stack) {
        return 1;
    }
}
