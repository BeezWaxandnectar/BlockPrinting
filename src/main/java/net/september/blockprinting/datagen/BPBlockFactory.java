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


//to do list://

// proof check each individual pattern
// Test and document every color ingame
// Implement this shit in survival???
// Printing press
// Stamps
// FIX SUBSTRATE SHADING
//
// Presets - oh lord
// Recipes for all of the above

// WOULD BE NICE:

// Only generate blocks once they've been programmed ingame :/
// Pin Patterns to the stamp carver
// Teach the provider to read from divvied up Styles

public class BPBlockFactory extends BlockStateProvider{

    public BPBlockFactory(PackOutput output, String modid, ExistingFileHelper XFileHelper) throws IOException {
        super(output, modid, XFileHelper);
    }
    final ResourceLocation base = new  ResourceLocation("blockprinting","block/base");

    protected void registerStatesAndModels() {

        registerStandardBlock(BPBlocks.BISMUTH_BLOCK);
        try {
            //registerLayeredBlock(BPBlocks.BASE,  82,"tropical", "floral", "wallpaper");
            //registerLayeredBlock(BPBlocks.TEST2, 81,"hydra", "argyle", "wallpaper");

           // registerManyLayeredBlocks(BPBlocks.BOARDS, "board");
            registerManyLayeredBlocks(BPBlocks.WALLPAPER, "board");



            //  registerManyLayeredBlocks(BPBlocks.WOOL, "wool");
            //  registerManyLayeredBlocks(BPBlocks.WALLPAPER, "wallpaper");

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

    protected void registerManyLayeredBlocks(Substrate[] SubstrateArray, String SubstrateResourceLocation) throws IOException {
        int CurrentIndex = 0;
        for (Substrate substrate : SubstrateArray){
            Assembly CurrentAssembly = Assembly.AssembledCombinations[CurrentIndex];
            registerLayeredBlock(
                    substrate.blockField,
                    CurrentIndex,
                    CurrentAssembly.swatch,
                    CurrentAssembly.style,
                    SubstrateResourceLocation
            );
            CurrentIndex++;
        }
    }

    protected void registerLayeredBlock(RegistryObject<Block> blockRegistryObject, Integer index, String swatchName, String Style, String substrate) throws IOException {
        Block block = blockRegistryObject.get();

        simpleBlockWithItem( block,
               buildLayeredBlock(
                        getName(block),
                        index,
                        FileHandler.getStyle(Style),
                        FileHandler.getSubstrate(substrate),
                        swatchName
                ));}

    private BlockModelBuilder buildLayeredBlock(String name, Integer index, ResourceLocation style, ResourceLocation substrate, String swatchName){

        Swatch swatch = Swatch.getSwatch(swatchName);
        int BaseColor = swatch.BaseColor;
        int StyleColor = swatch.StyleColor;
        int SubstrateColor = swatch.SubstrateColor;

        System.out.println("Building #" + index + " " + name);

        //TODO: Fix RenderType
        //TODO: Split Substrates off of FileHandler

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
