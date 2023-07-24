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

    private static final ArrayList<Swatch> SwatchList = new ArrayList<>(Arrays.asList(
            new Swatch("tropical", 0xFFF99542, 0xFF17A0A0, 0x80b8de39),
            new Swatch("hydra", 0xFF136F63, 0xFF032B43, 0x80386653),
            new Swatch("goth", 0xFF262420, 0xFF130900, 0x80090000),
            new Swatch("pastel", 0xFFF4D3EE, 0xFFAFB8F7, 0x80b60dff),
            new Swatch("crimson", 0xFF5E1823, 0xFF331011, 0x80),
            new Swatch("sunset", 0xFFf7af9d, 0xFFC084b2, 0x80995660),
            new Swatch("roulette", 0xFF754043, 0xFF171614, 0x80693232),
            new Swatch("cedar", 0xFF976b4e, 0xFF5f3c2d, 0x80654436),
            new Swatch("royal", 0xFFd7b377, 0xFF385f71, 0x80A19978),
            new Swatch("yellow", 0xFFe7e5bc, 0xFFc4af73, 0x80a18b6c),
            new Swatch("antique", 0xFFa0a083, 0xFF4b7884, 0x8086947d),
            new Swatch("neon", 0xFFa0a083, 0xFF4b7884, 0x804ad13b),
            new Swatch("frosty", 0xFF94d5db, 0xFF4c678b, 0x802ecbff),
            new Swatch("aqua", 0xFFbcd8d7, 0xFF6696a0, 0x805c9ac4),
            new Swatch("violet", 0xFFbf678f, 0xFF542d61, 0x80875184),
            new Swatch("spring", 0xFFd4f4dd, 0xFF17bebb, 0x80b0fffa),
            new Swatch("deep_blue", 0xFF52aeff, 0xFF2A8FE9, 0x800028ff)

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






