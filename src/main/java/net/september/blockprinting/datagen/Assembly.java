package net.september.blockprinting.datagen;

import java.util.ArrayList;
import java.util.Set;

public class Assembly {
    public String swatch;
    public String style;

    protected Assembly(String swatch, String style) {
        this.swatch = swatch;
        this.style = style;
    }

    private static final Set<String> AllSwatches = Swatch.getAllSwatches();
    private static final Set<String> AllStyles = FileHandler.getAllStyles();

    public static Assembly[] AssembledCombinations = new Assembly[AllStyles.size()*AllSwatches.size()];

    public static void RunTheAssemblinator(){
        int CurrentIndex = 0;
            for (String CurrentSwatch : AllSwatches) {
                for (String CurrentStyle : AllStyles) {
                    AssembledCombinations[CurrentIndex] = new Assembly(CurrentSwatch, CurrentStyle);
                    CurrentIndex++;
                }
            }
        }
    }




