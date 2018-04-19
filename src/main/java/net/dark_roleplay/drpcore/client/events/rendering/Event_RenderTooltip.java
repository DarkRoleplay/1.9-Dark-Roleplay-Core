package net.dark_roleplay.drpcore.client.events.rendering;

import java.util.List;

import net.dark_roleplay.drpcore.common.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = References.MODID)
public class Event_RenderTooltip extends Gui {

	private static Event_RenderTooltip tooltip = new Event_RenderTooltip();


	@SubscribeEvent
	public static void highlightGhostBlock(RenderTooltipEvent.Color event) {
	}
	
	@SubscribeEvent
	public static void highlightGhostBlock(RenderTooltipEvent.PostText event) {
//		event.get
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.shadeModel(7425);
		
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		
		double posX = event.getX(), posY = event.getY() - 20;
		
		float percentSweet = 1.0F, percentSour = 0.75F, percentSpicy = 1.0F, percentBitter = 0.25F, percentSalty = 0.5F;
		
		//BG
		bufferbuilder.begin(9, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos( posX +  0, posY - 20, 50).color(0.1f, 0.1f, 0.1f, 1f).endVertex();
		bufferbuilder.pos( posX - 19, posY -  6, 50).color(0.1f, 0.1f, 0.1f, 1f).endVertex();
		bufferbuilder.pos( posX - 12, posY + 16, 50).color(0.1f, 0.1f, 0.1f, 1f).endVertex();
		bufferbuilder.pos( posX + 12, posY + 16, 50).color(0.1f, 0.1f, 0.1f, 1f).endVertex();
		bufferbuilder.pos( posX + 19, posY -  6, 50).color(0.1f, 0.1f, 0.1f, 1f).endVertex();
		tessellator.draw();
		
		//Actuall Values
		bufferbuilder.begin(9, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos( posX +  0, posY - (20 * percentSweet), 50)						.color(1f - 1f * percentSweet, 1f * percentSweet, 0f, 1f).endVertex();
		bufferbuilder.pos( posX - (19 * percentSour), posY -  (6 * percentSour), 50)		.color(1f - 1f * percentSour, 1f * percentSour, 0f, 1f).endVertex();
		bufferbuilder.pos( posX -  (12 * percentSpicy), posY + (16 * percentSpicy), 50)		.color(1f - 1f * percentSpicy, 1f * percentSpicy, 0f, 1f).endVertex();
		bufferbuilder.pos( posX +  (12 * percentBitter), posY + (16 * percentBitter), 50)	.color(1f - 1f * percentBitter, 1f * percentBitter, 0f, 1f).endVertex();
		bufferbuilder.pos( posX + (19 * percentSalty), posY - (6 * percentSalty), 50)		.color(1f - 1f * percentSalty, 1f * percentSalty, 0f, 1f).endVertex();
		tessellator.draw();
		
		GlStateManager.glLineWidth(1F);
		bufferbuilder.begin(1, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos( posX +  0, posY - 20, 51).color(0.75f, 0.75f, 0.75f, 1f).endVertex();
		bufferbuilder.pos( posX, posY, 51).color(0.75f, 0.75f, 0.75f, 1f).endVertex();
		bufferbuilder.pos( posX - 19, posY -  6, 51).color(0.75f, 0.75f, 0.75f, 1f).endVertex();
		bufferbuilder.pos( posX, posY, 51).color(0.75f, 0.75f, 0.75f, 1f).endVertex();
		bufferbuilder.pos( posX -  12, posY + 16, 51).color(0.75f, 0.75f, 0.75f, 1f).endVertex();
		bufferbuilder.pos( posX, posY, 51).color(0.75f, 0.75f, 0.75f, 1f).endVertex();
		bufferbuilder.pos( posX +  12, posY + 16, 51).color(0.75f, 0.75f, 0.75f, 1f).endVertex();
		bufferbuilder.pos( posX, posY, 51).color(0.75f, 0.75f, 0.75f, 1f).endVertex();
		bufferbuilder.pos( posX + 19, posY -  6, 51).color(0.75f, 0.75f, 0.75f, 1f).endVertex();
		bufferbuilder.pos( posX, posY, 51).color(0.75f, 0.75f, 0.75f, 1f).endVertex();
		tessellator.draw();
		
		GlStateManager.glLineWidth(res.getScaleFactor());
		bufferbuilder.begin(2, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos( posX +  0, posY - 20, 50).color(0f, 0f, 0f, 1f).endVertex();
		bufferbuilder.pos( posX - 19, posY -  6, 50).color(0f, 0f, 0f, 1f).endVertex();
		bufferbuilder.pos( posX -  12, posY + 16, 50).color(0f, 0f, 0f, 1f).endVertex();
		bufferbuilder.pos( posX +  12, posY + 16, 50).color(0f, 0f, 0f, 1f).endVertex();
		bufferbuilder.pos( posX + 19, posY -  6, 50).color(0f, 0f, 0f, 1f).endVertex();
		tessellator.draw();

		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}
}
