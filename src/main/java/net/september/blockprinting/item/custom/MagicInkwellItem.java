package net.september.blockprinting.item.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.september.blockprinting.util.BPInventoryUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagicInkwellItem extends Item {

    public static final int MAX_DYE_STORAGE = 4096;


    public static int CURRENT_LOADED_DYE = 0;



    CompoundTag CurrentDyeStorage = new CompoundTag();




    public MagicInkwellItem(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack Inkwell = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (!level.isClientSide()) {

            if (BPInventoryUtil.PlayerHasDyeItem(player)) {
                addDyeToInkwell(player);
            }

            @NotNull InteractionResultHolder<ItemStack> InteractionResultHolder;
        }
        return net.minecraft.world.InteractionResultHolder.success(Inkwell);
    }




    private void addDyeToInkwell(@NotNull Player player){
        ItemStack Inkwell = player.getItemInHand(InteractionHand.MAIN_HAND);
        int FirstDyeIndex = BPInventoryUtil.getFirstDyeItem(player);

        ItemStack FirstDyeItem = player.getInventory().getItem(FirstDyeIndex);

        FirstDyeItem.shrink(1);



    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);


    }
}

