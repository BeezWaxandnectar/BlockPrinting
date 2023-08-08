package net.september.blockprinting.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.september.blockprinting.ux.StampCarverMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

public class StampCarverBlockEntity extends BlockEntity implements MenuProvider {
    public StampCarverBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }

    public StampCarverBlockEntity(BlockPos pos, BlockState state){
        super(BPBlockEntities.STAMP_CARVER_ENTITY.get(), pos, state);
    }


    @Override
    public @NotNull CompoundTag getUpdateTag() {
        return super.getUpdateTag();
    }

    @Override
    public void handleUpdateTag(CompoundTag tag) {
        super.handleUpdateTag(tag);
    }



    //--------NBT--------//
    @Override
    @ParametersAreNonnullByDefault
    protected void saveAdditional(CompoundTag pTag) {

        super.saveAdditional(pTag);
    }

    @ParametersAreNonnullByDefault
    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new StampCarverMenu(id, inventory);
    }
    @Override
    @ParametersAreNonnullByDefault
    public void load(CompoundTag pTag) {
        super.load(pTag);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal(" ");
    }


}
