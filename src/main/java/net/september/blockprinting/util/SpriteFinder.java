package net.september.blockprinting.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.SharedConstants;
import net.minecraft.client.resources.IndexedAssetSource;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.*;
import net.minecraft.server.packs.metadata.pack.PackMetadataSection;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.september.blockprinting.datagen.FileHandler;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SpriteFinder {

    final static String LoserDir = System.getProperty("user.dir");
    final static String UserDir = LoserDir.substring(0, (LoserDir.length()-4));
    final static String destPath = "/src/main/resources/assets/blockprinting/textures/block/";
    final static String indexPath = "/src/main/resources/assets/blockprinting/textures/block/indexes/";
    final static Path generatedFolder = Path.of(UserDir + destPath);
    final static Path indexFolder = Path.of(UserDir + indexPath);
    private static List<PackResources> defaultResources = new ArrayList<>();
    private final static PackMetadataSection VERSION_METADATA_SECTION = new PackMetadataSection(Component.translatable("resourcePack.vanilla.description"), SharedConstants.getCurrentVersion().getPackVersion(PackType.CLIENT_RESOURCES));
    private final static BuiltInMetadata BUILT_IN_METADATA = BuiltInMetadata.of(PackMetadataSection.TYPE, VERSION_METADATA_SECTION);
    private static final Gson Gsonathan = new GsonBuilder()
            .registerTypeAdapter(ResourceLocation.class, new ResourceLocation.Serializer())
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .create();

    //###############//
    // METHODS START //
    //###############//

    private static void savePackMcmeta(Path folder){
        Path path = folder.resolve("pack.mcmeta");
        JsonObject meta = new JsonObject();
        JsonObject pack = new JsonObject();
        pack.addProperty("description", "Testing testing 123");
        pack.addProperty("pack_format", 8);
        meta.add("pack", pack);

        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
            }
            String json = Gsonathan.toJson(meta);
            try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)){
                bufferedWriter.write(json);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void listPacks() throws IOException {
        if (defaultResources.size()> 0){
            defaultResources.clear();
        }

        savePackMcmeta(indexFolder);
        setDefaultResources(FileHandler.StyleMap);
        setDefaultResources(FileHandler.SubstrateMap);
        setDefaultResources(FileHandler.TabulaRasaFiles);
    }

    private static void setDefaultResources(HashMap<String, ResourceLocation> map){

        for (ResourceLocation loc : map.values()){
            Path path = IndexedAssetSource.createIndexFs(generatedFolder, "pack");

            VanillaPackResourcesBuilder builder = (new VanillaPackResourcesBuilder()).setMetadata(BUILT_IN_METADATA).exposeNamespace("minecraft", "realms", "blockprinting");
            VanillaPackResources VPR = builder.applyDevelopmentConfig().pushJarResources().pushAssetPath(PackType.CLIENT_RESOURCES, path).build();

            defaultResources.add(VPR);
        }
    }

    public static NativeImage look(String type, String namespace) throws IOException {
        MultiPackResourceManager manager = new MultiPackResourceManager(PackType.CLIENT_RESOURCES, defaultResources);
        NativeImage output = null;

        if (type.equals("base")){ output = findSprite(FileHandler.getBlankSlate(namespace), manager); }
        if (type.equals("style")){ output = findSprite(FileHandler.getStyle(namespace), manager); }
        if (type.equals("substrate")){ output = findSprite(FileHandler.getSubstrate(namespace), manager); }

        return output;
    }

    public static NativeImage findSprite(ResourceLocation loc, ResourceManager manager) throws IOException {
        Resource resource = manager.getResourceOrThrow(loc);
        return NativeImage.read(resource.open());
    }
}
