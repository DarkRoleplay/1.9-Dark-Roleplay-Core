package net.dark_roleplay.core.common.config;

import net.dark_roleplay.core.References;
import net.dark_roleplay.core.common.handler.DRPCoreConfigs.FoodStatsDisplayType;
import net.dark_roleplay.library.configs.prefabs.RGB;
import net.minecraftforge.common.config.Config;

@Config(modid = References.MODID, name = "Dark Roleplay Core/Client", category = "client")
public class Client {

	@Config.Comment("All you need to customize your hud")
	public static Hud HUD = new Hud();

	@Config.Comment("Containing Settings for food information gui's")
	public static Food FOOD = new Food();

	@Config.Comment("Everything you might want to improve your building experience")
	public static Building BUILDING = new Building();

	@Config.Comment("Containing Settings for Tutorial Toasts")
	public static Tutorial TUTORIAL = new Tutorial();

	@Config.Comment("Disables the Button that's added to the main menu when an update is available")
	public static boolean DISABLE_UPDATE_INFO = false;

	public static class Hud{

		@Config.Name("Draw a Realtime Clock")
		@Config.Comment("Enables a realtime Clock")
		public boolean DRAW_REALTIME_CLOCK = true;

	}

	public static class Food{

		@Config.Name("Draws two boxes for invisible blocks")
		@Config.Comment("Increases visability but can cause higher fps drops ")
		public FoodStatsDisplayType FOOD_STAT_TYPE = FoodStatsDisplayType.PENTAGON;

		@Config.Name("Single Color Mode")
		@Config.Comment("Enables the food information gui to just use a single color")
		public boolean SINGLE_COLOR_MODE = false;

		@Config.Name("Primary Dual Color")
		@Config.Comment("The primary color for dual color mode")
		public RGB DUAL_COLOR_ONE = new RGB(1F, 0F, 0F);

		@Config.Name("Secondary Dual Color")
		@Config.Comment("The secondary color for dual color mode")
		public RGB DUAL_COLOR_TWO = new RGB(0F, 1F, 0F);

		@Config.Name("Single Color Mode Color")
		@Config.Comment("This color is used")
		public RGB SINGLE_COLOR = new RGB(1F, 1F, 1F);

	}

	public static class Building{

		@Config.Name("Enable Placement Preview")
		@Config.Comment("Show a preview of how the block that you are holding would be placed. \nAllowed values: true, false")
		public boolean PLACEMENT_PREVIEW = true;
	}

	public static class Tutorial{

		@Config.Name("Show Crafting Tutorial")
		@Config.Comment("Set to true this will prevent the crafting gui to show up again.")
		public boolean SHOW_CRAFTING_TUT = true;

	}

}