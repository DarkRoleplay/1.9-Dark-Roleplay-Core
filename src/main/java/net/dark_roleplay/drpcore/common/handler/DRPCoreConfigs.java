package net.dark_roleplay.drpcore.common.handler;

import java.io.File;

import net.dark_roleplay.drpcore.client.events.config.Event_ConfigChange;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class DRPCoreConfigs {

	public static Configuration config;
	
	public static boolean ENABLE_DEBUG_ITEMS = false;
	public static boolean ENABLE_DEBUG_BLOCKS = false;
	
	public static void loadConfig(File configFile) {
		config = new Configuration(configFile);

		config.load();
		load();

		//MinecraftForge.EVENT_BUS.register(new Event_ConfigChange());
	}
	
	public static void load() {
		Property prop;
		
		prop = config.get("debug","Enable Debug Items", ENABLE_DEBUG_ITEMS);
		prop.setComment("Set this to true when you want to enable debug items added by Dark Roleplay Core");
		prop.setRequiresMcRestart(true);
		ENABLE_DEBUG_ITEMS = prop.getBoolean();

		prop = config.get("debug","Enable Debug Block", ENABLE_DEBUG_ITEMS);
		prop.setComment("Set this to true when you want to enable debug blocks added by Dark Roleplay Core");
		prop.setRequiresMcRestart(true);
		ENABLE_DEBUG_BLOCKS = prop.getBoolean();
		
		if(config.hasChanged())
			config.save();
	}
}
