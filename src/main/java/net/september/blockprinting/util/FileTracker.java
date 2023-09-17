package net.september.blockprinting.util;

import com.google.gson.JsonObject;
import net.minecraft.client.resources.DownloadedPackSource;
import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.repository.RepositorySource;
import net.september.blockprinting.block.printed.PrintedBlockTexture;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class FileTracker {

    //##################//
    // Static Variables //----------------------------------------------------------------------------------------------------
    //##################//

    final static private String LoserDir = System.getProperty("user.dir");
    final static public String UserDir = LoserDir.substring(0, (LoserDir.length() - 3));
    private static final FileSystem DEFAULT_FS = FileSystems.getDefault();
    final static public String packPrefix = "assets/blockprinting/textures/texgen/";
    final static public String texturesPath = "src/main/resources/assets/blockprinting/textures/texgen/";
    final static public Path texturesFolder = Path.of(UserDir + texturesPath);
    final static public int resourceLocBeginIndex = 40;
    final static String resourcesPath = "src/main/resources/assets/blockprinting/";
    final static String metaPath = "src/main/resources/pack.mcmeta";
    final static String packPath = "src/main/custompacks/";
    final static Path packFolder = Path.of(UserDir + packPath);
    final static Path metaLoc = Path.of(UserDir + metaPath);
    final static String zipPath = "src/main/custompacks/bptexgen.zip";
    final static Path zipLocation = Path.of(UserDir + zipPath);
    final static Path resourcesFolder = Path.of(UserDir + resourcesPath);


    //################//
    // Varying Fields //------------------------------------------------------------------------------------------------------
    //################//

    private static List<PackResources> defaultResources = new ArrayList<>();
    private PackRepository Repo;


    //###############//
    // START METHODS // ------------------------------------------------------------------------------------------------------
    //###############//

    public static void compressResourceFolder() throws IOException {
        ZipperUpper zipper = new ZipperUpper(texturesFolder, zipLocation);
        if(youExist(zipLocation)){
            Files.delete(zipLocation);
        }
        zipper.execute();
    }


    public void openRepo() throws IOException {

        //I create a repository source.
        RepositorySource repoSrc = new DownloadedPackSource(packFolder.toFile());

        //I create a pack repository.
        this.Repo = new PackRepository(repoSrc);

        //Then, I clear defaultResources if it currently exists, which it should.
        if (defaultResources.size() > 0) {
            defaultResources.clear();
        }

        //I convert this zip file to a PackResource
        populateDefaultResources();

        //I open a new Texture Factory.
        TextureFactory tf = new TextureFactory();

        //I run the default texgen.
        tf.GenerateDefaultPNGs();

        //I update the zip file with the generated textures.
        populateDefaultResources();

        //I close the repo up again after I'm done.
        closeRepo();
    }

    private void populateDefaultResources() throws IOException {
        compressResourceFolder();
        File ZipLocation = zipLocation.toFile();
        PackResources PR = new FilePackResources(ZipLocation.getName(), ZipLocation, false);
        defaultResources.add(PR);
        //I reload the Repo.
        this.Repo.reload();
        this.Repo.addPack("bptexgen");
    }

    public static boolean youExist(Path path) {
        // NIO Files.exists is notoriously slow when checking the file system - According to Fabric API.
        return path.getFileSystem() == DEFAULT_FS ? path.toFile().exists() : Files.exists(path);
    }

    public static List<PackResources> getDefaultResources() {
        return defaultResources;
    }

    public void closeRepo() {
        this.Repo = null;
    }
}
