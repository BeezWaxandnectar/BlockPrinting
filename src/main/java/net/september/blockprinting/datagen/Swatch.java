package net.september.blockprinting.datagen;

import java.util.*;

public class Swatch {
    String Name;
    public int BaseColor;
    public int StyleColor;
    public int SubstrateColor;
    public int SubstrateMask;
    public int ABaseColor;
    public int AStyleColor;

    protected Swatch(String Name, int BaseColor, int StyleColor, int SubstrateColor, int SubstrateMask, int ABaseColor, int AStyleColor){
        this.Name = Name;
        this.BaseColor = BaseColor;
        this.StyleColor = StyleColor;
        this.SubstrateColor = SubstrateColor;
        this.SubstrateMask = SubstrateMask;
        this.ABaseColor = ABaseColor;
        this.AStyleColor = AStyleColor;

    }

    private static String getSwatchName(Swatch swatch){
        return swatch.Name;
    }

    private static final ArrayList<Swatch> SwatchList = new ArrayList<>(Arrays.asList(


            //new Swatch("aether", 0xFFffffff, 0xFFbbe7ff, 0xffd4f4ff, 0xFF00a7ff, 0xFFebfeff, 0xFF71cdff),
            //new Swatch("alabaster", 0xFFffffff, 0xFFbbe7ff, 0xffd4f4ff, 0xFF00a7ff, 0xFFf5f5f5, 0xFFf0e8ce),
            //new Swatch("antique", 0xFFc1c19e, 0xFF308ba3, 0xff807659, 0xFF3d6666, 0xFFa0a083, 0xFF4b7884),
            //new Swatch("aqua", 0xFFceeceb, 0xFF83c1ce, 0xff89abab, 0xFF366b66, 0xFFbcd8d7, 0xFF6696a0),
            //new Swatch("arboreal", 0xFFa3bb8c, 0xFF593729, 0xff3b4031, 0xFF381d10, 0xFF708060, 0xFF42291f),
            //new Swatch("blazing", 0xFFffce85, 0xFFd00202, 0xffd69a51, 0xFF780000, 0xFFffc978, 0xFFaa0202),
            //new Swatch("bluesy", 0xFF788ea4, 0xFF5c718c, 0xff3e555e, 0xFF001b27, 0xFF536271, 0xFF3e4c5e),
            //new Swatch("burgundy", 0xFFaa4449, 0xFF672427, 0xff573434, 0xFF3b0000, 0xFF6f3033, 0xFF4d1b1d),
            //new Swatch("cedar", 0xFFc58c66, 0xFF9f644b, 0xff593a2c, 0xFF210e0e, 0xFF976b4e, 0xFF5f3c2d),
            //new Swatch("cerulean", 0xFF9ed2ff, 0xFF4166a1, 0xff0086cf, 0xFF283366, 0xFF52aeff, 0xFF374e74),
            //new Swatch("chocolate", 0xFFa05734, 0xFF633017, 0xff2e1107, 0xFF210b09, 0xFF653017, 0xFF442110),
            //new Swatch("citrus", 0xFFfff6d0, 0xFFc4ff83, 0xfff0ea78, 0xFF76d700, 0xFFec9d, 0xFFaae965),
            //new Swatch("crimson", 0xFFa71e23, 0xFF750b05, 0xff520000, 0xFF210000, 0xFF81171b, 0xFF540804),
            //new Swatch("daisy0gray", 0xFFb9b9b9, 0xFF666666, 0xff5e5e5e, 0xFF261b1b, 0xFF818181, 0xFF404040),
            //new Swatch("fearne", 0xFFe5ffa8, 0xFFb2c75c, 0xffabab5e, 0xFF586000, 0xFF949e84, 0xFF4f6356),
            //new Swatch("fine", 0xFFffffff, 0xFFc8eede, 0xffdaf8ec, 0xFF92a699, 0xFFf7fffc, 0xFFaccdbf),
            //new Swatch("frosty", 0xFFc6faff, 0xFF6c93c6, 0xff56b2b2, 0xFF330a33, 0xFF94d5db, 0xFF4c678b),
            //new Swatch("gilded", 0xFFffeaae, 0xFFdcc579, 0xffbf984e, 0xFF6b6141, 0xFFebd289, 0xFFae9c60),
            //new Swatch("goth", 0xFF6b6069, 0xFF392d3b, 0xff332a2b, 0xFF000000, 0xFF534b52, 0xFF2d232e),
            //new Swatch("grumpy", 0xFFffd271, 0xFFe08d40, 0xffb88100, 0xFF612c00, 0xFFffbe32, 0xFFa65203),
            //new Swatch("hollow", 0xFFf5f1ff, 0xFFe08d40, 0xffaca2b0, 0xFF3e3652, 0xFFcac5d7, 0xFF5a5171),
            //new Swatch("hot0pink", 0xFFffe8fa, 0xFFff95bd, 0xffea9fba, 0xFFab567c, 0xFFf0cee8, 0xFFcd7898),
            //new Swatch("hydra", 0xFF1da795, 0xFF06517e, 0xff215457, 0xFF040f1a, 0xFF136f63, 0xFF032b43),
            //new Swatch("indigo", 0xFF655cb0, 0xFF45497c, 0xff272845, 0xFF0f1140, 0xFF453f78, 0xFF2e3156),
            //new Swatch("masquerade0ball", 0xFF7e3668, 0xFF520057, 0xff2e1521, 0xFF140a0f, 0xFF522344, 0xFF330036),
            //new Swatch("mauve", 0xFF8a6296, 0xFF623f71, 0xff4b3159, 0xFF322a33, 0xFF62466b, 0xFF45364b),
            //new Swatch("navy", 0xFF082253, 0xFF041940, 0xff000d33, 0xFF000000, 0xFF011646, 0xFF020f27),
            //new Swatch("neon", 0xFFddff99, 0xFF88e6bd, 0xff8cdb00, 0xFF117342, 0xFFccff66, 0xFF74c5a2),
            //new Swatch("olive", 0xFFf4ffdb, 0xFFb2c75c, 0xff808c3f, 0xFF586000, 0xFFb3c783, 0xFF72803b),
            //new Swatch("pastel", 0xFFffebfa, 0xFFb5beff, 0xfff0b9ed, 0xFF7ca5d6, 0xFFf4d3ee, 0xFFafb8f7),
            //new Swatch("pitch", 0xFF3a3831, 0xFF0d0d0d, 0xff141111, 0xFF000000, 0xFF262420, 0xFF000000),
            //new Swatch("quartz", 0xFFffffff, 0xFFe2d2c2, 0xffcfc4b5, 0xFFb2a894, 0xFFe1ddd6, 0xFFc3b6a8),
            //new Swatch("rose", 0xFFe07a89, 0xFFbd3c4e, 0xffb84058, 0xFF92133f, 0xFFce5f70, 0xFFb03d4d),
            //new Swatch("roulette", 0xFF946466, 0xFF312e2b, 0xff6b2b2e, 0xFF000000, 0xFF754043, 0xFF171614),
            new Swatch("royal", 0xFFffe7c0, 0xFF4b7f97, 0xffa37631, 0xFF1b3e52, 0xFFd7b377, 0xFF385f71),
            //new Swatch("silver", 0xFFf2f2f2, 0xFFe2e2e2, 0xffc9c9c9, 0xFF999999, 0xFFd9d9d9, 0xFFbcbcbc),
            new Swatch("spring", 0xFFfdfffd, 0xFF1bddd9, 0xffa0deb6, 0xFF227685, 0xFFd4f4dd, 0xFF17bebb),
            //new Swatch("sunset", 0xFFffc5b6, 0xFFdfabd2, 0xffd78576, 0xFF965993, 0xFFf7af9d, 0xFFc084b2),
            //new Swatch("timberwolf", 0xFFe4dccd, 0xFFc1b2a4, 0xff857a6a, 0xFF61563d, 0xFFafa89d, 0xFF897969),
            //new Swatch("tropical", 0xFFffc990, 0xFF0dcece, 0xfffc7035, 0xFF177372, 0xFFf99542, 0xFF17a0a0),
            //new Swatch("violet", 0xFFcfc6ff, 0xFF7871D6, 0xff7267ce, 0xFF20153b, 0xFFa594f9, 0xFF4f4789),
            new Swatch("winter", 0xFFffffff, 0xFFffffff, 0xffe9f0ed, 0xFFceddde, 0xFFf4fafb, 0xFFd8ebea)

           //new Swatch("control", 0xFF808080, 0xFF808080, 0xffff00ff, 0xFF00FF00, 0xFFFFFFFF, 0xFF000000)

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






