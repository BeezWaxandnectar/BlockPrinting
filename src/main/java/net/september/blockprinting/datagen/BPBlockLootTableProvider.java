package net.september.blockprinting.datagen;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import net.september.blockprinting.block.BPBlocks;

import java.util.Set;

public class BPBlockLootTableProvider extends BlockLootSubProvider {

    public BPBlockLootTableProvider() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {

        this.dropSelf(BPBlocks.BISMUTH_BLOCK.get());
        //this.dropSelf(BPBlocks.CANVASTEST.get());
        this.dropSelf(BPBlocks.BASE.get());

    }


    @Override
    protected Iterable<Block> getKnownBlocks() {
        return BPBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
