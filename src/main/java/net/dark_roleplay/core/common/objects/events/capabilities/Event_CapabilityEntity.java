package net.dark_roleplay.core.common.objects.events.capabilities;

import net.dark_roleplay.core.common.References;
import net.dark_roleplay.core.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.library.capabilities.CapabilityProvider;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = References.MODID)
public class Event_CapabilityEntity {

	@SubscribeEvent
    public static void attachCapability(AttachCapabilitiesEvent<Entity> event){
        if (!(event.getObject() instanceof EntityPlayer)) return;

        event.addCapability(new ResourceLocation(References.MODID, "skill_handler"), new CapabilityProvider(DRPCoreCapabilities.SKILL_HANDLER));
    }
	
	@SubscribeEvent
    public static void attachCapabilityChunk(AttachCapabilitiesEvent<Chunk> event){
        if (!(event.getObject() instanceof Chunk)) return;
        event.addCapability(new ResourceLocation(References.MODID, "crop_handler"), new CapabilityProvider(DRPCoreCapabilities.CROP_HANDLER));
        event.addCapability(new ResourceLocation(References.MODID, "lock_handler"), new CapabilityProvider(DRPCoreCapabilities.LOCK_HANDLER));
	}
}
