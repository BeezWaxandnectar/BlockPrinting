package net.september.blockprinting.datagen;

import java.util.ArrayList;
import java.util.Set;

public class Assembly {

    public int index;
    public String swatch;
    public String style;

    protected Assembly(int index, String swatch, String style) {
        this.index = index;
        this.swatch = swatch;
        this.style = style;
    }


    private final Set<String> AllSwatches = Swatch.getAllSwatches();
    private final Set<String> AllStyles = FileHandler.getAllStyles();

    public static Assembly[] AssembledCombinations;

    public void RunTheAssemblinator(){
        int CurrentIndex = 0;
            for (String CurrentSwatch : AllSwatches) {
                for (String CurrentStyle : AllStyles) {
                    AssembledCombinations[CurrentIndex] = new Assembly(CurrentIndex, CurrentSwatch, CurrentStyle));
                    CurrentIndex++;
                }
            }
        }
    }




