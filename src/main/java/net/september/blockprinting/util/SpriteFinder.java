package net.september.blockprinting.util;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.*;
import net.minecraft.server.packs.resources.*;

import java.io.IOException;

public class SpriteFinder {


    public static NativeImage lookup(String type, String namespace) throws IOException {
        MultiPackResourceManager manager = new MultiPackResourceManager(PackType.CLIENT_RESOURCES, FileTracker.getDefaultResources());
        NativeImage output = null;

        if (type.equals("base")){ output = findSprite(FileMaps.getBlankSlate(namespace), manager); }
        if (type.equals("style")){ output = findSprite(FileMaps.getStyle(namespace), manager); }
        if (type.equals("substrate")){ output = findSprite(FileMaps.getSubstrate(namespace), manager); }

        return output;
    }

    public static NativeImage findSprite(ResourceLocation loc, ResourceManager manager) throws IOException {
        Resource resource = manager.getResourceOrThrow(addPngTo(loc));
        return NativeImage.read(resource.open());
    }

    public static ResourceLocation addPngTo(ResourceLocation input){
        return new ResourceLocation(input.getNamespace(), input.getPath() + ".png");
    }
}
