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

    }//*/


    //##############################################//
    //# I'm pretty sure this method is the problem #//
    //##VVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVVV##//

    //TODO Find out why.
    /*private static Stream<String> TextureNames(String folder) throws IOException {

        Path folderpath = locateResource(folder);
        System.out.println("Method TextureNames activates");
        System.out.println(" > Read Folderpath= " + folderpath);

        if (folderpath.isAbsolute()){
            System.out.println(" > Path reads as absolute!");
        } else {
            System.out.println(" > Path is not absolute, but does that mean it can't be read?");
        }

      Stream<Path> files = Files.list(folderpath);

      return files.map(String::valueOf);

    }*/


    public static Set<String> TextureNames(String folder) throws IOException {

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


    /*

public Set<String> listFilesUsingDirectoryStream(String dir) throws IOException {
    Set<String> fileSet = new HashSet<>();
    try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get(dir))) {
        for (Path path : stream) {
            if (!Files.isDirectory(path)) {
                fileSet.add(path.getFileName()
                    .toString());
            }
        }
    }
    return fileSet;
}
*/


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

}

/*
/############################################################################################################

public interface OldFileHandler extends DataProvider {

    private static ArrayList<String> listNamesOfTextures(Path folder) throws IOException {
        Stream<Path> Textures = Files.list(folder);
        ArrayList<String> TextureNames = new ArrayList<String>();
        Textures.forEach(Texture -> TextureNames.add(String.valueOf(Texture)));
        return TextureNames;
    }
    static HashMap<String, ResourceLocation> NewMap(Path folder) throws IOException {
        HashMap<String, ResourceLocation> NewMap = new HashMap<>();
        ArrayList<String> TextureNames = listNamesOfTextures(folder);

        for(String TextureName : TextureNames) {
            String Key = TextureName;

            if (TextureName.endsWith(".png")){
                Key = TextureName.substring(0, (TextureName.length()-4));

            } else if (TextureName.startsWith(String.valueOf(folder))) {
                Key = TextureName.substring(String.valueOf(folder).length() + 1);

            } else if (TextureName.startsWith(String.valueOf(folder)) && TextureName.endsWith(".png")){
                Key = TextureName.substring((String.valueOf(folder).length() + 1), (TextureName.length()-4));

            } else if (NewMap.size() < TextureNames.size()){
                NewMap.put(Key, new ResourceLocation("blockprinting:block/bpstylesfolder/" + Key));

            }}
        return NewMap;
    }
}
*/
