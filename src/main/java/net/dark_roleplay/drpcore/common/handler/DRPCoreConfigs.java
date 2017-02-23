package net.dark_roleplay.drpcore.common.handler;

import java.io.File;

import net.dark_roleplay.drpcore.client.events.config.Event_ConfigChange;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class DRPCoreConfigs {

	public static Configuration config;
	
	/**-------------------- DEBUG STUFF --------------------**/
	public static boolean ENABLE_DEBUG_ITEMS = false;
	public static boolean ENABLE_DEBUG_BLOCKS = false;
	
	/**-------------------- CRAFTING STUFF --------------------**/
	
	//Recipe Gui
	public static boolean HIDE_LOCKED_RECIPES = false;
	public static boolean HIDE_UNKOWN_RECIPES = false;
	
	public static void loadConfig(File configFile) {
		config = new Configuration(configFile);

		config.load();
		load();

		//MinecraftForge.EVENT_BUS.register(new Event_ConfigChange());
	}
	
	public static void load() {
		Property prop;
		
		prop = config.get("debug","Enable debug items", ENABLE_DEBUG_ITEMS);
		prop.setComment("Set this to true when you want to enable debug items added by Dark Roleplay Core");
		prop.setRequiresMcRestart(true);
		ENABLE_DEBUG_ITEMS = prop.getBoolean();

		prop = config.get("debug","Enable debug blocks", ENABLE_DEBUG_ITEMS);
		prop.setComment("Set this to true when you want to enable debug blocks added by Dark Roleplay Core");
		prop.setRequiresMcRestart(true);
		ENABLE_DEBUG_BLOCKS = prop.getBoolean();
		
		prop = config.get("gui.crafting.selection","Hide unknown recipes", HIDE_UNKOWN_RECIPES);
		prop.setComment("Used to hide/show unknown recipes from recipe selection");
		HIDE_UNKOWN_RECIPES = prop.getBoolean();

		prop = config.get("gui.crafting.selection","Hide locked recipes", HIDE_LOCKED_RECIPES);
		prop.setComment("Used to hide/show locked recipes from recipe selection");
		HIDE_LOCKED_RECIPES = prop.getBoolean();
		
		if(config.hasChanged())
			config.save();
	}
}
