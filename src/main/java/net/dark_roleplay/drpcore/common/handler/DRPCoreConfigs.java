package net.dark_roleplay.drpcore.common.handler;

import java.io.File;
import java.lang.invoke.MethodHandle;

import net.dark_roleplay.drpcore.client.events.config.Event_ConfigChange;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.dark_roleplay.drpcore.common.config.SyncedConfigRegistry;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;

@Config(modid = DRPCoreInfo.MODID, name = "Dark Roleplay Core/Dark Roleplay Core", category = "drpcore")
public class DRPCoreConfigs {

	@Config.Comment("This category contains everything that affects the mod but not any of it's features.")
	public static General GENERAL = new General();
	
	@Config.Comment("This category contains everything that deals with crafting.")
	public static Crafting CRAFTING = new Crafting();
	
	@Config.Comment("This category is mainly for modders that want to have some example and test stuff.")
	public static Debug DEBUG = new Debug();
	
	@Config.Comment("Everything that is just important for the Client")
	public static Client CLIENT = new Client();

	
	public static class General{
		
		@Config.Name("Enable Placement Preview")
		@Config.Comment("Show a preview of how the block that you are holding would be placed. \nAllowed values: true, false")
		public boolean PLACEMENT_PREVIEW = true;
		
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
	
	public static class Debug{
		
		@Config.Name("Enable Debug Items")
		@Config.Comment("Enables debug items ingame. \nAllowed values: true, false")
		@Config.RequiresMcRestart
		public boolean DEBUG_ITEMS = false;
		
		@Config.Name("Enable Debug Blocks")
		@Config.Comment("Enables debug blocks ingame. \nAllowed values: true, false")
		@Config.RequiresMcRestart
		public boolean DEBUG_BLOCKS = false;
		
		@Config.Name("Enable Debug Key")
		@Config.Comment("Enables debug keybinds ingame. \nAllowed values: true, false")
		@Config.RequiresMcRestart
		public boolean DEBUG_KEY = false;
		
		@Config.Name("Display recipe names")
		@Config.Comment("Display Recipe Names in Crafting GUI's. \nAllowed values: true, false")
		public boolean DEBUG_RECIPE_NAMES = false;
	}
	
	public static class Client{
		
		@Config.Comment("All you need to customize your hud")
		public Hud HUD = new Hud();
		
		@Config.Comment("Everything that deals with blueprints and the blueprint controller")
		public Blueprints BLUEPRINTS = new Blueprints();
		
		public class Hud{
			
			@Config.Name("Draw a Realtime Clock")
			@Config.Comment("Enables a realtime Clock")
			public boolean DRAW_REALTIME_CLOCK = true;
			
		}
		
		public class Blueprints{
			
			@Config.Name("Draws two boxes for invisible blocks")
			@Config.Comment("Increases visability but can cause higher fps drops ")
			public boolean HIGHLIGHT_INVISIBLE_BLOCKS = true;
			
			@Config.Name("Invisible block color: RED")
			@Config.Comment("How strong red color should invisible block have?")
			@Config.RangeDouble(min = 0.0, max = 1.0)
			public float INVISIBLE_BLOCKS_RED = 0.5F;
			
			@Config.Name("Invisible block color: GREEN")
			@Config.Comment("How strong green color should invisible block have?")
			@Config.RangeDouble(min = 0.0, max = 1.0)
			public float INVISIBLE_BLOCKS_GREEN = 0.5F;
			
			@Config.Name("Invisible block color: BLUE")
			@Config.Comment("How strong blue color should invisible block have?")
			@Config.RangeDouble(min = 0.0, max = 1.0)
			public float INVISIBLE_BLOCKS_BLUE = 1.0F;
			
			@Config.Name("Invisible block transparency")
			@Config.Comment("Defines how transparent the invisible blocks hould be rendered")
			@Config.RangeDouble(min = 0.0, max = 1.0)
			public float INVISIBLE_BLOCKS_ALPHA = 0.5F;
			
		}
	}
}
