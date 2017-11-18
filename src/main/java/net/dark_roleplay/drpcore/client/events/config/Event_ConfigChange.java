package net.dark_roleplay.drpcore.client.events.config;

import net.dark_roleplay.drpcore.client.keybindings.DRPCoreKeybindings;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.dark_roleplay.drpcore.common.DarkRoleplayCore;
import net.dark_roleplay.drpcore.common.handler.DRPCoreConfigs;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Event_ConfigChange {

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if(event.getModID().equals(DRPCoreInfo.MODID)){
			ConfigManager.sync(DRPCoreInfo.MODID, Config.Type.INSTANCE);
			if(DRPCoreConfigs.DEBUG.DEBUG_KEY)
				DRPCoreKeybindings.enableDebugKeys();
			else{
				DRPCoreKeybindings.disableDebugKeys();
			}
		}
	}
}
