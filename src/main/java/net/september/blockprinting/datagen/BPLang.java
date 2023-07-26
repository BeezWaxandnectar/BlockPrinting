package net.september.blockprinting.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.september.blockprinting.block.BPBlocks;
import net.september.blockprinting.item.BPItems;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public class BPLang extends LanguageProvider {

   public BPLang(PackOutput output, String modid, String locale) {
      super(output, modid, locale);
   }

   //block.blockprinting.quartz_panelopera_wallpaper -> Quartz Panelopera Wallpaper

   public String[] decomposename(@NotNull RegistryObject<Block> block) {
      String[] assembly = new String[3];

      String Name = getName(block.get());

      int FirstUnderscore = Name.indexOf("_");
      int SecondUnderscore = Name.lastIndexOf("_");

      assembly [0] = Name.substring(0, FirstUnderscore);
      assembly [1] = Name.substring(FirstUnderscore + 1, SecondUnderscore);
      assembly [2] = Name.substring(SecondUnderscore + 1);

      return assembly;
   }

   private static String Capitalize(String input){
      return input.substring(0, 1).toUpperCase() + input.substring(1);
   }

   //--Untouched Private Methods from the Vanilla BlockRegistryProvider--
   private String getName(Block block) { return key(block).getPath(); }

   private ResourceLocation key(Block block) {
      return ForgeRegistries.BLOCKS.getKey(block);
   }

   @Override
   protected void addTranslations() {

      addManyBlocks(BPBlocks.WALLPAPER);

      // --- non-Datagen --- //

      addBlock(BPBlocks.BISMUTH_BLOCK, "Bismuth Block");

      add("bpcreativetab", "Block Printing Devtab");

      addItem(BPItems.DEV_REMOTE, "Developer's Remote");
      addItem(BPItems.MAGIC_INKWELL, "Magic Inkwell");


   }
   protected void addManyBlocks(Substrate[] inputArray) {

      int CurrentIndex = 0;
      for( Substrate substrate : inputArray){
         RegistryObject<Block> currentBlock = substrate.blockField;

         String[] currentBlockNameData = decomposename(currentBlock);

         String Swatch = Capitalize(currentBlockNameData[0]);
         String Style = Capitalize(currentBlockNameData[1]);
         String Substrate = Capitalize(currentBlockNameData[2]);

         String EnglishName = Swatch + " " + Style + " " + Substrate;

         addBlock(currentBlock, EnglishName);

      }

   }

}
