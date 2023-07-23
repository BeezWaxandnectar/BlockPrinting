package net.september.blockprinting.datagen;

import java.util.Iterator;
import java.util.Set;

public class Assembly {

    int Index;
    String substrate;
    String swatch;
    String style;

    protected Assembly(int Index, String substrate, String swatch, String style) {
        this.Index = Index;
        this.substrate = substrate;
        this.swatch = swatch;
        this.style = style;
    }

    private final Set<String> AllSubstrates = FileHandler.getAllSubstrates();
    private final Set<String> AllSwatches = Swatch.getAllSwatches();
    private final Set<String> AllStyles = FileHandler.getAllStyles();

    public Set<Assembly> AssembledCombinations;

    public void RunTheAssemblinator(){
        Iterator<String> IterateSubstrates = AllSubstrates.iterator();
        int CurrentIndex = 0;

        while (IterateSubstrates.hasNext()) {
            Iterator<String> IterateSwatches = AllSwatches.iterator();
            String CurrentSubstrate = IterateSubstrates.next();

            while (IterateSwatches.hasNext()) {
                Iterator<String> IterateStyles = AllStyles.iterator();
                String CurrentSwatch = IterateSwatches.next();

                while (IterateStyles.hasNext()) {
                    String CurrentStyle = IterateStyles.next();
                    AssembledCombinations.add(
                           new Assembly(CurrentIndex, CurrentSubstrate, CurrentSwatch, CurrentStyle));
                    CurrentIndex++;
                }
            }
        }
    }



}
