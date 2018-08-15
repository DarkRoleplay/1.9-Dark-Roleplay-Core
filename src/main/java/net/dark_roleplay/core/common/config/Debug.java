package net.dark_roleplay.core.common.config;

import net.dark_roleplay.core.References;
import net.minecraftforge.common.config.Config;

@Config(modid = References.MODID, name = "Dark Roleplay Core/Debug", category = "debug")
public class Debug {

	@Config.Name("Enable Debug Items")
	@Config.Comment("Enables debug items ingame. \nAllowed values: true, false")
	@Config.RequiresMcRestart
	public static boolean DEBUG_ITEMS = false;
	
	@Config.Name("Enable Debug Blocks")
	@Config.Comment("Enables debug blocks ingame. \nAllowed values: true, false")
	@Config.RequiresMcRestart
	public static boolean DEBUG_BLOCKS = false;
	
	@Config.Name("Enable Debug Key")
	@Config.Comment("Enables debug keybinds ingame. \nAllowed values: true, false")
	public static boolean DEBUG_KEY = false;
	
	@Config.Name("Display recipe names")
	@Config.Comment("Display Recipe Names in Crafting GUI's. \nAllowed values: true, false")
	public static boolean DEBUG_RECIPE_NAMES = false;
	
}
