package net.dark_roleplay.drpcore.common.handler;

import net.dark_roleplay.drpcore.common.capabilities.player.crafting.IRecipeController;
import net.dark_roleplay.drpcore.common.capabilities.player.crafting.RecipeControllerFactory;
import net.dark_roleplay.drpcore.common.capabilities.player.crafting.RecipeControllerStorage;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class DRPCoreCapabilities {

	@CapabilityInject(IRecipeController.class)
	public static final Capability<IRecipeController> DRPCORE_RECIPE_CONTROLLER = null;
	
	
	public static void preInit(){
	}
	
	public static final void preInit(FMLPreInitializationEvent event) {}
	
	public static final void init(FMLInitializationEvent event) {
		CapabilityManager.INSTANCE.register(IRecipeController.class, new RecipeControllerStorage(), new RecipeControllerFactory());
	}

	public static final void postInit(FMLPostInitializationEvent event) {}
}
