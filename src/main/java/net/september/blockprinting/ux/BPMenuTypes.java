package net.september.blockprinting.ux;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.september.blockprinting.BlockPrinting;

public class BPMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, BlockPrinting.MOD_ID);


    public static RegistryObject<MenuType<StampCarverMenu>> STAMP_CARVER_MENU =
            registerMenuType(StampCarverMenu::new, "stamp_carver_menu");

    private static <T extends AbstractContainerMenu> RegistryObject<MenuType<T>> registerMenuType(
            IContainerFactory<T> factory, String name) {
        System.out.println("Registering Menu with the following Parameters: " + factory + ", " + name);
        return MENUS.register(name, () -> IForgeMenuType.create(factory));
    }


    public static void register(IEventBus eventBus){
        System.out.println("Registering Menus...");
        MENUS.register(eventBus);
        System.out.println("done!");
    }

}
