package net.september.blockprinting.ux;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.items.SlotItemHandler;
import net.september.blockprinting.block.BPBlocks;
import net.september.blockprinting.block.entity.StampCarverBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

public class StampCarverMenu extends AbstractContainerMenu {
    private static final int HOTBAR_SLOT_COUNT = 9;
    private static final int PLAYER_INVENTORY_ROW_COUNT = 3;
    private static final int PLAYER_INVENTORY_COLUMN_COUNT = 9;
    private static final int PLAYER_INVENTORY_SLOT_COUNT = PLAYER_INVENTORY_COLUMN_COUNT * PLAYER_INVENTORY_ROW_COUNT;
    private static final int VANILLA_SLOT_COUNT = HOTBAR_SLOT_COUNT + PLAYER_INVENTORY_SLOT_COUNT;
    private static final int VANILLA_FIRST_SLOT_INDEX = 0;
    private static final int TE_INVENTORY_FIRST_SLOT_INDEX = VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT;
    private static final int TE_INVENTORY_SLOT_COUNT = 2;
    public StampCarverBlockEntity blockEntity;
    public static Level level;
    public static ContainerData data;
    //private final ContainerLevelAccess access;

    public final Container container = new SimpleContainer(1) {
        public void setChanged() {
            super.setChanged();
            StampCarverMenu.this.slotsChanged(this);
            StampCarverMenu.this.slotUpdateListener.run();
        }
    };


    //#######//
    //METHODS//
    //#######//

    public StampCarverMenu(int id, Inventory inv, FriendlyByteBuf buf) {
        this(
                id,
                inv,
                //StampCarverBlockEntity(extraData.readBlockPos(), level.getBlockState(extraData.readBlockPos())),
                level.getBlockEntity(buf.readBlockPos()),
                new SimpleContainerData(1));
        System.out.println("FriendlyByteBuf: " + buf);
    }


    public StampCarverMenu(int id, Inventory inv, @NotNull BlockEntity entity, ContainerData data ) {
        super(BPMenuTypes.STAMP_CARVER_MENU.get(), id);
        checkContainerSize(inv, 2);
        blockEntity = (StampCarverBlockEntity) entity;
        this.level = inv.player.level();
        StampCarverMenu.data = data;

        addPlayerInventory(inv);
        addPlayerHotbar(inv);
        System.out.println("ContainerData: " + data);

        this.blockEntity.getCapability(ForgeCapabilities.ITEM_HANDLER).ifPresent(handler -> {
            this.addSlot(new SlotItemHandler(handler, 0, 8, 59));
            this.addSlot(new SlotItemHandler(handler, 1, 152, 59));
        });

        addDataSlots(data);}

    @ParametersAreNonnullByDefault
    @Override
    public @NotNull ItemStack quickMoveStack(Player playerIn, int index) {
        Slot sourceSlot = slots.get(index);
        if (!sourceSlot.hasItem()) return ItemStack.EMPTY;
        ItemStack sourceStack = sourceSlot.getItem();
        ItemStack copyOfSourceStack = sourceStack.copy();

        if (index < VANILLA_FIRST_SLOT_INDEX + VANILLA_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, TE_INVENTORY_FIRST_SLOT_INDEX, TE_INVENTORY_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }} else if (index < TE_INVENTORY_FIRST_SLOT_INDEX + TE_INVENTORY_SLOT_COUNT) {
            if (!moveItemStackTo(sourceStack, VANILLA_FIRST_SLOT_INDEX, VANILLA_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }
        } else {
            System.out.println("Invalid slot Index!" + index);
            return ItemStack.EMPTY;
        }
        if (sourceStack.getCount() == 0) {
            sourceSlot.set(ItemStack.EMPTY);
        } else {
            sourceSlot.setChanged();
        }
        sourceSlot.onTake(playerIn, sourceStack);
        return copyOfSourceStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                player, BPBlocks.STAMP_CARVER.get());
    }

    private void addPlayerInventory(Inventory playerInventory) {
        for (int i = 0; i < 2; ++i) {
            for (int l = 0; l < 9; ++l) {
                this.addSlot(new Slot(playerInventory, l + i * 9 + 9, 8 + l *18, 86 + i * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory playerInventory) {
        for (int i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 144));
        }
    }

    Runnable slotUpdateListener = () -> {
    };

    public void registerUpdateListener(Runnable pListener) {
        this.slotUpdateListener = pListener;
    }

}
