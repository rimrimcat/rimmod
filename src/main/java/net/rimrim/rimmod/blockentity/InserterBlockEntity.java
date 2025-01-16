package net.rimrim.rimmod.blockentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.rimrim.rimmod.init.ModBlockEntities;
import net.rimrim.rimmod.init.ModDataComponents;
import net.rimrim.rimmod.menu.InserterMenu;

public class InserterBlockEntity extends BaseContainerBlockEntity {
    // The container size. This can of course be any value you want.
    public static final int SIZE = 1;
    private NonNullList<ItemStack> items = NonNullList.withSize(SIZE, ItemStack.EMPTY);

    private int value;

    public InserterBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.INSERTER.get(), pos, state);

    }

    // container stuff
    // The container size, like before.
    @Override
    public int getContainerSize() {
        return SIZE;
    }

    @Override
    public boolean isEmpty() {
        return this.items.stream().allMatch(ItemStack::isEmpty);
    }

    // Return the item stack in the specified slot.
    @Override
    public ItemStack getItem(int slot) {
        return this.items.get(slot);
    }

    @Override
    public void setItem(int slot, ItemStack item) {
        super.setItem(slot, item);
        this.setChanged();
    }

    // The getter for our item stack list.
    @Override
    protected NonNullList<ItemStack> getItems() {
        return items;
    }

    // The setter for our item stack list.
    @Override
    protected void setItems(NonNullList<ItemStack> items) {
        this.items = items;
        this.setChanged();
    }


    // The display name of the menu. Don't forget to add a translation!
    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.rimmod.inserter");
    }

    // Read values from the passed CompoundTag here.
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        // Will default to 0 if absent. See the NBT article for more information.
        //this.value = tag.getInt("value");


//        ItemStack istack = this.components().get(ModDataComponents.ITEM_STACK.get());
//        if (istack != null) {
//            this.items = NonNullList.withSize(SIZE, istack);
//        }
    }

    // Save values into the passed CompoundTag here.
    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);


//        DataComponentMap dcmap = this.components();
//        DataComponentMap dcmap2 = DataComponentMap.builder().set(ModDataComponents.ITEM_STACK, this.items.getFirst()).build();
//        DataComponentMap new_dcmap = DataComponentMap.composite(dcmap, dcmap);
//        this.setComponents(new_dcmap);
    }

    // SYNC ON CHUNK LOAD
    // Create an update tag here. For block entities with only a few fields, this can just call #saveAdditional.
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
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

    // SYNC ON BLOCK UPDATE
    // Return our packet here. This method returning a non-null result tells the game to use this packet for syncing.
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        // The packet uses the CompoundTag returned by #getUpdateTag. An alternative overload of #create exists
        // that allows you to specify a custom update tag, including the ability to omit data the client might not need.
        return ClientboundBlockEntityDataPacket.create(this);
    }

    // Optionally: Run some custom logic when the packet is received.
    // The super/default implementation forwards to #loadAdditional.
    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket packet, HolderLookup.Provider registries) {
        super.onDataPacket(connection, packet, registries);
        // Do whatever you need to do here.
    }

    // Custom Menu
//    @Override
//    protected AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
//        return new TankMenu(containerId, inventory, this.worldPosition, this.level);
//    }

    @Override
    protected AbstractContainerMenu createMenu(int id, Inventory player) {
        return new InserterMenu(id, player);
    }


    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, T t) {
        //

    }
}
