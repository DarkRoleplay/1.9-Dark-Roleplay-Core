package net.dark_roleplay.core.client.events.config;

import net.dark_roleplay.core.References;
import net.dark_roleplay.core.client.keybindings.DRPCoreKeybindings;
import net.dark_roleplay.core.common.config.Debug;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = References.MODID)
public class Event_ConfigChange {

	@SubscribeEvent
	public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
		if(event.getModID().equals(References.MODID)){
			ConfigManager.sync(References.MODID, Config.Type.INSTANCE);
			if(Debug.DEBUG_KEY)
				DRPCoreKeybindings.enableDebugKeys();
			else{
				DRPCoreKeybindings.disableDebugKeys();
			}
		}
	}
}
