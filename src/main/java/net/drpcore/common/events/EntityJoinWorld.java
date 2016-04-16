package net.drpcore.common.events;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.gui.inventories.AdvancedPlayerInventory;
import net.drpcore.common.network.PacketHandler;
import net.drpcore.common.network.PacketOpenPurse;
import net.drpcore.common.network.PacketSyncAdvancedInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityJoinWorld {

	@SubscribeEvent
	public void onEntityJoinWorld(EntityJoinWorldEvent event){
		if (event.getEntity() instanceof EntityPlayer){
			
			//if(event.getEntity().hasCapability(DarkRoleplayCore.DRPCORE_INV, null)){
				
				//AdvancedPlayerInventory inventory = event.getEntity().getCapability(DarkRoleplayCore.DRPCORE_INV, null).getInventory();
	
				//for(int i = 0; i < inventory.getSizeInventory(); i++){
				//	PacketHandler.sendToServer(new PacketSyncAdvancedInventory(i, (EntityPlayer) event.getEntity()));
				//}
			//}
		}
	}
	
}
