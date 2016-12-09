package net.dark_roleplay.drpcore.client.events.config;

import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.dark_roleplay.drpcore.common.handler.DRPCoreConfigs;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Event_ConfigChange {

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if(event.getModID().equals(DRPCoreInfo.MODID))
			DRPCoreConfigs.load();
	}
	
}
