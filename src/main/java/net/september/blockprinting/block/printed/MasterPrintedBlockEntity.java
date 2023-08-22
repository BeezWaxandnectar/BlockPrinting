package net.september.blockprinting.block.printed;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;

public class MasterPrintedBlockEntity extends BlockEntity {

    private String swatch;
    private String style;
    private String substrate;
    private String namespace = swatch + "_" + style + "_" + substrate;


    public MasterPrintedBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState) {
        super(pType, pPos, pBlockState);
    }


    @Override
    @ParametersAreNonnullByDefault
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
    }

    @Override
    @ParametersAreNonnullByDefault
    public void load(CompoundTag pTag) {
        super.load(pTag);
    }

}
