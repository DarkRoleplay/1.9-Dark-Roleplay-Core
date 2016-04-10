package net.drpcore.common.events;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.entities.player.advancedInventoryCapabiliy.PlayerCapabilityProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AttachCapabilitiesEntity {

	@SubscribeEvent
	public void attachPlayerCapabilities(AttachCapabilitiesEvent.Entity event) {

		event.addCapability(new ResourceLocation(DarkRoleplayCore.MODID, "DRPCore_Inventory"), new PlayerCapabilityProvider(event.getEntity()));
		
	}
	
}
