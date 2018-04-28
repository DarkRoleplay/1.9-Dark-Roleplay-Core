package net.dark_roleplay.drpcore.common.config;

import net.dark_roleplay.drpcore.common.References;
import net.dark_roleplay.drpcore.common.handler.DRPCoreConfigs.FoodStatsDisplayType;
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
		
		@Config.Comment("Some Settings to customize the blueprint controller")
		public static Blueprints BLUEPRINTS = new Blueprints();
	}
	
	public static class Tutorial{
		
		@Config.Name("Show Crafting Tutorial")
		@Config.Comment("Set to true this will prevent the crafting gui to show up again.")
		public boolean SHOW_CRAFTING_TUT = true;
		
	}
	
	public static class Blueprints{
		
		@Config.Name("Draws two boxes for invisible blocks")
		@Config.Comment("Increases visability but can cause higher fps drops ")
		public boolean HIGHLIGHT_INVISIBLE_BLOCKS = true;
		
		@Config.Name("Ivisible Blocks color")
		@Config.Comment("The Color in which invisible blocks will be highlighted")
		public RGBA INVISIBLE_BLOCKS_COLOR = new RGBA(0.5F, 0.5F, 1.0F, 0.5F);
		
	}
	
	public static class RGB{
		
		@Config.Name("Red Value")
		@Config.Comment("1.0 = full red, 0.0 = not red at all")
		@Config.RangeDouble(min = 0.0, max = 1.0)
		public float RED = 1.0F;
		
		@Config.Name("Green Value")
		@Config.Comment("1.0 = full green, 0.0 = not green at all")
		@Config.RangeDouble(min = 0.0, max = 1.0)
		public float GREEN = 1.0F;
		
		@Config.Name("Blue Value")
		@Config.Comment("1.0 = full blue, 0.0 = not blue at all")
		@Config.RangeDouble(min = 0.0, max = 1.0)
		public float BLUE = 1.0F;
		
		public RGB(float red, float green, float blue) {
			this.RED = red;
			this.GREEN = green;
			this.BLUE = blue;
		}
	}
	
	public static class RGBA{
		
		@Config.Name("Red Value")
		@Config.Comment("1.0 = full red, 0.0 = not red at all")
		@Config.RangeDouble(min = 0.0, max = 1.0)
		public float RED = 1.0F;
		
		@Config.Name("Green Value")
		@Config.Comment("1.0 = full green, 0.0 = not green at all")
		@Config.RangeDouble(min = 0.0, max = 1.0)
		public float GREEN = 1.0F;
		
		@Config.Name("Blue Value")
		@Config.Comment("1.0 = full blue, 0.0 = not blue at all")
		@Config.RangeDouble(min = 0.0, max = 1.0)
		public float BLUE = 1.0F;
		
		@Config.Name("Alpha Value")
		@Config.Comment("1.0 = full visible, 0.0 = not visible at all")
		@Config.RangeDouble(min = 0.0, max = 1.0)
		public float ALPHA = 1.0F;
		
		public RGBA(float red, float green, float blue, float alpha) {
			this.RED = red;
			this.GREEN = green;
			this.BLUE = blue;
			this.ALPHA = alpha;
		}
	}
	
}