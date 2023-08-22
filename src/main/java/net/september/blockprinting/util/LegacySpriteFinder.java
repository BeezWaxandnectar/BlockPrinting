package net.september.blockprinting.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.september.blockprinting.datagen.FileHandler;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class LegacySpriteFinder {

    public static HashMap<String, NativeImage> DefaultStyles;
    public static HashMap<String, NativeImage> DefaultSubstrates;
    public static HashMap<String, NativeImage> TabulaRasa;

    public static HashMap<String, NativeImage> OpenTextures;





    final static String LoserDir = System.getProperty("user.dir");
    final static String UserDir = LoserDir.substring(0, (LoserDir.length()-4));
    final static String destPath = "/src/main/resources/assets/blockprinting/textures/block/";
    final static String ledgerPath = "/src/main/resources/assets/blockprinting/textures/block/ledgers/";



    private static final Gson Gsonathan = new GsonBuilder()
            .setPrettyPrinting()
            .create();




    //################//
    // Default Images //------------------------------------------------------------------------------------------------
    //################//
    private static void MapDefaultStyles(ExistingFileHelper XFileHelper) throws IOException {
        for (String style : FileHandler.getAllStyles()) {
            ResourceLocation RL = FileHandler.getStyle(style);
            Resource resource = XFileHelper.getResource(RL, PackType.CLIENT_RESOURCES);
            NativeImage img = NativeImage.read(resource.open());
            DefaultStyles.put(style, img);
        }
    }
    private static void MapDefaultSubstrates(ExistingFileHelper XFileHelper)  throws IOException {
        for (String substrate : FileHandler.getAllSubstrates()) {
            ResourceLocation RL = FileHandler.getSubstrate(substrate);
            Resource resource = XFileHelper.getResource(RL, PackType.CLIENT_RESOURCES);
            NativeImage img = NativeImage.read(resource.open());
            DefaultSubstrates.put(substrate, img);
        }

    }
    private static void MapTabulaRasa(ExistingFileHelper XFileHelper)  throws IOException {
        for (String slate : FileHandler.getTabulaRasa()){
            ResourceLocation RL = FileHandler.getBlankSlate(slate);
            Resource resource = XFileHelper.getResource(RL, PackType.CLIENT_RESOURCES);
            NativeImage img = NativeImage.read(resource.open());
            TabulaRasa.put(slate, img);
        }
    }

    public void MapDefaultTextures(ExistingFileHelper XFileHelper) throws IOException {
        MapDefaultStyles(XFileHelper);
        MapDefaultSubstrates(XFileHelper);
        MapTabulaRasa(XFileHelper);

        ArchiveDefaultTextures(XFileHelper);
    }
    public void ArchiveDefaultTextures (ExistingFileHelper XFileHelper) throws IOException {
        createTexLedgerIfNeeded("defaultstyles.json");
        createTexLedgerIfNeeded("defaultsubstrates.json");
        createTexLedgerIfNeeded("tabularasa.json");

        overwriteTexLedger("defaultstyles.json", DefaultStyles);
        overwriteTexLedger("defaultsubstrates.json", DefaultSubstrates);
        overwriteTexLedger("tabularasa.json", TabulaRasa);
    }

    public void createTexLedgerIfNeeded(String jsonTitle) throws IOException {
        Path path = Path.of(UserDir + ledgerPath + jsonTitle);
        if (!Files.exists(path)){
            Files.createFile(path);
        }
    }

    public void overwriteTexLedger(String jsonTitle, HashMap<String, NativeImage> inputMap) throws IOException {
        Path path = Path.of(UserDir + ledgerPath + jsonTitle);
        //TODO: make it just be a file to begin with.
        Gsonathan.toJson(inputMap, new FileWriter(path.toFile()));
    }

/*
    public HashMap<String, NativeImage> readTexLedger(String jsonTitle) throws IOException {
        Path path = Path.of(UserDir + ledgerPath + jsonTitle);
        InputStream stream = Files.newInputStream(path);



        try(BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8))) {
            JsonObject JO = GsonHelper.parse(reader);
            HashMap<String, NativeImage> output = new HashMap<>();
        }



        Gsonathan.fromJson();
    }
*/


    private static void saveImage(Path folder, ResourceLocation location, NativeImage image) {
        Path path = folder.resolve(Paths.get(PackType.CLIENT_RESOURCES.getDirectory(),
                location.getNamespace(), destPath, location.getPath() + ".png"));

        try {
            Files.createDirectories(path.getParent());
            image.writeToFile(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void savePackMcmeta(){
        Path path = (Path.of(destPath)).resolve("/indexes/pack.mcmeta");
        JsonObject meta = new JsonObject();
        JsonObject pack = new JsonObject();

        pack.addProperty("description", "Textures Generated by BlockPrinting");
        pack.addProperty("pack_format", 8);
        meta.add("pack", pack);

        try {
            Files.createDirectories(path.getParent());
            String json = Gsonathan.toJson(meta);
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)){
                bufferedWriter.write(json);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    //########//
    // LEGACY // ---------------------------------------------------------------------------------------------------------
    //########//
/*

    static List<PackResources> ClientResources = new ArrayList<>();
    static List<PackResources> ServerResources = new ArrayList<>();
    private MultiPackResourceManager assetManager = new MultiPackResourceManager(PackType.CLIENT_RESOURCES, ClientResources);
    private MultiPackResourceManager dataManager = new MultiPackResourceManager(PackType.SERVER_DATA, ServerResources);
    private ResourceManager getManager(PackType type){
        return type == PackType.CLIENT_RESOURCES ? assetManager : dataManager;
    }


    private static void init(){
        MinecraftForge.EVENT_BUS.addListener();
    }
    private static final Multimap<PackType, ResourceLocation> DefaultTextures = HashMultimap.create();
    private static Multimap<PackType, ResourceLocation> GeneratedTextures = HashMultimap.create();

    boolean existsByDefault(ResourceLocation loc, PackType packType) {
        return DefaultTextures.get(packType).contains(loc) || getManager(packType).getResource(loc).isPresent();
    }

    public static void trackGenerated(ResourceLocation loc, PackType packType){

    }

    private static boolean wasGenerated(ResourceLocation loc, PackType packType){
        return false;
    }

    public static void GrabDefaultAssets() throws IOException {
        List<PackResources> clientResources = new ArrayList<>();
        List<PackResources> serverResources = new ArrayList<>();
        String styleFolder = UserDir + "/src/main/resources/assets/blockprinting/textures/block/bpstylesfolder/";
        String substrateFolder = UserDir + "/src/main/resources/assets/blockprinting/textures/block/bpsubstratesfolder/";


        clientResources.add(ClientPackSource.createVanillaPackSource(IndexedAssetSource.createIndexFs(Path.of(packFolder), "pack")));
        serverResources.add(ServerPacksSource.createVanillaPackSource());

        for (String style : FileHandler.getAllStyles()) {
            File file = Path.of(styleFolder, style, ".png").toFile();

            PackResources pack = new FilePackResources(file.getName(), file, false);

            clientResources.add(pack);
            serverResources.add(pack);
        }

        for (String substrate : FileHandler.getAllSubstrates()) {
            File file = Path.of(substrateFolder, substrate, ".png").toFile();

            PackResources pack = new FilePackResources(file.getName(), file, false);

            clientResources.add(pack);
            serverResources.add(pack);
        }

        IModFileInfo modFileInfo = ModList.get().getModFileById(BlockPrinting.MOD_ID);
        if (modFileInfo != null) {
            PackResources pack = ResourcePackLoader.createPackForMod(modFileInfo);
            clientResources.add(pack);
            serverResources.add(pack);
        }

        ClientResources.addAll(clientResources);
        ServerResources.addAll(serverResources);

    }




    public static Gson GSON = (new GsonBuilder())
            .registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer())
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();




    private NativeImage read(ResourceLocation loc, PackType packType) throws IOException{
        Resource resource = getManager(packType).getResourceOrThrow(loc);
        return NativeImage.read(resource.open());
    }

    public NativeImage readExisting(ResourceLocation loc, PackType packType) throws IOException{
        if (existsByDefault(loc, packType)){
            try{
                return read(loc, packType);
            } catch (IOException e){
                if (wasGenerated(loc, packType)) {
                    try{
                        return read(loc, packType);
                    } catch (IOException f) {
                        System.out.println("This shouldn't be printed.");
                    }
                }
            }
        }
        return null;
    }
*/


    //WHAT THIS CACHE NEEDS TO DO :
    // - Read sprites from the mod
    // - Construct blocks from each sprite.
    // - write the data to a json PER WORLD?
    // - read the data from said json

    //public HashCache BPHashCache = new HashCache(destFolder, );


}
