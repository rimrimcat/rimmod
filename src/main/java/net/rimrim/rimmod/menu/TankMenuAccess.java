package net.rimrim.rimmod.menu;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.rimrim.rimmod.registry.ModBlocks;

public class TankMenuAccess {

    private final ContainerLevelAccess access;

    // Client menu constructor
    public TankMenuAccess(int containerId, Inventory playerInventory) {
        this(containerId, playerInventory, ContainerLevelAccess.NULL);
    }

    // Server menu constructor
    public TankMenuAccess(int containerId, Inventory playerInventory, ContainerLevelAccess access) {
        this.access = access;
    }

    // Assume this menu is attached to Supplier<Block> MY_BLOCK
//    @Override
//    public boolean stillValid(Player player) {
//        return AbstractContainerMenu.stillValid(this.access, player, ModBlocks.TANK.get());
//    }

}
