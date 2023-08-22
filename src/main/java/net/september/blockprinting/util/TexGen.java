
package net.september.blockprinting.util;

import com.mojang.blaze3d.platform.NativeImage;
import net.september.blockprinting.datagen.FileHandler;
import net.september.blockprinting.datagen.Swatch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class TexGen{

    public TexGen(){}
    final String LoserDir = System.getProperty("user.dir");
    final String UserDir = LoserDir.substring(0, (LoserDir.length()-4));
    final String destPath = "/src/main/resources/assets/blockprinting/textures/block/generated/";
    final String generatedFolder = (UserDir + destPath);

    //##############//
    // PNG PIPELINE //-----------------------------------------------------------------------------------------------
    //##############//

    public void GeneratePNGs() throws IOException {

        refreshGeneration("block/");
        refreshGeneration("assembly/");
        refreshGeneration("assemblypreview/");

        generateAllAssemblies();

        generateForAll("wallpaper");

    }

    private void generateAllAssemblies() throws IOException {

        for (String swatch : Swatch.getAllSwatches()){
            for (String style : FileHandler.getAllStyles()){
                generateAssemblyPreview(swatch, style);
            }}}


    private void generateAssemblyPreview(String swatchname, String stylename) throws IOException {
        NativeImage base = SpriteFinder.look("base", "base");
        NativeImage style = SpriteFinder.look("style", stylename);
        String Namespace = swatchname + "_" + stylename;

        Swatch swatch = Swatch.getSwatch(swatchname);

        int baseColor = swatch.ABaseColor;
        int styleColor = swatch.AStyleColor;

        NativeImage assembly = FromParents("binary", base, baseColor, style, styleColor, Namespace, 1.0f, "new", "assembly/");
        NativeImage stitchBase = SpriteFinder.look("base", "assembly_preview_base");

        NativeImage output = FromParents("binary", stitchBase, null, assembly, null, Namespace, 1.0f, "new", "assemblypreview/");

        assembly.writeToFile(Path.of(generatedFolder + "assembly/" + Namespace + ".png"));
        output.writeToFile(Path.of(generatedFolder + "assemblypreview/" + Namespace + ".png"));

    }

    private void generateForAll(String substrate) throws IOException{

        for( String swatch : Swatch.getAllSwatches()){
            for(String style : FileHandler.getAllStyles()){
                processImages(swatch, style, substrate);
            }
        }
    }

    private void processImages(String swatchname, String stylename, String substratename) throws IOException {
        try{
            NativeImage base = SpriteFinder.look("base", "base");
            NativeImage style = SpriteFinder.look("style", stylename);

            String Namespace = swatchname + "_" + stylename + "_" + substratename;

            Swatch swatch = Swatch.getSwatch(swatchname);

            int baseColor = swatch.BaseColor;
            int styleColor = swatch.StyleColor;
            int substrateColor = swatch.SubstrateColor;
            int substrateMask = swatch.SubstrateMask;

            NativeImage substrate = SpriteFinder.look("substrate", substratename);

            NativeImage baseBlend = FromParents("blend", base, baseColor, substrate, substrateColor,
                    "baseblend",  0.0f, "temp", null);
            NativeImage styleBlend = FromParents("blend", style, styleColor, substrate, substrateMask,
                    "styleblend",  0.0f, "temp", null);

            NativeImage assembly = FromParents("binary", baseBlend, null, styleBlend, null,
                    Namespace, 1.0f, "new", "block/");

            Path destination = Path.of(generatedFolder + "block/" + Namespace + ".png");

            assembly.writeToFile(destination);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private @NotNull NativeImage FromParents(String function,
                                                    NativeImage base, @Nullable Integer baseColor,
                                                    NativeImage overlay, @Nullable Integer overlayColor,
                                                    String Namespace, float alphaCoefficient,
                                                    String type, @Nullable String FolderExtension){
        if (baseColor != null){
            SpecifyColor(base, baseColor);
        }
        if (overlayColor != null){
            SpecifyColor(overlay, overlayColor);
        }

        NativeImage output = new NativeImage(16, 16, false);

        for (int x = 0; x < base.getWidth(); x++){
            for (int y = 0; y < base.getHeight(); y++){
                int[] baseData = separateColors((base.getPixelRGBA(x, y)));
                int overlayX = x;
                int overlayY = y;
                while(overlayX >= overlay.getWidth()){overlayX = overlayX - overlay.getWidth();}
                while(overlayY >= overlay.getHeight()){overlayY = overlayY - overlay.getHeight();}
                int[] overlayData = separateColors((overlay.getPixelRGBA(overlayX, overlayY)));


                if (overlayData[0] == 0){
                    output.setPixelRGBA(x, y, assembleColors(baseData));
                } else {
                    switch (function) {
                        case "binary" -> output.setPixelRGBA(x, y, assembleColors(overlayData));
                        case "blend" -> {
                            int[] blendedColorsDebug = blendColors(baseData, overlayData, alphaCoefficient);
                            int blendedColors = assembleColors(blendedColorsDebug);
                            output.setPixelRGBA(x, y, blendedColors);
                        }
                    }}}}
        return output;
    }

    private void refreshGeneration(String FolderExtension) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Path.of(generatedFolder + FolderExtension))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    Files.delete(path);
                }}}}

    //#############//
    // COLOR LOGIC // ----------------------------------------------------------------------------------------------
    //#############//

    private void SpecifyColor(NativeImage img, int color){
        for (int x = 0; x < img.getWidth(); x++){
            for (int y = 0; y < img.getHeight(); y++){
                    int[] pixelData = separateColors((img.getPixelRGBA(x, y)));
                    int[] colorData = separateColors(makeABGR(color));
                    colorData[0] = pixelData[0];
                    int finalcolor = assembleColors(colorData);

                    img.setPixelRGBA(x, y, (finalcolor));
                }}}

    private int makeABGR(int color){
        return (color & 0xFF00FF00) | (((color & 0x00FF0000) >> 16) & 0x000000FF) | (((color & 0x000000FF) << 16) & 0x00FF0000);
    }

    private int[] separateColors(int abgr){
        int[] ColorData = new int[4];

        // ALPHA BREAKPOINT -

        ColorData[0] = (abgr & 0xFF000000) >> 24; //ALPHA
        ColorData[1] = (abgr & 0x00FF0000) >> 16; //BLUE
        ColorData[2] = (abgr & 0x0000FF00) >> 8;  //GREEN
        ColorData[3] = (abgr & 0x000000FF);       //RED
        return ColorData;
    }
    private int assembleColors(int[] Colordata){
        return (Colordata[0] << 24) | (Colordata[1] << 16) | (Colordata[2] << 8) | (Colordata[3]);
    }

    private int[] blendColors(int[] baseData, int[] overlayData, float alphaCoefficient){
        int[] blendedData = new int[4];
        int overlayoutput;

        if (overlayData[0] < 0) {
            overlayoutput = overlayData[0] + 256;
        } else {
            overlayoutput = overlayData[0];
        }

        float overlayAlpha = 1.0f - (((float) overlayoutput) / 255.0f);
        float baseAlpha = 1.0f - overlayAlpha;

        blendedData[0] = baseData[0];
        blendedData[1] = (int) ((baseData[1] * baseAlpha) + (overlayData[1] * overlayAlpha));
        blendedData[2] = (int) ((baseData[2] * baseAlpha) + (overlayData[2] * overlayAlpha));
        blendedData[3] = (int) ((baseData[3] * baseAlpha) + (overlayData[3] * overlayAlpha));

        return blendedData;
    }
}
