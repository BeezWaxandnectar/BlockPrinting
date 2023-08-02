package net.september.blockprinting.util;

//CREDIT FOR THIS CLASS GOES TO VAZKII

import com.google.gson.JsonArray;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import javax.json.Json;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class OverrideHolder {
    final List<ModelOverride> modelOverrides = new ArrayList<>();

    @SafeVarargs
    public final OverrideHolder add(ResourceLocation model, Pair<ResourceLocation, Integer>... preds){
        Map<ResourceLocation, Integer> predMap = new LinkedHashMap<>();
        for (Pair<ResourceLocation, Integer> pred : preds) {
            predMap.put(pred.getFirst(), pred.getSecond());
        }
        modelOverrides.add(new ModelOverride(predMap, model));
        return this;
    }

    @Nullable
    public JsonArray toJson() {
        if (modelOverrides.isEmpty()) {return null;}
        else {
            JsonArray ret = new JsonArray();
            modelOverrides.stream().map(ModelOverride::toJson).forEach(ret::add);
            return ret;
        }
    }

}
