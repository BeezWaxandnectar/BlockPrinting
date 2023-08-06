package net.september.blockprinting.dyesystem;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.Tags;

public class DyeSystem {

    private int dyeHeld = 0;

    public int getDyeHeld(){
        return dyeHeld;
    }

    public static boolean PlayerHasDyeItem(Player player){
        for(int i = 0; i < player.getInventory().getContainerSize(); i++){
            ItemStack currentStack = player.getInventory().getItem(i);
            if (currentStack.is(Tags.Items.DYES)) {
                return true;
            }}
        return false;
    }

    public static int getFirstDyeItem(Player player) {
        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack currentStack = player.getInventory().getItem(i);
            if (currentStack.is(Tags.Items.DYES)){
                return i;
            }
        }
        return -1;
    }

    public static int getDyeTotal(Player player){
        int DyeTotal = 0;

        for (int slot = 0; slot < player.getInventory().getContainerSize(); slot++) {
            ItemStack currentStack = player.getInventory().getItem(slot);
            if (currentStack.is(Tags.Items.DYES)){
                DyeTotal += (currentStack.getCount());
            }
        }
        return DyeTotal;
    }
    public void copyFrom(PlayerDyeProvider source) {
        this.dyeHeld = source.DyeHeld.getDyeHeld();
    }

    public void useUpDye (Player player){
        player.getCapability(PlayerDyeProvider.PlayerDyeHeld).ifPresent(DyeHeld -> {
            if (PlayerHasDyeItem(player)){
                player.getInventory().getItem(getFirstDyeItem(player)).shrink(1);
            }
        });

    }


    public void saveNBTData(CompoundTag nbt){
        nbt.putInt("dyeHeld", dyeHeld);
    }

    public void loadNBTData(CompoundTag nbt){
        dyeHeld = nbt.getInt("dyeHeld");
    }


}