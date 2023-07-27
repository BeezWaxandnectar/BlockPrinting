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
import net.september.blockprinting.dyesystem.DyeSystem;
import net.september.blockprinting.dyesystem.PlayerDyeProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class MagicInkwellItem extends Item {

    CompoundTag CurrentDyeStorage = new CompoundTag();


    public MagicInkwellItem(Properties pProperties) {
        super(pProperties);
    }


    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, @NotNull InteractionHand usedHand) {
        ItemStack Inkwell = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (!level.isClientSide()) {

            if (PlayerDyeProvider.PlayerHasDyeItem(player) && !player.isShiftKeyDown()) {
                addDyeToInkwell(Inkwell, player);
            }

            if (player.isShiftKeyDown()){
                removeDye(Inkwell, player);
            }
        }
        return net.minecraft.world.InteractionResultHolder.success(Inkwell);
    }

    public void addDyeToInkwell(ItemStack Inkwell, Player player){
        CompoundTag storedInk = Inkwell.getOrCreateTag();
        if(storedInk.getInt("storedInk") < 2048) {
            int FirstDyeIndex = DyeSystem.getFirstDyeItem(player);
            player.getInventory().getItem(FirstDyeIndex).shrink(1);

            int before = storedInk.getInt("storedInk");
            int after = before + 1;
            storedInk.putInt("storedInk", after);
        }
            player.sendSystemMessage(Component.nullToEmpty("Stored Ink: " + (storedInk.getInt("storedInk"))));
        }

        public void removeDye(ItemStack Inkwell, Player player){
        CompoundTag storedInk = Inkwell.getTag();
        if (storedInk != null) {
            int HeldInk = storedInk.getInt("storedInk");
            int after = HeldInk - 1;
            storedInk.putInt("storedInk", after);

            if (after == 0){
                Inkwell.deserializeNBT(storedInk);
            }
        } else {
            player.sendSystemMessage(Component.nullToEmpty("No more dye!"));
        }

        }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {
        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);



    }
}

