package net.drpcore.client.events;

import net.drpcore.client.gui.screens.GuiInformation;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class GuiRenderHandler {

	public static final GuiInformation infGui = new GuiInformation();
	
    @SubscribeEvent
    public void onRenderGui(RenderGameOverlayEvent.Post event){
    	if (event.getType() != ElementType.EXPERIENCE) return;
    	infGui.drawGui(Minecraft.getMinecraft());
    }
}