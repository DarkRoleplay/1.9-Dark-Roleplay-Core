package net.dark_roleplay.core.common.handler;

import net.dark_roleplay.core.common.References;
import net.minecraftforge.common.config.Config;

@Config(modid = References.MODID, name = "Dark Roleplay Core/Dark Roleplay Core", category = "drpcore")
public class DRPCoreConfigs {

	@Config.Comment("This category contains everything that affects the mod but not any of it's features.")
	public static General GENERAL = new General();
	
	@Config.Comment("This category contains everything that deals with crafting.")
	public static Crafting CRAFTING = new Crafting();
	
	public static class General{
		
		@Config.Name("Enable Update Info")
		@Config.Comment("Shows informational screen if a new update is available. \nAllowed values: true, false")
		public boolean UPDATE_GUI = true;
		
		@Config.Name("First Time Installed")
		@Config.Comment("Shows informational screen when this value is set to true, automaticly sets to false. \nAllowed values: true, false")
		public boolean FIRST_INSTALL = true;
	}
	
	public static class Crafting{
		
		@Config.Name("Hide Locked Crafting Recipes")
		@Config.Comment("If set to true recipes that were locked wont be shown in the crafting screen. \nAllowed values: true, false")
		public boolean HIDE_LOCKED_RECIPES = false;
		
		@Config.Name("Hide Unknown Crafting Recipes")
		@Config.Comment("If set to true recipes that you haven't unlocked wont be shown in the crafting screen. \nAllowed values: true, false")
		public boolean HIDE_UNKNOWN_RECIPES = false;
	}
	
	public static enum FoodStatsDisplayType{
		PENTAGON,
		LINES
	}
}
