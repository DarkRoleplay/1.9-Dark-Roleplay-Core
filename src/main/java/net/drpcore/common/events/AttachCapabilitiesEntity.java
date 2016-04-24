package net.drpcore.common.events;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.entities.player.advancedInventoryCapabiliy.PlayerCapabilityProvider;
import net.drpcore.common.gui.inventories.AdvancedPlayerInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class AttachCapabilitiesEntity {

	@SubscribeEvent
	public void attachPlayerCapabilities(AttachCapabilitiesEvent.Entity event) {

		if(event.getEntity() instanceof EntityPlayer) event.addCapability(new ResourceLocation(DarkRoleplayCore.MODID, "DRPCore_Inventory"), new PlayerCapabilityProvider(event.getEntity()));
	}

}
