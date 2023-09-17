package net.september.blockprinting.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.september.blockprinting.BlockPrinting;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class FileMaps {


    public FileMaps() throws IOException {
    }

    private static Path locateResource(String folder) {
        //Method Credit: TheSilkMiner
        Objects.requireNonNull(folder);
        if (!ResourceLocation.isValidPath(folder)) {
            throw new IllegalArgumentException("locateResource didn't work!!!");
        }
        return ModList.get()
                .getModFileById(BlockPrinting.MOD_ID)
                .getFile()
                .findResource(folder);
    }

    private static Set<String> AssignTextureNames(String folder) throws IOException {
        Path folderpath = locateResource(folder);
        Set<String> fileSet = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Path.of(FileTracker.UserDir + folderpath))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileSet.add((path.getFileName().toString()).substring(0, ((path.getFileName().toString()).length()-4)));
                }
            }
        }
        return fileSet;
    }

    private static HashMap<String, ResourceLocation> NewMap(String folder) throws IOException {
        Set<String> TextureNames = AssignTextureNames(folder);
        HashMap<String, ResourceLocation> NewMap = new HashMap<>();


        TextureNames.forEach(key -> NewMap.put(key, new ResourceLocation(BlockPrinting.MOD_ID, folder.substring(FileTracker.resourceLocBeginIndex) + (key) )));
        return NewMap;
    }

    public static HashMap<String, ResourceLocation> StyleMap;
    public static HashMap<String, ResourceLocation> SubstrateMap;
    public static HashMap<String, ResourceLocation> BlankSlateFiles;

    public static void CreateMaps() {
        try {
            StyleMap = NewMap( FileTracker.texturesPath + "styles/");
            SubstrateMap = NewMap(FileTracker.texturesPath + "substrates/");
            BlankSlateFiles = NewMap(FileTracker.texturesPath + "blankslates/");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResourceLocation getStyle(String key) throws IOException {return StyleMap.get(key);}
    public static ResourceLocation getSubstrate(String key) throws IOException {return SubstrateMap.get(key);}
    public static ResourceLocation getBlankSlate(String key) throws IOException {return BlankSlateFiles.get(key);}

    public static Set<String> getAllStyles() {return StyleMap.keySet();}
    public static Set<String> getAllSubstrates() {return SubstrateMap.keySet();}
    public static Set<String> getAllBlankSlates() {return BlankSlateFiles.keySet();}
   /* public static Collection<ResourceLocation> getStyleFiles() {return StyleMap.values();}
    public static Collection<ResourceLocation> getSubstrateFiles() {return SubstrateMap.values();}
    public static Collection<ResourceLocation> getTabulaFiles() {return BlankSlateFiles.values();}
*/
    public static boolean isValidStyle(String input){return StyleMap.containsKey(input);}
    public static boolean isValidSubstrate(String input){return SubstrateMap.containsKey(input);}

}
