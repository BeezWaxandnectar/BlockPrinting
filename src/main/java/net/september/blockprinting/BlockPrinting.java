package net.september.blockprinting;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.september.blockprinting.block.BPBlocks;
import net.september.blockprinting.block.entity.BPBlockEntities;
import net.september.blockprinting.datagen.*;
import net.september.blockprinting.dyesystem.PlayerDyeProvider;
import net.september.blockprinting.item.BPItemProperties;
import net.september.blockprinting.item.BPItems;
import net.september.blockprinting.ux.BPMenuTypes;
import net.september.blockprinting.ux.StampCarverScreen;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(BlockPrinting.MOD_ID)
@Mod.EventBusSubscriber(modid = BlockPrinting.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class BlockPrinting
{
    public static final String MOD_ID = "blockprinting";
    private static final Logger LOGGER = LogUtils.getLogger();

    public BlockPrinting()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        FileHandler.CreateMaps();
        Swatch.CreateSwatchMap();

        BPBlockEntities.register(modEventBus);
        BPMenuTypes.register(modEventBus);

        Assembly.RunTheAssemblinator();

        BPItems.register(modEventBus);
        BPBlocks.register(modEventBus);
        BPCreativeTab.register(modEventBus);

        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
    }


    private void commonSetup(final FMLCommonSetupEvent event)
    {}


    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) throws IOException {
        System.out.println("GATHERING DATA");
        DataGenerator generator = event.getGenerator();
        PackOutput packOutput = generator.getPackOutput();
        ExistingFileHelper XFileHelper = event.getExistingFileHelper();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        generator.addProvider(true, new BPItemModelProvider(packOutput, XFileHelper));
        generator.addProvider(true, new BPBlockFactory(packOutput, "blockprinting", XFileHelper));
        generator.addProvider(true, BPLootTableProvider.create(packOutput));
        generator.addProvider(true, new BPLang(packOutput, "blockprinting", "en_us"));
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event)
    {}
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            MenuScreens.register(BPMenuTypes.STAMP_CARVER_MENU.get(), StampCarverScreen::new);
            System.out.println("Detected Carver: " + BPBlockEntities.STAMP_CARVER.get());
        }
        @SubscribeEvent
        public static void onModelRegister(ModelEvent.RegisterAdditional event){
            BPItemProperties.init((itemLike, resourceLocation, clampedItemPropertyFunction) -> ItemProperties.register(itemLike.asItem(), resourceLocation, clampedItemPropertyFunction));
        }

    }
}
