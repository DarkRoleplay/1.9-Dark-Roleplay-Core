package net.dark_roleplay.drpcore.common.objects.events.capabilities;

import net.dark_roleplay.drpcore.common.DRPCoreReferences;
import net.dark_roleplay.drpcore.common.capabilities.CapabilityProvider;
import net.dark_roleplay.drpcore.common.capabilities.player.crafting.*;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;

public class Event_CapabilityEntity {

//	@SubscribeEvent
//    public void attachCapability(AttachCapabilitiesEvent<Entity> event){
//        if (!(event.getObject() instanceof EntityPlayer)) return;
//
//        event.addCapability(new ResourceLocation(DRPCoreReferences.MODID, "recipe_controller"), new CapabilityProvider(DRPCoreCapabilities.DRPCORE_RECIPE_CONTROLLER));
//        event.addCapability(new ResourceLocation(DRPCoreReferences.MODID, "skill_controller"), new CapabilityProvider(DRPCoreCapabilities.DRPCORE_SKILL_CONTROLLER));
//    }
	
	@SubscribeEvent
    public void attachCapabilityChunk(AttachCapabilitiesEvent<Chunk> event){
        if (!(event.getObject() instanceof Chunk)) return;
        event.addCapability(new ResourceLocation(DRPCoreReferences.MODID, "crop_handler"), new CapabilityProvider(DRPCoreCapabilities.CROP_HANDLER));
        event.addCapability(new ResourceLocation(DRPCoreReferences.MODID, "lock_handler"), new CapabilityProvider(DRPCoreCapabilities.LOCK_HANDLER));
	}
	
	@SubscribeEvent
    public void attachCapabilityWorld(AttachCapabilitiesEvent<World> event){
        if (!(event.getObject() instanceof World)) return;
        
        
        event.addCapability(new ResourceLocation(DRPCoreReferences.MODID, "date_handler"), new CapabilityProvider(DRPCoreCapabilities.DATE_HANDLER));

	}
}
