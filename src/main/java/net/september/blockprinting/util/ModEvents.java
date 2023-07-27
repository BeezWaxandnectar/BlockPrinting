package net.september.blockprinting.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.september.blockprinting.BlockPrinting;
import net.september.blockprinting.dyesystem.PlayerDyeProvider;

@Mod.EventBusSubscriber(modid = BlockPrinting.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void SetupDyeSystem(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() instanceof Player) {
            if (!event.getObject().getCapability(PlayerDyeProvider.PlayerDyeHeld).isPresent()) {
                event.addCapability(new ResourceLocation(BlockPrinting.MOD_ID, "properties"), new PlayerDyeProvider());
            }}}

    @SubscribeEvent
    public static void OnPlayerCloned(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(PlayerDyeProvider.PlayerDyeHeld).ifPresent(oldStore -> {
                event.getOriginal().getCapability(PlayerDyeProvider.PlayerDyeHeld).ifPresent(newStore -> {
                    newStore.copyFrom((PlayerDyeProvider) oldStore);
                });});}}

    @SubscribeEvent
    public static void onRegisterCapabilitiesEvent(RegisterCapabilitiesEvent event) {
        event.register(PlayerDyeProvider.class);


    }}