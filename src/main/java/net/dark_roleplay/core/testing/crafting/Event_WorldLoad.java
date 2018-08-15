package net.dark_roleplay.core.testing.crafting;

import java.io.IOException;

import net.dark_roleplay.core.References;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = References.MODID)
public class Event_WorldLoad {

	@SubscribeEvent
	public static void worldLoad(WorldEvent.Load event) {
		CraftingRegistry.loadCustomRecipes();
		try {
			CraftingRegistry.getIDMappings();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@SubscribeEvent
	public static void worldUnload(WorldEvent.Unload event) {
		CraftingRegistry.resetRegistry();
	}
}
