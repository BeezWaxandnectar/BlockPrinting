package net.september.blockprinting.datagen;

import net.minecraft.resources.ResourceLocation;
import net.september.blockprinting.BlockPrinting;

import java.lang.annotation.Target;

public interface IPrefix {
    static ResourceLocation prefix (String path) {
        return new ResourceLocation(BlockPrinting.MOD_ID, path);
    }
}
