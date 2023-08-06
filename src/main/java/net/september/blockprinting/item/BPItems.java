package net.september.blockprinting.item;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.september.blockprinting.BlockPrinting;
import net.september.blockprinting.item.custom.MagicInkwellItem;

public class BPItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, BlockPrinting.MOD_ID);


    public static final RegistryObject<Item> DEV_REMOTE = ITEMS.register("dev_remote", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STAMP = ITEMS.register("stamp", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> THUMBSUP = ITEMS.register("thumbsup", () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MAGIC_INKWELL = ITEMS.register("magic_inkwell", () -> new MagicInkwellItem(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus){ITEMS.register(eventBus);}
}



