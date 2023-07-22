package net.september.blockprinting;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.september.blockprinting.block.BPBlocks;
import net.september.blockprinting.item.BPItems;

public class BPCreativeTab {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, BlockPrinting.MOD_ID);

    public static final RegistryObject<CreativeModeTab> BP_TAB = CREATIVE_MODE_TABS.register("bpcreativetab", () ->
            CreativeModeTab.builder().icon(() -> new ItemStack(BPItems.DEV_REMOTE.get()))
                    .title(Component.translatable("bpcreativetab"))
                    .displayItems((pParameters, pOutput) -> {



                        pOutput.accept(BPItems.DEV_REMOTE.get());

                        pOutput.accept(BPBlocks.BISMUTH_BLOCK.get());
                        //pOutput.accept(BPBlocks.CANVASTEST.get());
                        pOutput.accept(BPBlocks.BASE.get());



                            }

                    )

                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
