package net.september.blockprinting;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.september.blockprinting.block.BPBlocks;
import net.september.blockprinting.datagen.Assembly;
import net.september.blockprinting.datagen.Substrate;
import net.september.blockprinting.item.BPItems;

import java.util.List;

public class BPCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BlockPrinting.MOD_ID);

    public static final RegistryObject<CreativeModeTab> BP_TAB = CREATIVE_MODE_TABS.register("bpcreativetab", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(BPItems.MAGIC_INKWELL.get()))
                    .title(Component.translatable("bpcreativetab"))
                    .displayItems((pParameters, pOutput) -> {

                        pOutput.accept(BPItems.DEV_REMOTE.get());
                        pOutput.accept(BPItems.STAMP.get());
                        pOutput.accept(BPItems.THUMBSUP.get());
                        pOutput.accept(BPItems.MAGIC_INKWELL.get());
                        pOutput.accept(BPBlocks.BISMUTH_BLOCK.get());
                        pOutput.accept(BPBlocks.PRINTING_PRESS.get());
                        pOutput.accept(BPBlocks.STAMP_CARVER.get());
                        //pOutput.accept(BPBlocks.BASE.get());
                        //pOutput.accept(BPBlocks.TEST2.get());


                    }).build());

    public static RegistryObject<CreativeModeTab> GENERATEDBLOCKS = CREATIVE_MODE_TABS.register("bpcreativegeneratedblocks", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(BPBlocks.BISMUTH_BLOCK.get())).withSearchBar().displayItems(((pParameters, pOutput) -> {

                //pOutput.acceptAll(List.of(SubstrateAsItemstacks(BPBlocks.BOARDS)));
              //  pOutput.acceptAll(List.of(SubstrateAsItemstacks(BPBlocks.WALLPAPER)));


            })).build());

    public static ItemStack[] SubstrateAsItemstacks(Substrate[] substrates){
        ItemStack[] OutputArray = new ItemStack[Assembly.AssembledCombinations.length];
        int CurrentIndex = 0;
        for (Substrate substrate : substrates){
            OutputArray[CurrentIndex] = new ItemStack(substrate.blockField.get());
            CurrentIndex++;
        }

        return OutputArray;
    }

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
