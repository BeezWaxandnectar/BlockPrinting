package net.september.blockprinting.util;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.MultiPackResourceManager;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.september.blockprinting.datagen.FileHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class SpriteFinder {

    private static List<PackResources> defaultResources;

    public static void listPacks() throws IOException {
        defaultResources.clear();

        setDefaultResources(FileHandler.StyleMap);
        setDefaultResources(FileHandler.SubstrateMap);
        setDefaultResources(FileHandler.TabulaRasaFiles);

    }

    private static void setDefaultResources(HashMap<String, ResourceLocation> map){

        for (ResourceLocation loc : map.values()){



        }

    }

  /*  final static String LoserDir = System.getProperty("user.dir");
    final static String UserDir = LoserDir.substring(0, (LoserDir.length()-4));
    final static String destPath = UserDir + "/src/main/resources/assets/blockprinting/textures/block/";
*/
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
