package net.september.blockprinting.util;

import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

//CREDIT FOR THIS CLASS GOES TO VAZKII

public class ModelOverride {
    private final Map<ResourceLocation, Integer> predicates;
    private final ResourceLocation model;

    public ModelOverride(Map<ResourceLocation, Integer> predicates, ResourceLocation model){
        this.predicates = predicates;
        this.model = model;
    }

    public JsonObject toJson() {
        JsonObject ret = new JsonObject();
        JsonObject preds = new JsonObject();
        predicates.forEach((p, v) -> preds.addProperty(p.toString(), v));
        ret.add("predicate", preds);
        ret.addProperty("model", model.toString());
        return ret;
    }
}
