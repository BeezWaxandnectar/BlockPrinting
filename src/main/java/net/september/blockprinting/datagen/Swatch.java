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
            new Swatch("tropical", 0xFFF99542, 0xFF17A0A0, 0xffca8b5c),
            new Swatch("hydra", 0xFF136F63, 0xFF032B43, 0xff386653),
            new Swatch("pitch", 0xFF262420, 0xFF130900, 0xff090000),
            new Swatch("pastel", 0xFFF4D3EE, 0xFFAFB8F7, 0xffe7b0ff),
            new Swatch("crimson", 0xFF81171b, 0xFF540804, 0xff402839),
            new Swatch("grumpy", 0xFFffbe32, 0xFFad2d06, 0xffffc966),
            new Swatch("sunset", 0xFFf7af9d, 0xFFC084b2, 0xffc4828c),
            new Swatch("roulette", 0xFF754043, 0xFF171614, 0xff1e1d1d),
            new Swatch("cedar", 0xFF976b4e, 0xFF5f3c2d, 0xff654436),
            new Swatch("royal", 0xFFd7b377, 0xFF385f71, 0xffA19d8d),
            new Swatch("chocolate", 0xFF653017, 0xFF442110, 0xff0a0400),
            new Swatch("silver", 0xFFd9d9d9, 0xFFbdbdbd, 0xffdcdcdc),
            new Swatch("blazing", 0xFFffc978, 0xFFaa0202, 0xffd9857b),
            new Swatch("antique", 0xFFa0a083, 0xFF4b7884, 0xff8c945f),
            new Swatch("arboreal", 0xFF708060, 0xFF42291f, 0xff42451c),
            new Swatch("mauve", 0xFF62466b, 0xFF45364b, 0xff803799),
            new Swatch("neon", 0xFFccff66, 0xFF74c5a2, 0xff5dff6d),
            new Swatch("frosty", 0xFF94d5db, 0xFF4c678b, 0xff99bcde),
            new Swatch("aqua", 0xFFbcd8d7, 0xFF6696a0, 0xff5c9ac4),
            new Swatch("daisy_gray", 0xFF808080, 0xFF404040, 0xff898989),
            new Swatch("hot_pink", 0xFFf0cee8, 0xFFcd7898, 0xffffa6e0),
            new Swatch("gilded", 0xFFebd289, 0xFFae9c60, 0xfff2c268),
            new Swatch("hollow", 0xFFcac5d7, 0xFF5a5171, 0xff8c95ab),
            new Swatch("citrus", 0xFFffec9d, 0xFFaae965, 0xffffff5f),
            new Swatch("masquerade_ball", 0xFF522344, 0xFF330036, 0xff4a1357),
            new Swatch("timberwolf", 0xFFafa89d, 0xFF897969, 0xffd4bb9f),
            new Swatch("burgundy", 0xFFb76743, 0xFF8b3f2b, 0xff99503e),
            new Swatch("rose", 0xFFce5f70, 0xFFb03d4d, 0xffca3e4d),
            new Swatch("violet", 0xFFbf678f, 0xFF542d61, 0xff875184),
            new Swatch("spring", 0xFFd4f4dd, 0xFF17bebb, 0xffb0ffd6),
            new Swatch("winter", 0xFFffffff, 0xFFd8ebea, 0xffffffff),
            new Swatch("quartz", 0xFFe6e2db, 0xFFc3b6a8, 0xfffff8cb),
            new Swatch("fearne", 0xFF949e84, 0xFF4f6356, 0xff999965),
            new Swatch("goth", 0xFF534b52, 0xFF2d232e, 0xff402947),
            new Swatch("aether", 0xFFebfeff, 0xFF71cdff, 0xffd1dbc5),
            new Swatch("fine", 0xFFf7fffc, 0xFFaccdbf, 0xffebffe0),
            new Swatch("navy", 0xFF011232, 0xFF010713, 0xff0a0c1c),
            new Swatch("indigo", 0xFF453f78, 0xFF2e3156, 0xff19195a),
            new Swatch("cerulean", 0xFF52aeff, 0xFF374e74, 0xff01bad)

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






