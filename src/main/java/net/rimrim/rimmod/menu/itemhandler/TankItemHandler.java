package net.rimrim.rimmod.menu.itemhandler;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.items.ItemStackHandler;

public class TankItemHandler extends ItemStackHandler {
    public TankItemHandler() {
        super(2);
    }


    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return switch (slot) {
            case 0 -> stack.getCapability(Capabilities.FluidHandler.ITEM, null) == null;
            // Slot 0 for non-fluid item
            case 1 -> stack.getCapability(Capabilities.FluidHandler.ITEM, null) != null;
            // Slot 1 for fluid items
            default -> false;
        };


    }

}
