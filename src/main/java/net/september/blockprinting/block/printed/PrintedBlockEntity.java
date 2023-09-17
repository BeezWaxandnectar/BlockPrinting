package net.september.blockprinting.block.printed;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

import javax.annotation.ParametersAreNonnullByDefault;

public class PrintedBlockEntity extends BlockEntity {

    private final PrintedBlockTexture texture;


    //For simplicity's sake I'm assuming that this block has the texture: royal + Panelopera + Wallpaper

    //What I need to do: Tell this block how to read from the texture "royal_panelopera_wallpaper"
        //tell this block's model where to find the texture in the files.
        //In the zip it'll be: bptexgen/assets/blockprinting/textures/generated/block/{royal_panelopera_wallpaper}.png
        //Out of the zip it's: resources/assets/blockprinting/textures/generated/block/{royal_panelopera_wallpaper}.png


        //make this the texture that the model displays in the world.
        //make the block display this texture while it's an item.
        //give the block the properties of the substrate selected.
        //allow this block to stack to 64.
        //

    public PrintedBlockEntity(BlockEntityType<?> pType, BlockPos pPos, BlockState pBlockState, PrintedBlockTexture texture) {
        super(pType, pPos, pBlockState);
        this.texture = texture;
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return super.getCapability(cap);
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
