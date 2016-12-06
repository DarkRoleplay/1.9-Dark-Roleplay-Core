package net.drpcore.client.events;

import net.drpcore.client.gui.ITimedGui;
import net.drpcore.client.gui.craftingOld.guis.RecipeSelection;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;


public class RenderTick {

	@SubscribeEvent
	public void RenderTickEvent(TickEvent.ClientTickEvent event) {

		if(event.phase == TickEvent.Phase.START) {
			if(Minecraft.getMinecraft().currentScreen instanceof ITimedGui) {
				ITimedGui gui = (ITimedGui) Minecraft.getMinecraft().currentScreen;
				gui.increaseTimer(1);;
			}
		}
		
		GuiRenderHandler.infGui.increaseTimer(1);
	}
}
