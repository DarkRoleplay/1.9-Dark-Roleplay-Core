package net.dark_roleplay.core.client.events.rendering;

import net.dark_roleplay.core.common.References;
import net.dark_roleplay.core.common.config.Client;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = References.MODID, value = Side.CLIENT)
public class Event_RenderTooltip extends Gui {

	private static Event_RenderTooltip tooltip = new Event_RenderTooltip();


	@SubscribeEvent
	public static void highlightGhostBlock(RenderTooltipEvent.Color event) {
	}
	
	@SubscribeEvent
	public static void highlightGhostBlock(RenderTooltipEvent.PostText event) {
//		if(!GuiScreen.isCtrlKeyDown())
//			return;
//		
//		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
//		
//		
//		FontRenderer font = event.getFontRenderer();
//		
//		int posX = 0;
//		int posY = 0;
//		
//		if(Client.FOOD.FOOD_STAT_TYPE == DRPCoreConfigs.FoodStatsDisplayType.LINES) {
//			posX = event.getX() + 1;
//			posY = event.getY() + 25;
//			drawLines(posX, posY, res, 0.1F, 0.0F, 1.0F, 0.15F, 0.2F);
//			font.drawStringWithShadow("Sweet", posX + 53, posY + 2, 0xFFFFFFFF); //20  -
//			font.drawStringWithShadow("Bitter", posX + 53, posY + 12, 0xFFFFFFFF); //0
//			font.drawStringWithShadow("Umami", posX + 53, posY + 22, 0xFFFFFFFF); //40
//			font.drawStringWithShadow("Salty", posX + 53, posY + 32, 0xFFFFFFFF); //5
//			font.drawStringWithShadow("Sour", posX + 53, posY + 42, 0xFFFFFFFF); //35
//		}else {
//			posX = event.getX() + (font.getStringWidth("                             ") / 2) - 20;
//			posY = event.getY() + 45;
//			drawPentagon(posX, event.getY() + 32, res, 0.1F, 0.0F, 1.0F, 0.15F, 0.2F);
//			font.drawStringWithShadow("Sweet", posX + 20 - (font.getStringWidth("Sweet")/2), event.getY() + 23, 0xFFFFFFFF); //20  -
//			font.drawStringWithShadow("Sour", posX - font.getStringWidth("Sour"), event.getY() + 42, 0xFFFFFFFF); //0
//			font.drawStringWithShadow("Bitter", posX + 40, event.getY() + 42, 0xFFFFFFFF); //40
//			font.drawStringWithShadow("Salty", posX + 5 - font.getStringWidth("Salty"), event.getY() + 70, 0xFFFFFFFF); //5
//			font.drawStringWithShadow("Umami", posX + 35, event.getY() + 70, 0xFFFFFFFF); //35
//		}
	}
	
