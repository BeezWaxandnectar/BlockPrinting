
package net.september.blockprinting.util;

import com.mojang.blaze3d.platform.NativeImage;
import net.september.blockprinting.datagen.Swatch;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class TextureFactory {

    public TextureFactory(){}
    final String generatedPath = (FileTracker.UserDir + FileTracker.texturesPath + "generated/");

    //##############//
    // PNG PIPELINE //-----------------------------------------------------------------------------------------------
    //##############//

    public void GenerateDefaultPNGs() throws IOException {

        refreshGeneration("block/");
        refreshGeneration("assembly/");
        refreshGeneration("assemblypreview/");

        generateAllAssemblies();

        generateEachSubstrate("royal", "panelopera");

    }

    private void generateAllAssemblies() throws IOException {

        for (String swatch : Swatch.getAllSwatches()){
            for (String style : FileMaps.getAllStyles()){
                generateAssemblyPreview(swatch, style);
            }}}

    private void generateForAll(String substrate) throws IOException{

        for( String swatch : Swatch.getAllSwatches()){
            for(String style : FileMaps.getAllStyles()){
                makeTexture(swatch, style, substrate);
            }}}

    private void generateEachSubstrate(String swatch, String style) throws IOException {
        for (String substrate : FileMaps.getAllSubstrates()){
            makeTexture(swatch, style, substrate);
        }}

    private void refreshGeneration(String FolderExtension) throws IOException {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Path.of(generatedPath + FolderExtension))) {
            for (Path path : stream) {
                if (!Files.isDirectory(path)) {
                    Files.delete(path);
                }}}}

    //####################//
    // NATIVE IMAGE LOGIC //--------------------------------------------------------------------------------------------
    //####################//

    private void makeTexture(String swatchname, String stylename, String substratename) throws IOException {
        try{
            NativeImage base = SpriteFinder.lookup("base", "base");
            NativeImage style = SpriteFinder.lookup("style", stylename);

            String Namespace = swatchname + "_" + stylename + "_" + substratename;

            Swatch swatch = Swatch.getSwatch(swatchname);

            int baseColor = swatch.BaseColor;
            int styleColor = swatch.StyleColor;
            int substrateColor = swatch.SubstrateColor;
            int substrateMask = swatch.SubstrateMask;

            NativeImage substrate = SpriteFinder.lookup("substrate", substratename);

            NativeImage baseBlend = FromParents("blend", base, baseColor, substrate, substrateColor, 1.0f);
            NativeImage styleBlend = FromParents("blend", style, styleColor, substrate, substrateMask, 1.0f);

            NativeImage assembly = FromParents("binary", baseBlend, null, styleBlend, null, 1.0f);

            Path destination = Path.of(generatedPath + "block/" + Namespace + ".png");

            assembly.writeToFile(destination);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void generateAssemblyPreview(String swatchname, String stylename) throws IOException {
        NativeImage base = SpriteFinder.lookup("base", "base");
        NativeImage style = SpriteFinder.lookup("style", stylename);
        String Namespace = swatchname + "_" + stylename;

        Swatch swatch = Swatch.getSwatch(swatchname);

        int baseColor = swatch.ABaseColor;
        int styleColor = swatch.AStyleColor;

        NativeImage assembly = FromParents("binary", base, baseColor, style, styleColor, 1.0f);
        NativeImage stitchBase = SpriteFinder.lookup("base", "assembly_preview_base");

        NativeImage output = FromParents("binary", stitchBase, null, assembly, null, 1.0f);

        assembly.writeToFile(Path.of(generatedPath + "assembly/" + Namespace + ".png"));
        output.writeToFile(Path.of(generatedPath + "assemblypreview/" + Namespace + ".png"));

    }

    private @NotNull NativeImage FromParents(String function,
                                                    NativeImage base, @Nullable Integer baseColor,
                                                    NativeImage overlay, @Nullable Integer overlayColor, float alphaCoefficient){
        if (baseColor != null){
            SpecifyColor(base, baseColor);
        }
        if (overlayColor != null){
            SpecifyColor(overlay, overlayColor);
        }

        NativeImage output = new NativeImage(base.getWidth(), base.getHeight(), false);

        for (int baseX = 0; baseX < base.getWidth(); baseX++){
            for (int baseY = 0; baseY < base.getHeight(); baseY++){
                int[] baseData = separateColors((base.getPixelRGBA(baseX, baseY)));
                int overlayX = baseX;
                int overlayY = baseY;
                while(overlayX >= overlay.getWidth()){
                    overlayX = overlayX - overlay.getWidth();
                }
                while(overlayY >= overlay.getHeight()){
                    overlayY = overlayY - overlay.getHeight();
                }
                int[] overlayData = separateColors((overlay.getPixelRGBA(overlayX, overlayY)));


                if (overlayData[0] == 0){
                    output.setPixelRGBA(baseX, baseY, assembleColors(baseData));
                } else {
                    switch (function) {
                        case "binary" -> output.setPixelRGBA(baseX, baseY, assembleColors(overlayData));
                        case "blend" -> {
                            int[] blendedColorsDebug = blendColors(baseData, overlayData, alphaCoefficient);
                            int blendedColors = assembleColors(blendedColorsDebug);
                            output.setPixelRGBA(baseX, baseY, blendedColors);
                        }
                    }}}}
        return output;
    }

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

        float overlayAlpha = alphaCoefficient - (((float) overlayoutput) / 255.0f);
        float baseAlpha = 1.0f - overlayAlpha;

        blendedData[0] = baseData[0];
        blendedData[1] = (int) ((baseData[1] * baseAlpha) + (overlayData[1] * overlayAlpha));
        blendedData[2] = (int) ((baseData[2] * baseAlpha) + (overlayData[2] * overlayAlpha));
        blendedData[3] = (int) ((baseData[3] * baseAlpha) + (overlayData[3] * overlayAlpha));

        return blendedData;
    }
}
