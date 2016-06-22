package net.drpcore.client.events;

import net.drpcore.client.gui.guis.crafting.RecipeSelection;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;


public class RenderTick {

	@SubscribeEvent
	public void RenderTickEvent(TickEvent.ClientTickEvent event) {

		if(event.phase == TickEvent.Phase.START) {
			if(Minecraft.getMinecraft().currentScreen instanceof RecipeSelection) {
				RecipeSelection gui = (RecipeSelection) Minecraft.getMinecraft().currentScreen;
				gui.currentTicks++ ;
				if(gui.currentTicks == 20) {
					gui.currentTicks = 0;
					gui.passedSeconds++ ;
				}
			}
		}
	}
}