	/**
	 * 
	 * @param posX
	 * @param posY
	 * @param res
	 * @param f1 Sweet
	 * @param f2 Bitter
	 * @param f3 Spicy
	 * @param f4 Salty
	 * @param f5 Sour
	 */
	public static void drawLines(int posX, int posY, ScaledResolution res, float sweet, float bitter, float umami, float salty, float sour) {
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.shadeModel(7425);
		
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		
		int width = 50;
		int height = 52;
		
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos( posX +     0, posY + height, 50).color(0.1f, 0.1f, 0.1f, 1f).endVertex();
		bufferbuilder.pos( posX + width, posY + height, 50).color(0.1f, 0.1f, 0.1f, 1f).endVertex();
		bufferbuilder.pos( posX + width, posY +      0, 50).color(0.1f, 0.1f, 0.1f, 1f).endVertex();
		bufferbuilder.pos( posX +     0, posY +      0, 50).color(0.1f, 0.1f, 0.1f, 1f).endVertex();
		tessellator.draw();

		GlStateManager.glLineWidth(res.getScaleFactor());
		bufferbuilder.begin(2, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos( posX +     0, posY + height, 50).color(0.75f, 0.75f, 0.75f, 1f).endVertex();
		bufferbuilder.pos( posX + width, posY + height, 50).color(0.75f, 0.75f, 0.75f, 1f).endVertex();
		bufferbuilder.pos( posX + width, posY +      0, 50).color(0.75f, 0.75f, 0.75f, 1f).endVertex();
		bufferbuilder.pos( posX +     0, posY +      0, 50).color(0.75f, 0.75f, 0.75f, 1f).endVertex();
		tessellator.draw();

		posX += 1;
		posY += 2;
		width -= 2;

		for(int i = 0; i < 5; i++) {
			float multiplier = i == 0 ? sweet : i == 1 ? bitter : i == 2 ? umami : i == 3 ? salty : sour;
			bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
			
			float redLeft = Client.FOOD.SINGLE_COLOR_MODE ? Client.FOOD.SINGLE_COLOR.RED : Client.FOOD.DUAL_COLOR_ONE.RED;
			float greenLeft = Client.FOOD.SINGLE_COLOR_MODE ? Client.FOOD.SINGLE_COLOR.GREEN : Client.FOOD.DUAL_COLOR_ONE.GREEN;
			float blueLeft = Client.FOOD.SINGLE_COLOR_MODE ? Client.FOOD.SINGLE_COLOR.BLUE : Client.FOOD.DUAL_COLOR_ONE.BLUE;
			float redRight = Client.FOOD.SINGLE_COLOR_MODE ? Client.FOOD.SINGLE_COLOR.RED : (Client.FOOD.DUAL_COLOR_ONE.RED * (1f - multiplier)) + (Client.FOOD.DUAL_COLOR_TWO.RED * multiplier);
			float greenRight = Client.FOOD.SINGLE_COLOR_MODE ? Client.FOOD.SINGLE_COLOR.GREEN : (Client.FOOD.DUAL_COLOR_ONE.GREEN * (1f - multiplier)) + (Client.FOOD.DUAL_COLOR_TWO.GREEN * multiplier);
			float blueRight =Client.FOOD.SINGLE_COLOR_MODE ? Client.FOOD.SINGLE_COLOR.BLUE :  (Client.FOOD.DUAL_COLOR_ONE.BLUE * (1f - multiplier)) + (Client.FOOD.DUAL_COLOR_TWO.BLUE * multiplier);
				
			bufferbuilder.pos( posX +     0, posY + 8, 50).color(redLeft, greenLeft, blueLeft, 1f).endVertex();
			bufferbuilder.pos( posX + width * multiplier, posY + 8, 50).color(redRight, greenRight, blueRight, 1f).endVertex();
			bufferbuilder.pos( posX + width * multiplier, posY + 0, 50).color(redRight, greenRight, blueRight, 1f).endVertex();
			bufferbuilder.pos( posX +     0, posY + 0, 50).color(redLeft, greenLeft, blueLeft, 1f).endVertex();

			tessellator.draw();
			posY += 10;
		}
		
		
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}
	
	/**
	 * 
	 * @param posX
	 * @param posY
	 * @param res
	 * @param sweet Sweet
	 * @param bitter Bitter
	 * @param umami Spicy
	 * @param salty Salty
	 * @param sour Sour
	 */
	public static void drawPentagon(int posX, int posY, ScaledResolution res, float sweet, float bitter, float umami, float salty, float sour) {
		posX += 20;
		posY += 20;
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.shadeModel(7425);
		
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		
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
		bufferbuilder.pos( posX +  0, posY - (20 * sweet), 50).color(
				Client.FOOD.SINGLE_COLOR_MODE ? Client.FOOD.SINGLE_COLOR.RED : (Client.FOOD.DUAL_COLOR_ONE.RED * (1f - sweet)) + (Client.FOOD.DUAL_COLOR_TWO.RED * sweet),
				Client.FOOD.SINGLE_COLOR_MODE ? Client.FOOD.SINGLE_COLOR.GREEN : (Client.FOOD.DUAL_COLOR_ONE.GREEN * (1f - sweet)) + (Client.FOOD.DUAL_COLOR_TWO.GREEN * sweet),
				Client.FOOD.SINGLE_COLOR_MODE ? Client.FOOD.SINGLE_COLOR.BLUE : (Client.FOOD.DUAL_COLOR_ONE.BLUE * (1f - sweet)) + (Client.FOOD.DUAL_COLOR_TWO.BLUE * sweet),
			1f).endVertex();
		bufferbuilder.pos( posX - (19 * sour), posY -  (6 * sour), 50).color(
				Client.FOOD.SINGLE_COLOR_MODE ? Client.FOOD.SINGLE_COLOR.RED : (Client.FOOD.DUAL_COLOR_ONE.RED * (1f - sour)) + (Client.FOOD.DUAL_COLOR_TWO.RED * sour),
				Client.FOOD.SINGLE_COLOR_MODE ? Client.FOOD.SINGLE_COLOR.GREEN : (Client.FOOD.DUAL_COLOR_ONE.GREEN * (1f - sour)) + (Client.FOOD.DUAL_COLOR_TWO.GREEN * sour),
				Client.FOOD.SINGLE_COLOR_MODE ? Client.FOOD.SINGLE_COLOR.BLUE : (Client.FOOD.DUAL_COLOR_ONE.BLUE * (1f - sour)) + (Client.FOOD.DUAL_COLOR_TWO.BLUE * sour),
			1f).endVertex();
		bufferbuilder.pos( posX -  (12 * salty), posY + (16 * salty), 50).color(
				Client.FOOD.SINGLE_COLOR_MODE ? Client.FOOD.SINGLE_COLOR.RED : (Client.FOOD.DUAL_COLOR_ONE.RED * (1f - salty)) + (Client.FOOD.DUAL_COLOR_TWO.RED * salty),
				Client.FOOD.SINGLE_COLOR_MODE ? Client.FOOD.SINGLE_COLOR.GREEN : (Client.FOOD.DUAL_COLOR_ONE.GREEN * (1f - salty)) + (Client.FOOD.DUAL_COLOR_TWO.GREEN * salty),
				Client.FOOD.SINGLE_COLOR_MODE ? Client.FOOD.SINGLE_COLOR.BLUE : (Client.FOOD.DUAL_COLOR_ONE.BLUE * (1f - salty)) + (Client.FOOD.DUAL_COLOR_TWO.BLUE * salty),
			1f).endVertex();
		bufferbuilder.pos( posX +  (12 * umami), posY + (16 * umami), 50).color(
				Client.FOOD.SINGLE_COLOR_MODE ? Client.FOOD.SINGLE_COLOR.RED : (Client.FOOD.DUAL_COLOR_ONE.RED * (1f - umami)) + (Client.FOOD.DUAL_COLOR_TWO.RED * umami),
				Client.FOOD.SINGLE_COLOR_MODE ? Client.FOOD.SINGLE_COLOR.GREEN : (Client.FOOD.DUAL_COLOR_ONE.GREEN * (1f - umami)) + (Client.FOOD.DUAL_COLOR_TWO.GREEN * umami),
				Client.FOOD.SINGLE_COLOR_MODE ? Client.FOOD.SINGLE_COLOR.BLUE : (Client.FOOD.DUAL_COLOR_ONE.BLUE * (1f - umami)) + (Client.FOOD.DUAL_COLOR_TWO.BLUE * umami),
			1f).endVertex();
		bufferbuilder.pos( posX + (19 * bitter), posY - (6 * bitter), 50).color(
				Client.FOOD.SINGLE_COLOR_MODE ? Client.FOOD.SINGLE_COLOR.RED : (Client.FOOD.DUAL_COLOR_ONE.RED * (1f - bitter)) + (Client.FOOD.DUAL_COLOR_TWO.RED * bitter),
				Client.FOOD.SINGLE_COLOR_MODE ? Client.FOOD.SINGLE_COLOR.GREEN : (Client.FOOD.DUAL_COLOR_ONE.GREEN * (1f - bitter)) + (Client.FOOD.DUAL_COLOR_TWO.GREEN * bitter),
				Client.FOOD.SINGLE_COLOR_MODE ? Client.FOOD.SINGLE_COLOR.BLUE : (Client.FOOD.DUAL_COLOR_ONE.BLUE * (1f - bitter)) + (Client.FOOD.DUAL_COLOR_TWO.BLUE * bitter),
			1f).endVertex();
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
