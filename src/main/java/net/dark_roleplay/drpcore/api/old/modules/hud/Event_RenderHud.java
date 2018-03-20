package net.dark_roleplay.drpcore.api.old.modules.hud;

import java.util.List;

import net.dark_roleplay.drpcore.api.old.Modules;
import net.dark_roleplay.drpcore.api.old.util.DRPRegistries;
import net.dark_roleplay.drpcore.common.handler.DRPCoreConfigs;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Event_RenderHud {
	@SubscribeEvent
	public void renderHud(RenderGameOverlayEvent.Post event){
		if(event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE){
			int width = event.getResolution().getScaledWidth();
			int height = event.getResolution().getScaledHeight();
			if(Modules.HUD.isEnabled()){
				List<Hud> huds = DRPRegistries.getHudRegistry().getValues();
				for(Hud hud : huds){
					hud.render(width, height, event.getPartialTicks());
				}
			}
		}
	}
}
