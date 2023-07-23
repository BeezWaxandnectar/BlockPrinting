package net.september.blockprinting.datagen;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.september.blockprinting.BlockPrinting;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;


public class FileHandler {


    public FileHandler() throws IOException {
    }
    static String LoserDir = System.getProperty("user.dir");
    static String UserDir = LoserDir.substring(0, (LoserDir.length()-4));

    static Path locateResource(String folder) {
        //Method Credit: TheSilkMiner
        System.out.println("locateResource activates");
        Objects.requireNonNull(folder);
        if (!ResourceLocation.isValidPath(folder)) {
            throw new IllegalArgumentException("locateResource didn't work!!!");
        } else {
            System.out.println(" > Entered String: " + folder);
        }

        Path output = ModList.get()
                .getModFileById(BlockPrinting.MOD_ID)
                .getFile()
                .findResource(folder);
        System.out.println(" > Output String:  " + output);
        return output;

    }

    public static Set<String> TextureNames(String folder) throws IOException {
        // Method Credit: Baeldung
        Path folderpath = locateResource(folder);
        Set<String> fileSet = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Path.of(UserDir + folderpath))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileSet.add((path.getFileName().toString()).substring(0, ((path.getFileName().toString()).length()-4)));
                }
            }
        }
        System.out.println(fileSet);
        return fileSet;
    }

    static String StringerBell(Path input){
        //placeholder string manipulator
        return String.valueOf(input);
    }

    public static HashMap<String, ResourceLocation> NewMap(String folder) throws IOException {
        Set<String> TextureNames = TextureNames(folder);
        System.out.println("NewMap activates for " + folder);
        HashMap<String, ResourceLocation> NewMap = new HashMap<>();


        TextureNames.forEach(key -> NewMap.put(key, new ResourceLocation("blockprinting", folder.substring(50) + (key))));
        System.out.println(" > Final Map Contents: " + NewMap);
        return NewMap;
    }

    public static HashMap<String, ResourceLocation> StyleMap;
    public static HashMap<String, ResourceLocation> SubstrateMap;

    public static void CreateMaps() {
        System.out.println(UserDir);
        try {
            StyleMap = NewMap("/src/main/resources/assets/blockprinting/textures/block/bpstylesfolder/");
            SubstrateMap = NewMap("/src/main/resources/assets/blockprinting/textures/block/bpsubstratesfolder/");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResourceLocation getStyle(String key) throws IOException {return StyleMap.get(key);}
    public static ResourceLocation getSubstrate(String key) throws IOException {return SubstrateMap.get(key);}

    public static Set<String> getAllStyles() {return StyleMap.keySet();}
    public static Set<String> getAllSubstrates() {return SubstrateMap.keySet();}



}
