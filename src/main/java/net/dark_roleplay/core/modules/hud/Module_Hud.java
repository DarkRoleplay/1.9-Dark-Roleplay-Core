package net.dark_roleplay.core.modules.hud;

import net.dark_roleplay.core.api.old.modules.Module;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Module_Hud extends Module{

	public Module_Hud(String name, Module... requiredModules) {
		super(name, requiredModules);
	}
	
	@Override
	public void preInit(FMLPreInitializationEvent event){
		MinecraftForge.EVENT_BUS.register(new EventHandler());
	}

	@Override
	public void postInit(FMLPostInitializationEvent event){
		HudLoader.initializeHuds();
	}

}
