package net.september.blockprinting.block.printed;

public class MasterPrintedBlockTexture {

    String swatch;
    String style;
    String substrate;
    String namespace;

    public MasterPrintedBlockTexture(String swatch, String style, String substrate){
        this.swatch = swatch;
        this.style = style;
        this.substrate = substrate;

        this.namespace = swatch + "_" + style + "_" + substrate;
    }

    public MasterPrintedBlockTexture(String namespace){
        this.namespace = namespace;


    }



}
