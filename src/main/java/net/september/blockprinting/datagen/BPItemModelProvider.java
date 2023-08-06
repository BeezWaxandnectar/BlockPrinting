package net.september.blockprinting.datagen;

import com.google.gson.JsonElement;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplate;
import net.minecraft.data.models.model.TextureMapping;
import net.minecraft.data.models.model.TextureSlot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.september.blockprinting.BlockPrinting;
import net.september.blockprinting.block.BPBlocks;
import net.september.blockprinting.item.BPItems;
import net.september.blockprinting.util.ModelWithOverrides;
import net.september.blockprinting.util.OverrideHolder;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BPItemModelProvider extends ItemModelProvider implements DataProvider{

    private final PackOutput packOutput;

    public ModelWithOverrides registerInkwellModels = new ModelWithOverrides(new ResourceLocation("item/generated"), TextureSlot.LAYER0);
    public ModelTemplate basicModel = new ModelTemplate(Optional.of(new ResourceLocation("item/generated")), Optional.empty(), TextureSlot.LAYER0);


    public BPItemModelProvider(PackOutput packOutput, ExistingFileHelper XFileHelper){
        super(packOutput, BlockPrinting.MOD_ID, XFileHelper);
        this.packOutput = packOutput;
    }


    @Override
    protected void registerModels() {

        //To use default Forge Provider//
        basicItem(BPItems.DEV_REMOTE.get());
        basicItem(BPItems.STAMP.get());
        basicItem(BPItems.THUMBSUP.get());



    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        Set<Item> items = BuiltInRegistries.ITEM.stream().filter(i ->
                BlockPrinting.MOD_ID.equals(BuiltInRegistries.ITEM.getKey(i).getNamespace())).collect(Collectors.toSet());
        Map<ResourceLocation, Supplier<JsonElement>> map = new HashMap<>();
        registerModels(items, map::put);
        registerModels();

        PackOutput.PathProvider modelPathProvider = packOutput.createPathProvider(PackOutput.Target.RESOURCE_PACK, "models");
        List<CompletableFuture<?>> output = new ArrayList<>();

        for (Map.Entry<ResourceLocation, Supplier<JsonElement>> e : map.entrySet()){
            ResourceLocation id = e.getKey();
            output.add(DataProvider.saveStable(cache, e.getValue().get(), modelPathProvider.json(id)));
        }
        return CompletableFuture.allOf(output.toArray(CompletableFuture[]::new));
    }




   protected void registerModels(Set<Item> items, BiConsumer<ResourceLocation, Supplier<JsonElement>> consumer) {

        TextureMapping InkwellTextures = TextureMapping
                .layer0(BPItems.MAGIC_INKWELL.get());

       ResourceLocation emptyInkwell = ModelLocationUtils.getModelLocation(BPItems.MAGIC_INKWELL.get(), "_empty");
        TextureMapping EmptyInkwellTx = TextureMapping
                .layer0(emptyInkwell);
        basicModel.create(emptyInkwell, EmptyInkwellTx, consumer);

        registerInkwellModels.create(ModelLocationUtils.getModelLocation(BPItems.MAGIC_INKWELL.get()), InkwellTextures,
                new OverrideHolder().add(emptyInkwell,
                        Pair.of(IPrefix.prefix("magic_inkwell_empty"), 1),
                        Pair.of(IPrefix.prefix("magic_inkwell0"), 0),
                        Pair.of(IPrefix.prefix("magic_inkwell1"), 1),
                        Pair.of(IPrefix.prefix("magic_inkwell2"), 2),
                        Pair.of(IPrefix.prefix("magic_inkwell3"), 3),
                        Pair.of(IPrefix.prefix("magic_inkwell4"), 4),
                        Pair.of(IPrefix.prefix("magic_inkwell5"), 5),
                        Pair.of(IPrefix.prefix("magic_inkwell6"), 6),
                        Pair.of(IPrefix.prefix("magic_inkwell7"), 7),
                        Pair.of(IPrefix.prefix("magic_inkwell8"), 8),
                        Pair.of(IPrefix.prefix("magic_inkwell9"), 9),
                        Pair.of(IPrefix.prefix("magic_inkwell10"), 10),
                        Pair.of(IPrefix.prefix("magic_inkwell11"), 11),
                        Pair.of(IPrefix.prefix("magic_inkwell12"), 12),
                        Pair.of(IPrefix.prefix("magic_inkwell13"), 13),
                        Pair.of(IPrefix.prefix("magic_inkwell14"), 14),
                        Pair.of(IPrefix.prefix("magic_inkwell15"), 15)),
                consumer);

        items.remove(BPItems.MAGIC_INKWELL.get());

}






    @Override
    public @NotNull String getName() {
        return "BlockPrinting Item Models";
    }
}
