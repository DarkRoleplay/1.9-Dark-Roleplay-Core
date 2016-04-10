package net.drpcore.common.events;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EntityConstruct {
	
	@SubscribeEvent
	public void onEntityConstructing(EntityConstructing event){
	}
}
