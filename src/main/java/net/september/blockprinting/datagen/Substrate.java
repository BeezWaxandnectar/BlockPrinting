package net.september.blockprinting.datagen;

import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;

public class Substrate {
    public RegistryObject<Block> blockField;
    int index;

    public Substrate(RegistryObject<Block> blockField, int index) {
        this.blockField = blockField;
        this.index = index;
    }
}
