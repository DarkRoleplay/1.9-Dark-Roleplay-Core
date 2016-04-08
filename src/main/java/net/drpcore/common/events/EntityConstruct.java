package net.drpcore.common.events;

import net.drpcore.common.entities.player.ExtendedPlayer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityConstruct {
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event){
		//TODO Entity Construct
		//if (event.getEntity() instanceof EntityPlayer && ExtendedPlayer.get((EntityPlayer) event.getEntity()) == null){
		
			//ExtendedPlayer.register((EntityPlayer) event.getEntity());
		//}
			
	}
}
