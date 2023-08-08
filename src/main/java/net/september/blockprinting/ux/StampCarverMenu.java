package net.september.blockprinting.ux;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.september.blockprinting.block.BPBlocks;

public class StampCarverMenu extends AbstractContainerMenu {

    private ContainerLevelAccess AccessField;

    //Client
    public StampCarverMenu(int id, Inventory inv) {
        super(BPMenuTypes.STAMP_CARVER_MENU.get(), id);
    }

    //Server
    public StampCarverMenu(int id, Inventory inv, FriendlyByteBuf extraData){
        super(BPMenuTypes.STAMP_CARVER_MENU.get(), id);
        this.AccessField = ContainerLevelAccess.create(inv.player.level(), extraData.readBlockPos());
        System.out.println("Constructing Stamp Carver Menu...");
        System.out.println("ID: " + id);
        System.out.println("Inv: " + inv);
        System.out.println("Extra Data: " + extraData);
        System.out.println("Access Field: " + this.AccessField);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int StartIndex) {
        ItemStack MovingStack = ItemStack.EMPTY;
        Slot StartSlot = this.slots.get(StartIndex);

        if (StartSlot != null && StartSlot.hasItem()) {
            ItemStack StartStack = StartSlot.getItem();
            MovingStack = StartStack.copy();

            if (StartIndex == 0) {
                if (!this.moveItemStackTo(StartStack, 5, 41, true)) {
                    return ItemStack.EMPTY;
                }
                StartSlot.onQuickCraft(StartStack, MovingStack);
            } else if (StartIndex >= 5 && StartIndex <41){
                if (!this.moveItemStackTo(StartStack, 1, 5, false)) {
                    if (StartIndex < 32) {
                        if (!this.moveItemStackTo(StartStack, 32, 41, false)) {
                            return ItemStack.EMPTY;
                        }
                    } else if (!this.moveItemStackTo(StartStack, 5, 32, false)) {
                        return ItemStack.EMPTY;
                    }
                }
            } else if (!this.moveItemStackTo(StartStack, 5, 41, false)) {
                return ItemStack.EMPTY;
            }
            if (StartStack.isEmpty()) {
                StartSlot.set(ItemStack.EMPTY);
            } else {
                StartSlot.setChanged();
            }
            if (StartStack.getCount() == MovingStack.getCount()) {
                return ItemStack.EMPTY;
            }
            StartSlot.onTake(pPlayer, StartStack);
        }
        return MovingStack;
    }



    @Override
    public boolean stillValid(Player pPlayer) {
        //return AbstractContainerMenu.stillValid(this.AccessField, pPlayer, BPBlocks.STAMP_CARVER.get());
        //Produces a NullPointerException!!! FIX THIS!!!
        return true;
    }

}
