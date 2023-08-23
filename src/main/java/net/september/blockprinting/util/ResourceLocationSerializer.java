package net.september.blockprinting.util;

import com.google.gson.*;
import lombok.RequiredArgsConstructor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.september.blockprinting.BlockPrinting;

import java.lang.reflect.Type;
import java.util.function.Function;

@RequiredArgsConstructor
public class ResourceLocationSerializer<T extends ResourceLocation> implements JsonDeserializer<T>, JsonSerializer<T>
{
   // private final Function<String,T> constructor;
    private final String modId = BlockPrinting.MOD_ID;

   public static ResourceLocationSerializer<ResourceLocation> resourceLocation(String modId){
      //  return new ResourceLocationSerializer<>(ResourceLocation::new, modId);
       return null;
    }

    @Override
    public JsonElement serialize(ResourceLocation loc, Type type, JsonSerializationContext context) {
        return new JsonPrimitive(loc.toString());
    }

    @Override
    public T deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        String loc = GsonHelper.convertToString(element, "location");

        if (!loc.contains(":")){
            loc = BlockPrinting.MOD_ID + ":" + loc;
        }

        return null;
       // return constructor.apply(loc);
    }
}
