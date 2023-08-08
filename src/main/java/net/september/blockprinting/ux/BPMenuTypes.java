package net.september.blockprinting.ux;

import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.september.blockprinting.BlockPrinting;


public class BPMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, BlockPrinting.MOD_ID);

    public static final RegistryObject<MenuType<StampCarverMenu>> STAMP_CARVER_MENU =
            MENUS.register("stamp_carver_menu", () -> IForgeMenuType.create(StampCarverMenu::new));

    public static void register(IEventBus eventBus){
        System.out.println("Registering Menus...");
        MENUS.register(eventBus);
        System.out.println("done!");
    }
}

