package net.september.blockprinting.datagen;

import net.minecraft.data.PackOutput;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.september.blockprinting.item.BPItems;

public class BPItemModelProvider extends ItemModelProvider {

    public BPItemModelProvider(PackOutput output, String modid, ExistingFileHelper existingFileHelper) {
        super(output, modid, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        basicItem(BPItems.DEV_REMOTE.get());
    }
}
