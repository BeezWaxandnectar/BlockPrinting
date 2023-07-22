package net.september.blockprinting.datagen;

import java.util.ArrayList;
import java.util.Arrays;

public class Swatch {
    static int Index;
    static String Name;
    static int BaseColor;
    static int StyleColor;
    static int SubstrateColor;

    public Swatch(int Index, String Name, int BaseColor, int StyleColor, int SubstrateColor){
        Swatch.Index = Index;
        Swatch.Name = Name;
        Swatch.BaseColor = BaseColor;
        Swatch.StyleColor = StyleColor;
        Swatch.SubstrateColor = SubstrateColor;
    }



    public static Swatch getSwatch(int Index){
        ArrayList<Swatch> SwatchList = new ArrayList<>(Arrays.asList(
                new Swatch(0,"Tropicana", 0xFFF99542, 0xFF17A0A0, 0xFF001212),
                new Swatch(1,"Ender", 0xFF136F63, 0xFF032B43, 0xFF000012)
        ));
        return SwatchList.get(Index);
    }


}



