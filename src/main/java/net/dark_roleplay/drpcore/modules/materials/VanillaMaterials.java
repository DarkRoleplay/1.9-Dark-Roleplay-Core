package net.dark_roleplay.drpcore.modules.materials;

import net.dark_roleplay.drpcore.common.DRPCoreReferences;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@EventBusSubscriber
public class VanillaMaterials {
	
	@SubscribeEvent
	public static void addWoods(AddMaterials<MaterialWood> event){
		String path = DRPCoreReferences.MODID + ":argh/textures/wood/";
		event.addMaterial(
			new MaterialWood("oak", 
				new ResourceLocation(path + "bark_oak.png"), 
				new ResourceLocation(path + "log_oak_top.png"), 
				new ResourceLocation(path + "planks_oak.png"), 
				new ResourceLocation(path + "clean_planks_oak.png"))
		);
		
		event.addMaterial(
			new MaterialWood("spruce", 
				new ResourceLocation(path + "bark_spruce.png"), 
				new ResourceLocation(path + "log_spruce_top.png"), 
				new ResourceLocation(path + "planks_spruce.png"), 
				new ResourceLocation(path + "clean_planks_spruce.png"))
		);
		
		event.addMaterial(
			new MaterialWood("birch", 
				new ResourceLocation(path + "bark_birch.png"), 
				new ResourceLocation(path + "log_birch_top.png"), 
				new ResourceLocation(path + "planks_birch.png"), 
				new ResourceLocation(path + "clean_planks_birch.png"))
		);
		
		event.addMaterial(
			new MaterialWood("jungle", 
				new ResourceLocation(path + "bark_jungle.png"), 
				new ResourceLocation(path + "log_jungle_top.png"), 
				new ResourceLocation(path + "planks_jungle.png"), 
				new ResourceLocation(path + "clean_planks_jungle.png"))
		);
		
		event.addMaterial(
			new MaterialWood("acacia", 
				new ResourceLocation(path + "bark_acacia.png"), 
				new ResourceLocation(path + "log_acacia_top.png"), 
				new ResourceLocation(path + "planks_acacia.png"), 
				new ResourceLocation(path + "clean_planks_acacia.png"))
		);
		
		event.addMaterial(
			new MaterialWood("dark_oak", 
				new ResourceLocation(path + "bark_dark_oak.png"), 
				new ResourceLocation(path + "log_dark_oak_top.png"), 
				new ResourceLocation(path + "planks_dark_oak.png"), 
				new ResourceLocation(path + "clean_planks_dark_oak.png"))
		);
	}
}
