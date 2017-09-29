package net.dark_roleplay.drpcore.client.events.rendering;

import java.util.Calendar;
import java.util.TimeZone;


import net.dark_roleplay.drpcore.api.gui.advanced.IGuiElement;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Event_RenderHud {

	private static Calendar instance = Calendar.getInstance();
	private static ResourceLocation clockTexture = new ResourceLocation(DRPCoreInfo.MODID, "textures/hud/clock.png");
	private static ResourceLocation hourTexture = new ResourceLocation(DRPCoreInfo.MODID, "textures/hud/hour.png");
	private static ResourceLocation minuteTexture = new ResourceLocation(DRPCoreInfo.MODID, "textures/hud/minute.png");

	public static IGuiElement clock = new IGuiElement.IMPL(){
		@Override
		public void draw(int mouseX, int mouseY, float partialTicks) {
			Minecraft.getMinecraft().renderEngine.bindTexture(clockTexture);
			this.drawTexturedCenteredRect(this.posX + 16, this.posY + 16, 0F, 0F, 1F, 1F, 16, 0D);

			//Angle
			float hourAngle = 30 * Calendar.getInstance().get(Calendar.HOUR_OF_DAY) - 90;
			float minuteAndlge = 6 * Calendar.getInstance().get(Calendar.MINUTE) - 90;

			Minecraft.getMinecraft().renderEngine.bindTexture(minuteTexture);
			this.drawTexturedCenteredRect(this.posX + 16, this.posY + 16, 0F, 0F, 1F, 1F, 16, minuteAndlge);
			Minecraft.getMinecraft().renderEngine.bindTexture(hourTexture);
			this.drawTexturedCenteredRect(this.posX + 16, this.posY + 16, 0F, 0F, 1F, 1F, 16, hourAngle);
//			System.out.println(Calendar.getInstance().getTime());
//			System.out.println(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
//			System.out.println(Calendar.getInstance().get(Calendar.MINUTE));
		}
	};
	
	@SubscribeEvent
	public void renderHud(RenderGameOverlayEvent event){
		if(event.getType() == RenderGameOverlayEvent.ElementType.EXPERIENCE){
			clock.setPos(event.getResolution().getScaledWidth() - 34, 0);
			clock.draw(0, 0, event.getPartialTicks());
		}
	}
	
}
