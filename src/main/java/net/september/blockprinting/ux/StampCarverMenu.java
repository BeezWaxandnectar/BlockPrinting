package net.september.blockprinting.ux;

import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import net.september.blockprinting.block.BPBlocks;
import net.september.blockprinting.block.entity.StampCarverBlockEntity;
import net.september.blockprinting.item.BPItems;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

public class StampCarverMenu extends AbstractContainerMenu {

    private ContainerLevelAccess AccessField;

    //Client
    public StampCarverMenu(int id, Inventory inv) {
        super(BPMenuTypes.STAMP_CARVER_MENU.get(), id);
    }

    //Server
    public StampCarverMenu(int id, Inventory inv, FriendlyByteBuf extraData){
        super(BPMenuTypes.STAMP_CARVER_MENU.get(), id);
        //this.AccessField = ContainerLevelAccess.create(inv.player.level(), extraData.readBlockPos());

        StampCarverBlockEntity pEntity = (StampCarverBlockEntity) inv.player.level().getBlockEntity(extraData.readBlockPos());
        assert pEntity != null;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);
        pEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 8, 59));
            this.addSlot(new SlotItemHandler(handler, 1, 152, 59));}
        );
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull ItemStack quickMoveStack(Player pPlayer, int StartIndex) {
        ItemStack MovingStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(StartIndex);

        if (slot.hasItem()) {
            Item Stamp = BPItems.STAMP.get();
            ItemStack Stack = slot.getItem();
            Item StartItem = Stack.getItem();
            MovingStack = Stack.copy();

            if (StartIndex == 0) {
                if (!this.moveItemStackTo(Stack, 2, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (StartIndex == 1) {
                StartItem.onCraftedBy(Stack, pPlayer.level(), pPlayer);
                if (!this.moveItemStackTo(Stack, 2, 38, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickCraft(Stack, MovingStack);
            } else if (StartItem == Stamp){
                if (!this.moveItemStackTo(Stack, 0, 1, false)){
                    return ItemStack.EMPTY;
                }
            } else if (StartIndex >= 2 && StartIndex < 29) {
                if (!this.moveItemStackTo(Stack, 29, 38, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (StartIndex >= 29 && StartIndex < 38 && !this.moveItemStackTo(Stack, 2, 29, false)){
                return ItemStack.EMPTY;
            }

            if (Stack.isEmpty()) {
                slot.setByPlayer(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            slot.setChanged();

            if (Stack.getCount() == MovingStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(pPlayer, Stack);
            this.broadcastChanges();
        }
        return MovingStack;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean stillValid(Player pPlayer) {
        //return AbstractContainerMenu.stillValid(this.AccessField, pPlayer, BPBlocks.STAMP_CARVER.get());
        //Produces a NullPointerException!!! FIX THIS!!!
        return true;
    }
    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 3; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, 2 + l + i * 9 + 9, 8 + l *18, 84 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 142));
        }
    }



}
