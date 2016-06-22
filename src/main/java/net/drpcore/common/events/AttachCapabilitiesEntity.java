package net.drpcore.common.events;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.capabilities.entities.player.advancedInventory.AdvancedInventoryProvider;
import net.drpcore.common.capabilities.entities.player.advancedInventory.DefaultAdvancedInventory;
import net.drpcore.common.gui.inventories.AdvancedPlayerInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;


public class AttachCapabilitiesEntity {

	@SubscribeEvent
	public void AttachCapability(AttachCapabilitiesEvent.Entity e) {

		if( ! e.getEntity().hasCapability(AdvancedInventoryProvider.ADVANCED_INVENTORY, null) && e.getEntity() instanceof EntityPlayer)
			e.addCapability(new ResourceLocation("drpcore_advancedInventory"), new AdvancedInventoryProvider(new DefaultAdvancedInventory()));
	}
}
