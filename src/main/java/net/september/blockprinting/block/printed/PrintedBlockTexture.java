package net.september.blockprinting.block.printed;

import net.minecraft.resources.ResourceLocation;
import net.september.blockprinting.util.FileMaps;
import net.september.blockprinting.datagen.Swatch;

public class PrintedBlockTexture {

    String swatch;
    String style;
    String substrate;
    String namespace;

    ResourceLocation location;

    public PrintedBlockTexture(String swatch, String style, String substrate){
        this.namespace = swatch + "_" + style + "_" + substrate;

        if (Swatch.isValidSwatch(swatch) && FileMaps.isValidStyle(style) && FileMaps.isValidSubstrate(substrate)) {
            this.swatch = swatch;
            this.style = style;
            this.substrate = substrate;
        } else {
            if (!Swatch.isValidSwatch(swatch)){
                System.out.println("Your swatch " + swatch + " on the texture " + this.namespace + " is not listed.");
            }
            if (!FileMaps.isValidStyle(style)){
                System.out.println("Your style " + style + " on the texture " + this.namespace + " is not listed.");
            }
            if (!FileMaps.isValidSubstrate(substrate)){
                System.out.println("Your substrate '" + substrate + "' on the texture " + this.namespace + " is not listed.");
            }
        }
    }

    public PrintedBlockTexture(String namespace){
        this.namespace = namespace;
    }

    public String getSwatch() {return this.swatch;}
    public String getStyle() {return this.style;}
    public String getSubstrate() {return this.substrate;}
    public ResourceLocation getLocation() {return this.location;}


}
