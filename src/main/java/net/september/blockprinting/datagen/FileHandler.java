package net.september.blockprinting.datagen;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.ModList;
import net.september.blockprinting.BlockPrinting;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Stream;


public abstract class FileHandler {


    public FileHandler() throws IOException {
    }
    //I don't know why I have to do it like this, but I do.
    static final String LoserDir = System.getProperty("user.dir");
    static final String UserDir = LoserDir.substring(0, (LoserDir.length()-4));

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

    private static Set<String> TextureNames(String folder) throws IOException {
        Path folderpath = locateResource(folder);
        Set<String> fileSet = new HashSet<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Path.of(UserDir + folderpath))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    fileSet.add((path.getFileName().toString()).substring(0, ((path.getFileName().toString()).length()-4)));
                }
            }
        }
        return fileSet;
    }

    private static HashMap<String, ResourceLocation> NewMap(String folder) throws IOException {
        Set<String> TextureNames = TextureNames(folder);
        HashMap<String, ResourceLocation> NewMap = new HashMap<>();


        TextureNames.forEach(key -> NewMap.put(key, new ResourceLocation(BlockPrinting.MOD_ID, folder.substring(50) + (key))));
        return NewMap;
    }

    public static HashMap<String, ResourceLocation> StyleMap;
    public static HashMap<String, ResourceLocation> SubstrateMap;

    public static HashMap<String, ResourceLocation> TabulaRasaFiles;

    public static void CreateMaps() {
        try {
            StyleMap = NewMap("/src/main/resources/assets/blockprinting/textures/block/bpstylesfolder/");
            SubstrateMap = NewMap("/src/main/resources/assets/blockprinting/textures/block/bpsubstratesfolder/");
            TabulaRasaFiles = NewMap("/src/main/resources/assets/blockprinting/textures/block/tabularasa/");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static ResourceLocation getStyle(String key) throws IOException {return StyleMap.get(key);}
    public static ResourceLocation getSubstrate(String key) throws IOException {return SubstrateMap.get(key);}
    public static ResourceLocation getBlankSlate(String key) throws IOException {return TabulaRasaFiles.get(key);}

    public static Set<String> getAllStyles() {return StyleMap.keySet();}
    public static Set<String> getAllSubstrates() {return SubstrateMap.keySet();}
    public static Set<String> getTabulaRasa() {return TabulaRasaFiles.keySet();}





}
