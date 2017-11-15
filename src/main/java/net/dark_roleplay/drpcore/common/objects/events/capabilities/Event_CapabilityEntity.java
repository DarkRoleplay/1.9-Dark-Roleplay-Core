package net.dark_roleplay.drpcore.common.objects.events.capabilities;

import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.dark_roleplay.drpcore.common.capabilities.CapabilityProvider;
import net.dark_roleplay.drpcore.common.capabilities.player.crafting.*;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;

public class Event_CapabilityEntity {

	@SubscribeEvent
    public void attachCapability(AttachCapabilitiesEvent<Entity> event){
        if (!(event.getObject() instanceof EntityPlayer)) return;

        event.addCapability(new ResourceLocation(DRPCoreInfo.MODID, "recipe_controller"), new CapabilityProvider(DRPCoreCapabilities.DRPCORE_RECIPE_CONTROLLER));
        event.addCapability(new ResourceLocation(DRPCoreInfo.MODID, "skill_controller"), new CapabilityProvider(DRPCoreCapabilities.DRPCORE_SKILL_CONTROLLER));
        event.addCapability(new ResourceLocation(DRPCoreInfo.MODID, "extended_inv"), new CapabilityProvider(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY));
    }
	
}
