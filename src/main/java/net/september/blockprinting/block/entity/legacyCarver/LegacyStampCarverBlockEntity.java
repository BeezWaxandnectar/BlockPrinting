/*
package net.september.blockprinting.block.entity.legacyCarver;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.september.blockprinting.item.BPItems;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

public class LegacyStampCarverBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(2){
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();
    protected ContainerData data;
    private int isDetectingStamp;

    public LegacyStampCarverBlockEntity(BlockPos pos, BlockState state) {
        super(LegacyBPBlockEntities.STAMP_CARVER.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {return LegacyStampCarverBlockEntity.this.isDetectingStamp;}

            @Override
            public void set(int index, int value) {
                LegacyStampCarverBlockEntity.this.isDetectingStamp = value;}

            @Override
            public int getCount() {return 1;}
        };

    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal(" ");
    }

    @ParametersAreNonnullByDefault
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new LegacyStampCarverMenu(id, inventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == ForgeCapabilities.ITEM_HANDLER){ return lazyItemHandler.cast(); }
        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @ParametersAreNonnullByDefault
    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @ParametersAreNonnullByDefault
    @Override
    protected void saveAdditional(CompoundTag nbt) {

        nbt.put("inventory", itemHandler.serializeNBT());

        super.saveAdditional(nbt);
    }
    @ParametersAreNonnullByDefault
    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        itemHandler.deserializeNBT(nbt.getCompound("inventory"));

    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++){
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }
        assert this.level != null;
        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    public static void tick(Level level, BlockPos pos, BlockState blockState, LegacyStampCarverBlockEntity entity) {
        if (level.isClientSide){return;}

        if (isHoldingStamp(entity)) {
            setChanged(level, pos, blockState);
            craftItem(entity, BPItems.THUMBSUP.get());

        } else {
            setChanged(level, pos, blockState);
        }
    }

    public static boolean isHoldingStamp(LegacyStampCarverBlockEntity entity){

        SimpleContainer inventory = new SimpleContainer(entity.itemHandler.getSlots());
        for (int i = 0; i < entity.itemHandler.getSlots(); i++) {
            inventory.setItem(i, entity.itemHandler.getStackInSlot(i));
        }

        boolean isHoldingStamp = entity.itemHandler.getStackInSlot(0).getItem() == BPItems.STAMP.get();

        return isHoldingStamp && canOutputStamp(inventory, new ItemStack(BPItems.THUMBSUP.get(), 1));
    }

    private static boolean canOutputStamp(SimpleContainer inventory, ItemStack stack) {
        return inventory.getItem(0).isEmpty() && stack.getItem() == BPItems.STAMP.get();
    }

    private static void craftItem(LegacyStampCarverBlockEntity entity, Item result) {
        if (isHoldingStamp(entity)) {
            entity.itemHandler.extractItem(0, 1, false);
            entity.itemHandler.setStackInSlot(1, new ItemStack(BPItems.THUMBSUP.get(),
                    entity.itemHandler.getStackInSlot(1).getCount() + 1));
        }
    }

}
*/
