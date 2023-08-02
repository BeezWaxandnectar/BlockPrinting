package net.september.blockprinting.item;

import net.minecraft.client.renderer.item.ClampedItemPropertyFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.september.blockprinting.datagen.IPrefix;
import net.september.blockprinting.item.custom.MagicInkwellItem;
import org.apache.logging.log4j.util.TriConsumer;

public final class BPItemProperties {
    public static void init(TriConsumer<ItemLike, ResourceLocation, ClampedItemPropertyFunction> consumer){
        consumer.accept(BPItems.MAGIC_INKWELL.get(), IPrefix.prefix("magic_inkwell_empty"), (pStack, pLevel, pEntity, pSeed) -> MagicInkwellItem.hasDye(pStack) ? 0 : 1);



    }
}
