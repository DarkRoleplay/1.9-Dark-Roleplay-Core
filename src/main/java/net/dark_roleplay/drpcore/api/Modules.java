package net.dark_roleplay.drpcore.api;

import net.dark_roleplay.drpcore.modules.Module;
import net.dark_roleplay.drpcore.modules.update_check.Module_UpdateChecker;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Modules {
	
	public static final Module HUD = new Module("HUD"){
		@Override
		public void preInit(FMLPreInitializationEvent event){
			MinecraftForge.EVENT_BUS.register(new net.dark_roleplay.drpcore.modules.hud.Event_RenderHud());
		}
	};

	public static final Module CONFIGS = new Module("CONFIGS");
	public static final Module QUEST = new Module("QUEST");
	public static final Module CRAFTING = new Module("CRAFTING");
	public static final Module PARTY = new Module("PARTY", HUD);
	public static final Module SKILL = new Module("SKILL", HUD);
	public static final Module UPDATE_CHECKER = new Module_UpdateChecker("UPDATE_CHECKER", CONFIGS);
	
}
