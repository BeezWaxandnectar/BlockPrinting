package net.september.blockprinting.item.custom;

import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.block.SoundType;
import org.jetbrains.annotations.NotNull;

public class MagicInkwellItem extends Item {

    public static final int MAX_DYE_STORAGE = 2048;


    public MagicInkwellItem(Properties pProperties) {
        super(pProperties);
    }







    @Override
    public @NotNull InteractionResult useOn(UseOnContext pContext) {
        if(!pContext.getLevel().isClientSide()) {
            Player player = pContext.getPlayer();
            assert player != null;

            player.playSound(SoundEvent.createFixedRangeEvent(new ResourceLocation("minecraft", "block.brewing_stand.brew"), 6f));




        }
        ;
        return InteractionResult.SUCCESS;
    }



}

