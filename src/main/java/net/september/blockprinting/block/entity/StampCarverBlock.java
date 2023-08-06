package net.september.blockprinting.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;
import net.september.blockprinting.ux.StampCarverMenu;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

public class StampCarverBlock extends BaseEntityBlock {

    public StampCarverBlock(Properties pProperties) {
        super(pProperties);
    }

    @ParametersAreNonnullByDefault
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new StampCarverBlockEntity(pPos, pState);
    }

    @ParametersAreNonnullByDefault
    @Override
    public @NotNull RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @ParametersAreNonnullByDefault
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState pNewState, boolean isMoving) {
        if (state.getBlock() != pNewState.getBlock()){
            BlockEntity blockEntity = level.getBlockEntity(pos);
            if (blockEntity instanceof StampCarverBlockEntity) {
                ((StampCarverBlockEntity) blockEntity).drops();
            }
        }
        super.onRemove(state, level, pos, pNewState, isMoving);
    }


    @ParametersAreNonnullByDefault
    @Override
    public @NotNull InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos,
                                          Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

        if (!pLevel.isClientSide()) {
            BlockEntity entity = pLevel.getBlockEntity(pPos);
            if (entity instanceof StampCarverBlockEntity) {
                NetworkHooks.openScreen((ServerPlayer) pPlayer, (StampCarverBlockEntity) entity, pPos);
                System.out.println("Should be Opening screen...");
                System.out.println("PARAMETERS READ:");
                System.out.println("pState = " + pState);
                System.out.println("entity = " + entity);
                System.out.println("pPlayer = " + pPlayer);
                System.out.println("pHit = " + pHit);
            } else {
                throw new IllegalStateException("Our Container provider is missing!");
            }
        }
        return InteractionResult.sidedSuccess(pLevel.isClientSide());
    }


   /* @ParametersAreNonnullByDefault
    public MenuProvider getMenuProvider(BlockState pState, Level pLevel, BlockPos pPos) {
        FriendlyByteBuf PosBuf = new FriendlyByteBuf();
        PosBuf.writeBlockPos(pPos);

        return new SimpleMenuProvider((id, inv, player) ->{
            new StampCarverMenu(id, inv, PosBuf), Component.literal(" ")});
    }*/

    @Override
    @ParametersAreNonnullByDefault
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state,
                                                                  BlockEntityType<T> type) {
        return createTickerHelper(type, BPBlockEntities.STAMP_CARVER.get(),
                StampCarverBlockEntity::tick);
    }


}
