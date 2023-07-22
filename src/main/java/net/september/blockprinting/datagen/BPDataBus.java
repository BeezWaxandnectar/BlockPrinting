package net.september.blockprinting.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.september.blockprinting.BlockPrinting;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = BlockPrinting.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BPDataBus {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) throws IOException {
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper XFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(true, new BPItemModelProvider(packOutput, "blockprinting", XFileHelper));
        generator.addProvider(true, new BPBlockStateProvider(packOutput, "blockprinting", XFileHelper));
        generator.addProvider(true, BPLootTableProvider.create(packOutput));
    }
}
