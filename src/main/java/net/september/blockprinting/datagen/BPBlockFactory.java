package net.september.blockprinting.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.september.blockprinting.BlockPrinting;
import net.september.blockprinting.block.BPBlocks;

import java.io.IOException;



public class BPBlockFactory extends BlockStateProvider{
    public BPBlockFactory(PackOutput output, String modid, ExistingFileHelper XFileHelper) throws IOException {
        super(output, modid, XFileHelper);
    }
    final ResourceLocation base = new  ResourceLocation("blockprinting","block/base");




    //to do list://

    // Teach the provider to generate Lang files
    // Teach the provider to add an object to LootTableProvider
    // Teach the provider to read from substrate setting files?
    // Teach the provider to make every possible block.
    // Implement this shit in survival???
            // Printing press
            // Stamps / Stamp carver
            //
            // Presets - oh lord
            // Recipes for all of the above

    // WOULD BE NICE:

    // Only generate blocks once they've been programmed ingame :/
    // Teach the provider to read from divvied up Styles

    protected void registerStatesAndModels() {
        FileHandler.CreateMaps();
        Swatch.CreateSwatchMap();

        registerStandardBlock(BPBlocks.BISMUTH_BLOCK);
        try {
            registerLayeredBlock(BPBlocks.BASE, "floral", "wallpaper","tropical");
            registerLayeredBlock(BPBlocks.TEST2, "argyle", "wallpaper","hydra");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private void registerStandardBlock(RegistryObject<Block> block){
        simpleBlockWithItem(block.get(), cubeAll(block.get()));
    }

    //###########//
    //v-METHODS-v//
    //###########//

    protected void registerLayeredBlock(RegistryObject<Block> blockRegistryObject, String Style, String Substrate, String swatchName) throws IOException {
        Block block = blockRegistryObject.get();

        simpleBlockWithItem( block,
               buildLayeredBlock(
                        getName(block),
                        FileHandler.getStyle(Style),
                        FileHandler.getSubstrate(Substrate),
                        swatchName
                ));}

    private BlockModelBuilder buildLayeredBlock(String name, ResourceLocation style, ResourceLocation substrate, String swatchName){

        Swatch swatch = Swatch.getSwatch(swatchName);
        int BaseColor = swatch.BaseColor;
        int StyleColor = swatch.StyleColor;
        int SubstrateColor = swatch.SubstrateColor;

        System.out.println("Building " + swatchName + " " + style + " " + substrate);

        //TODO: Fix RenderType

        return models().cube(name, base, base, base, base, base, base)
                .texture("style", style)
                .texture("substrate", substrate)
                .texture("particle", base)
                .renderType("minecraft:translucent")

                //BASE//
                .element()
                .from(0,0,0).to(16,16,16)
                .allFaces((Direction, FaceBuilder) -> FaceBuilder
                        .uvs(0,0,16,16)
                        .texture("#" + Direction.getName())
                        .color(BaseColor)
                        .end()).end()

                //STYLE//
                .element()
                .from(0,0,0).to(16,16,16)
                .allFaces((Direction, FaceBuilder) -> FaceBuilder
                        .uvs(0,0,16,16)
                        .texture("#" + "style")
                        .color(StyleColor)
                        .end()).end()

                //SUBSTRATE//
                .element()
                .from(0,0,0).to(16,16,16)
                .allFaces((Direction, Facebuilder) -> Facebuilder
                        .uvs(0,0,16,16)
                        .texture("#" + "substrate")
                        .color(SubstrateColor)
                        .end()).end();


    }


    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, BlockPrinting.MOD_ID);


    //--Untouched Private Methods from the Vanilla BlockRegistryProvider--
    private String getName(Block block) { return key(block).getPath(); }

    private ResourceLocation key(Block block) {
        return ForgeRegistries.BLOCKS.getKey(block);
    }


}
