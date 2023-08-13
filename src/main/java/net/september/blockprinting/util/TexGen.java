package net.september.blockprinting.util;

import com.mojang.blaze3d.platform.NativeImage;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.september.blockprinting.BlockPrinting;
import net.september.blockprinting.datagen.Swatch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Set;

public class TexGen {
    //I don't know why I have to do it like this, but I do.
    static final String LoserDir = System.getProperty("user.dir");
    static final String UserDir = LoserDir.substring(0, (LoserDir.length()-4));
    static String tabulaFolder = "/src/main/resources/assets/blockprinting/textures/block/tabularasa";
    static String destPath = "/src/main/resources/assets/blockprinting/textures/block/generated/";
    static String destFolder = (UserDir + destPath);

    public static void GeneratePNGs(ExistingFileHelper XFileHelper) throws IOException {

        refreshGeneration();

        generateAssembly("tropical", "panelopera", XFileHelper);

    }

    public static void generateAssembly(String swatchname, String stylename, ExistingFileHelper XFileHelper) throws IOException {
        try{
            NativeImage base = FromExisting("base", "base", XFileHelper);
            NativeImage style = FromExisting("style", stylename, XFileHelper);

            String Namespace = swatchname + "_" + stylename;

            Swatch swatch = Swatch.getSwatch(swatchname);

            int baseColor = swatch.BaseColor;
            int styleColor = swatch.StyleColor;

            NativeImage assembly = FromParents("binary", base, baseColor, style, styleColor, Namespace, XFileHelper, 1.0f);

            Path destination = Path.of(destFolder + Namespace + ".png");

            assembly.writeToFile(destination);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static NativeImage FromExisting(String type, String Namespace, ExistingFileHelper XFileHelper){

        try{
        String sourcePath = null;

            if (type.equals("new")){sourcePath = "textures/block/generated/";}
            else if (type.equals("base")){sourcePath = "textures/block/";}
            else if (type.equals("style")){sourcePath = "textures/block/bpstylesfolder/";}
            else if (type.equals("substrate")){sourcePath = "textures/block/bpsubstratesfolder/";}
            else {throw new RuntimeException();}

            String NamespacePng = Namespace + ".png";

            if (type.equals("new")){
                Path tabula = Path.of(destFolder + NamespacePng);
                Files.createFile(tabula);
            }

            ResourceLocation sourceRL = new ResourceLocation(BlockPrinting.MOD_ID, sourcePath + NamespacePng);
            Resource resource = XFileHelper.getResource(sourceRL, PackType.CLIENT_RESOURCES);
            System.out.println("Source Resource Location: " + BlockPrinting.MOD_ID + sourcePath + NamespacePng);

            return NativeImage.read(resource.open());

        } catch (IOException e){
            throw new RuntimeException(e);
        }
    }


    public static @NotNull NativeImage FromParents(String function,
                                                   NativeImage base, @Nullable Integer baseColor,
                                                   NativeImage overlay, @Nullable Integer overlayColor,
                                                   String Namespace, ExistingFileHelper XFileHelper,
                                                   float alphaCoefficient){
        if (baseColor != null){
            SpecifyColor(base, baseColor);
        }
        if (overlayColor != null){
            SpecifyColor(overlay, overlayColor);
        }

        NativeImage output = FromExisting("new", Namespace, XFileHelper);

        for (int x = 0; x < base.getWidth(); x++){
            for (int y = 0; y < base.getHeight(); y++){
                int[] baseData = separateColors(makeABGR(base.getPixelRGBA(x, y)));
                int[] overlayData = separateColors(makeABGR(overlay.getPixelRGBA(x, y)));

                if (overlayData[0] == 0){
                    output.setPixelRGBA(x, y, assembleColors(baseData));
                } else {
                    switch (function) {
                        case "binary" -> output.setPixelRGBA(x, y, assembleColors(overlayData));
                        case "blend" -> {
                            int blendedColors = assembleColors(blendColors(baseData, overlayData, alphaCoefficient));
                            output.setPixelRGBA(x, y, blendedColors);
                        }
                        case "multiply" -> {
                            int[] multiplyData = multiplyColors(baseData, overlayData);
                            int multipliedColors = assembleColors(blendColors(baseData, multiplyData, alphaCoefficient));
                            output.setPixelRGBA(x, y, multipliedColors);
                        }
                    }
                }
            }
        }
        return output;
    }

    private static void refreshGeneration() throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Path.of(destFolder))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    Files.delete(path);
                }
            }
        }
    }

    //#############//
    // COLOR LOGIC //
    //#############//

    public static void SpecifyColor(NativeImage img, int color){
        for (int x = 0; x < img.getWidth(); x++){
            for (int y = 0; y < img.getHeight(); y++){
                if (img.getLuminanceOrAlpha(x, y) != 0){
                    int[] pixelData = separateColors(makeABGR(img.getPixelRGBA(x, y)));
                    int[] colorData = separateColors(makeABGR(color));
                    colorData[0] = pixelData[0];
                    int finalcolor = assembleColors(colorData);

                    img.setPixelRGBA(x, y, makeABGR(finalcolor));
                }}}}

    private static int makeABGR(int color){
        return (color & 0xFF00FF00) | (((color & 0x00FF0000) >> 16) & 0x000000FF) | (((color & 0x000000FF) << 16) & 0x00FF0000);
    }

    private static int[] separateColors(int abgr){
        int[] ColorData = new int[4];
        ColorData[0] = (abgr & 0xFF000000) >> 24; //ALPHA
        ColorData[1] = (abgr & 0x00FF0000) >> 16; //BLUE
        ColorData[2] = (abgr & 0x0000FF00) >> 8;  //GREEN
        ColorData[3] = (abgr & 0x000000FF);       //RED
        return ColorData;
    }
    private static int assembleColors(int[] Colordata){
        return (Colordata[0] << 24) | (Colordata[1] << 16) | (Colordata[2] << 8) | (Colordata[3]);
    }
    private static int[] multiplyColors(int[] baseData, int[] overlayData){
        int[] multiplydata = new int[4];

        multiplydata[0] = overlayData[0];
        multiplydata[1] = (baseData[1] * (overlayData[1])) / 255;
        multiplydata[2] = (baseData[2] * (overlayData[2])) / 255;
        multiplydata[3] = ((baseData[3]) * (overlayData[3])) / 255;

        return multiplydata;
    }
    private static int[] blendColors(int[] baseData, int[] overlayData, float alphaCoefficient){
        int[] blendedData = new int[4];
        float overlayAlpha = alphaCoefficient - (((float) overlayData[0]) / 255.0f);
        float baseAlpha = 1 - overlayAlpha;

        blendedData[0] = baseData[0];
        blendedData[1] = (int) ((baseData[1] * baseAlpha) + (overlayData[1] * overlayAlpha));
        blendedData[2] = (int) ((baseData[2] * baseAlpha) + (overlayData[2] * overlayAlpha));
        blendedData[3] = (int) ((baseData[3] * baseAlpha) + (overlayData[3] * overlayAlpha));

        return blendedData;
    }
}
