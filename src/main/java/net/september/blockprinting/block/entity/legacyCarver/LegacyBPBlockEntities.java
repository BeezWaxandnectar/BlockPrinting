/*package net.september.blockprinting.block.entity.legacyCarver;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.september.blockprinting.BlockPrinting;
import net.september.blockprinting.block.BPBlocks;

public class LegacyBPBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, BlockPrinting.MOD_ID);

    public static final RegistryObject<BlockEntityType<LegacyStampCarverBlockEntity>> STAMP_CARVER =
            BLOCK_ENTITIES.register("stamp_carver", () ->
                    BlockEntityType.Builder.of(LegacyStampCarverBlockEntity::new,
                            BPBlocks.STAMP_CARVER.get()).build(null));


    public static void register(IEventBus eventBus) {
        System.out.println("Registering Block Entities...");
        BLOCK_ENTITIES.register(eventBus);
    }

}*/
