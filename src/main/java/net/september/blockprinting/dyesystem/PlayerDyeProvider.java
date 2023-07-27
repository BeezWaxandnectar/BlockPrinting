package net.september.blockprinting.dyesystem;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerDyeProvider extends DyeSystem implements ICapabilityProvider, INBTSerializable<CompoundTag> {

    public static Capability<DyeSystem> PlayerDyeHeld = CapabilityManager.get(new CapabilityToken<DyeSystem>() {});

    public DyeSystem DyeHeld = null;
    private final LazyOptional<DyeSystem> optional = LazyOptional.of(this::createDyeSystem);

    private DyeSystem createDyeSystem() {
        if (this.DyeHeld == null) {
            this.DyeHeld = new DyeSystem();
        }
        return this.DyeHeld;
    }




    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {

        if (cap == PlayerDyeHeld) {
            return optional.cast();
        }
        return LazyOptional.empty();
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap) {
        return ICapabilityProvider.super.getCapability(cap);
    }

    @Override
    public CompoundTag serializeNBT() {

        CompoundTag nbt = new CompoundTag();
        createDyeSystem().saveNBTData(nbt);

        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        createDyeSystem().loadNBTData(nbt);



    }
}
