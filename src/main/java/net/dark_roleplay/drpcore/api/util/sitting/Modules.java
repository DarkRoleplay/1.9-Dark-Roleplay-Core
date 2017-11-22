package net.dark_roleplay.drpcore.api.util.sitting;

import net.dark_roleplay.drpcore.modules.Module;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Modules {
	
	public static final Module HUD = new Module("HUD"){
		@Override
		public void preInit(FMLPreInitializationEvent event){
			MinecraftForge.EVENT_BUS.register(new net.dark_roleplay.drpcore.modules.hud.Event_RenderHud());
		}
	};
	
	public static final Module QUEST = new Module("QUEST");
	public static final Module CRAFTING = new Module("CRAFTING");
	public static final Module PARTY = new Module("PARTY", HUD);
	public static final Module SKILL = new Module("SKILL", HUD);
	
}
