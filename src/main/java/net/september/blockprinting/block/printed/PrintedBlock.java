package net.september.blockprinting.block.printed;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.september.blockprinting.block.entity.BPBlockEntities;
import org.jetbrains.annotations.Nullable;

public class PrintedBlock extends BaseEntityBlock implements EntityBlock {

    public PrintedBlock(Properties pProperties) {
        super(pProperties);
    }


    //For simplicity's sake; Let's assume that this block has the texture: Royal, Panelopera, Wallpaper.
    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        //return new PrintedBlockEntity(BPBlockEntities.PRINTED_BLOCK_ENTITY.get(), pPos, pState);
        return null;
    }
}
