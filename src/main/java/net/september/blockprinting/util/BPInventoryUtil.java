package net.september.blockprinting.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

public class BPInventoryUtil {

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



}
