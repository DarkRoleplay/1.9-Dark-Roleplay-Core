package net.dark_roleplay.drpcore.modules.hud;

import java.util.List;

import net.dark_roleplay.drpcore.api.Modules;
import net.dark_roleplay.drpcore.api.util.DRPRegistries;
import net.dark_roleplay.drpcore.common.handler.DRPCoreConfigs;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Event_RenderHud {
	@SubscribeEvent
	public void renderHud(RenderGameOverlayEvent.Post event){
		if(event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE){
			if(Modules.HUD.isEnabled()){
				List<Hud> huds = DRPRegistries.getRegistryHUDs().getValues();
				for(Hud hud : huds){
					hud.render(event.getPartialTicks());
				}
			}
		}
	}
}
