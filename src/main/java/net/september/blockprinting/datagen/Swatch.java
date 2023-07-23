package net.september.blockprinting.datagen;

import java.util.*;

public class Swatch {
    String Name;
    int BaseColor;
    int StyleColor;
    int SubstrateColor;

    protected Swatch(String Name, int BaseColor, int StyleColor, int SubstrateColor){
        this.Name = Name;
        this.BaseColor = BaseColor;
        this.StyleColor = StyleColor;
        this.SubstrateColor = SubstrateColor;
    }

    private static String getSwatchName(Swatch swatch){
        return swatch.Name;
    }

    private static ArrayList<Swatch> SwatchList = new ArrayList<>(Arrays.asList(
            new Swatch("tropical", 0xFFF99542, 0xFF17A0A0, 0xFF001212),
            new Swatch("hydra", 0xFF136F63, 0xFF032B43, 0xFF000012)
    ));

    public static HashMap<String, Swatch> SwatchMap;

    public static HashMap<String, Swatch> NewMap(){
        HashMap<String, Swatch> NewMap = new HashMap<>();


        for(Swatch swatch : SwatchList){
            NewMap.put(getSwatchName(swatch), swatch);
        }
        return NewMap;
    }

    public static void CreateSwatchMap() {
        SwatchMap = NewMap();
    }

    public static Swatch getSwatch(String key) {return SwatchMap.get(key);}

    public static Set<String> getAllSwatches() {return SwatchMap.keySet();}




}






