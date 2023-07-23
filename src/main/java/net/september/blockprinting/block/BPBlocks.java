package net.september.blockprinting.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.september.blockprinting.BlockPrinting;
import net.september.blockprinting.datagen.Assembly;
import net.september.blockprinting.item.BPItems;

import java.util.function.Supplier;

public class BPBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, BlockPrinting.MOD_ID);

    public Substrate[] BlockRegistryObjectArray(String substrate, BlockBehaviour material, SoundType sound){
        Substrate[] SubstrateArray = new Substrate[Assembly.AssembledCombinations.size()];

            for (Assembly assembly : Assembly.AssembledCombinations){
                RegistryObject<Block> B = registerBlock(assembly.index + "__" + assembly.swatch + "_" + assembly.style + "_" + substrate,
                        () -> new Block(BlockBehaviour.Properties.copy(material).sound(sound)));
                SubstrateArray[assembly.index] = new Substrate(B);
            }
            return SubstrateArray;
    }





    public static final RegistryObject<Block> BISMUTH_BLOCK = registerBlock("bismuth_block", () -> new Block(BlockBehaviour.Properties.copy(Blocks.TUFF).sound(SoundType.TUFF)));
    public static final RegistryObject<Block> BASE = registerBlock("base", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.BAMBOO_WOOD)));
    public static final RegistryObject<Block> TEST2 = registerBlock("test2", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.BAMBOO_WOOD)));
    public static final RegistryObject<Block> BOARDTEST = registerBlock("boardtest", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS).sound(SoundType.WOOD)));

    public Substrate[] BOARD = BlockRegistryObjectArray("board", Blocks.OAK_PLANKS, SoundType.WOOD);


 /*   public static final RegistryObject<Block> WOOLTEST = registerBlock("wooltest", () -> new Block(BlockBehaviour.Properties.copy(Blocks.WHITE_WOOL).sound(SoundType.WOOL)));

    public static final RegistryObject<Block> TERRACOTTATEST = registerBlock("terracottatest", () -> new Block(BlockBehaviour.Properties.copy(Blocks.TERRACOTTA).sound(SoundType.STONE)));
*/




    //##################//
    // V-GENERIC CODE-V //
    //##################//

    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block) {
        return BPItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {BLOCKS.register(eventBus);}

}

 class Substrate{
    RegistryObject<Block> blockField;

    public Substrate(RegistryObject<Block> blockField){
        this.blockField = blockField;
    }
}