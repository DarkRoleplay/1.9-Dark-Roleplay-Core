package net.dark_roleplay.drpcore.client.events.ticks;

import net.dark_roleplay.drpcore.api.gui.ITimedGui;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class Event_ClientTick {

	@SubscribeEvent
	public void RenderTickEvent(TickEvent.ClientTickEvent event) {

		if(event.phase == TickEvent.Phase.START) {
			if(Minecraft.getMinecraft().currentScreen instanceof ITimedGui) {
				ITimedGui gui = (ITimedGui) Minecraft.getMinecraft().currentScreen;
				gui.increaseTimer(1);;
			}
		}
	
		//TODO ADD INFORMATION GUI
		//GuiRenderHandler.infGui.increaseTimer(1);
	}
}
