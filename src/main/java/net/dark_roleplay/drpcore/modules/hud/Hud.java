package net.dark_roleplay.drpcore.modules.hud;

import java.util.HashMap;

import org.lwjgl.util.Point;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.dark_roleplay.drpcore.api.skills.Skill;
import net.dark_roleplay.drpcore.api.skills.SkillTree;
import net.dark_roleplay.drpcore.api.skills.SkillTreeData;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Hud extends IForgeRegistryEntry.Impl<Hud> {

	protected int posX, posY;
	protected ALIGNMENT alignment;
    protected float zLevel;

	public Hud(ResourceLocation registryName) {
		this.setRegistryName(registryName);
		this.posX = 0;
		this.posY = 0;
		this.alignment = ALIGNMENT.TOP_LEFT;
	}

	public void render(float partialTicks) {}

	public JsonObject writeToDefaultConfig() {
		JsonObject obj = new JsonObject();
		obj.addProperty("style", "vanilla");
		return obj;
	}

	/**
	 * Draws a thin horizontal line between two points.
	 */
	protected void drawHorizontalLine(int startX, int endX, int y, int color) {
		if (endX < startX) {
			int i = startX;
			startX = endX;
			endX = i;
		}
		drawRect(startX, y, endX + 1, y + 1, color);
	}

	/**
	 * Draw a 1 pixel wide vertical line. Args : x, y1, y2, color
	 */
	protected void drawVerticalLine(int x, int startY, int endY, int color) {
		if (endY < startY) {
			int i = startY;
			startY = endY;
			endY = i;
		}
		drawRect(x, startY + 1, x + 1, endY, color);
	}

	/**
	 * Draws a solid color rectangle with the specified coordinates and color.
	 */
	public static void drawRect(int left, int top, int right, int bottom, int color) {
		if (left < right) {
			int i = left;
			left = right;
			right = i;
		}

		if (top < bottom) {
			int j = top;
			top = bottom;
			bottom = j;
		}

		float f3 = (float) (color >> 24 & 255) / 255.0F;
		float f = (float) (color >> 16 & 255) / 255.0F;
		float f1 = (float) (color >> 8 & 255) / 255.0F;
		float f2 = (float) (color & 255) / 255.0F;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.color(f, f1, f2, f3);
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
		bufferbuilder.pos((double) left, (double) bottom, 0.0D).endVertex();
		bufferbuilder.pos((double) right, (double) bottom, 0.0D).endVertex();
		bufferbuilder.pos((double) right, (double) top, 0.0D).endVertex();
		bufferbuilder.pos((double) left, (double) top, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}

	/**
	 * Draws a rectangle with a vertical gradient between the specified colors
	 * (ARGB format). Args : x1, y1, x2, y2, topColor, bottomColor
	 */
	protected void drawGradientRect(int left, int top, int right, int bottom, int startColor, int endColor) {
		float f = (float) (startColor >> 24 & 255) / 255.0F;
		float f1 = (float) (startColor >> 16 & 255) / 255.0F;
		float f2 = (float) (startColor >> 8 & 255) / 255.0F;
		float f3 = (float) (startColor & 255) / 255.0F;
		float f4 = (float) (endColor >> 24 & 255) / 255.0F;
		float f5 = (float) (endColor >> 16 & 255) / 255.0F;
		float f6 = (float) (endColor >> 8 & 255) / 255.0F;
		float f7 = (float) (endColor & 255) / 255.0F;
		GlStateManager.disableTexture2D();
		GlStateManager.enableBlend();
		GlStateManager.disableAlpha();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.shadeModel(7425);
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_COLOR);
		bufferbuilder.pos((double) right, (double) top, (double) this.zLevel).color(f1, f2, f3, f).endVertex();
		bufferbuilder.pos((double) left, (double) top, (double) this.zLevel).color(f1, f2, f3, f).endVertex();
		bufferbuilder.pos((double) left, (double) bottom, (double) this.zLevel).color(f5, f6, f7, f4).endVertex();
		bufferbuilder.pos((double) right, (double) bottom, (double) this.zLevel).color(f5, f6, f7, f4).endVertex();
		tessellator.draw();
		GlStateManager.shadeModel(7424);
		GlStateManager.disableBlend();
		GlStateManager.enableAlpha();
		GlStateManager.enableTexture2D();
	}

	/**
	 * Renders the specified text to the screen, center-aligned. Args :
	 * renderer, string, x, y, color
	 */
	public void drawCenteredString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
		fontRendererIn.drawStringWithShadow(text, (float) (x - fontRendererIn.getStringWidth(text) / 2), (float) y, color);
	}

	/**
	 * Renders the specified text to the screen. Args : renderer, string, x, y,
	 * color
	 */
	public void drawString(FontRenderer fontRendererIn, String text, int x, int y, int color) {
		fontRendererIn.drawStringWithShadow(text, (float) x, (float) y, color);
	}

	/**
	 * Draws a textured rectangle at the current z-value.
	 */
	public void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos((double) (x + 0), (double) (y + height), (double) this.zLevel).tex((double) ((float) (textureX + 0) * 0.00390625F), (double) ((float) (textureY + height) * 0.00390625F)).endVertex();
		bufferbuilder.pos((double) (x + width), (double) (y + height), (double) this.zLevel).tex((double) ((float) (textureX + width) * 0.00390625F), (double) ((float) (textureY + height) * 0.00390625F)).endVertex();
		bufferbuilder.pos((double) (x + width), (double) (y + 0), (double) this.zLevel).tex((double) ((float) (textureX + width) * 0.00390625F), (double) ((float) (textureY + 0) * 0.00390625F)).endVertex();
		bufferbuilder.pos((double) (x + 0), (double) (y + 0), (double) this.zLevel).tex((double) ((float) (textureX + 0) * 0.00390625F), (double) ((float) (textureY + 0) * 0.00390625F)).endVertex();
		tessellator.draw();
	}

	/**
	 * Draws a textured rectangle using the texture currently bound to the
	 * TextureManager
	 */
	public void drawTexturedModalRect(float xCoord, float yCoord, int minU, int minV, int maxU, int maxV) {
		float f = 0.00390625F;
		float f1 = 0.00390625F;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos((double) (xCoord + 0.0F), (double) (yCoord + (float) maxV), (double) this.zLevel).tex((double) ((float) (minU + 0) * 0.00390625F), (double) ((float) (minV + maxV) * 0.00390625F)).endVertex();
		bufferbuilder.pos((double) (xCoord + (float) maxU), (double) (yCoord + (float) maxV), (double) this.zLevel).tex((double) ((float) (minU + maxU) * 0.00390625F), (double) ((float) (minV + maxV) * 0.00390625F)).endVertex();
		bufferbuilder.pos((double) (xCoord + (float) maxU), (double) (yCoord + 0.0F), (double) this.zLevel).tex((double) ((float) (minU + maxU) * 0.00390625F), (double) ((float) (minV + 0) * 0.00390625F)).endVertex();
		bufferbuilder.pos((double) (xCoord + 0.0F), (double) (yCoord + 0.0F), (double) this.zLevel).tex((double) ((float) (minU + 0) * 0.00390625F), (double) ((float) (minV + 0) * 0.00390625F)).endVertex();
		tessellator.draw();
	}

	/**
	 * Draws a texture rectangle using the texture currently bound to the
	 * TextureManager
	 */
	public void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn) {
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos((double) (xCoord + 0), (double) (yCoord + heightIn), (double) this.zLevel).tex((double) textureSprite.getMinU(), (double) textureSprite.getMaxV()).endVertex();
		bufferbuilder.pos((double) (xCoord + widthIn), (double) (yCoord + heightIn), (double) this.zLevel).tex((double) textureSprite.getMaxU(), (double) textureSprite.getMaxV()).endVertex();
		bufferbuilder.pos((double) (xCoord + widthIn), (double) (yCoord + 0), (double) this.zLevel).tex((double) textureSprite.getMaxU(), (double) textureSprite.getMinV()).endVertex();
		bufferbuilder.pos((double) (xCoord + 0), (double) (yCoord + 0), (double) this.zLevel).tex((double) textureSprite.getMinU(), (double) textureSprite.getMinV()).endVertex();
		tessellator.draw();
	}

	/**
	 * Draws a textured rectangle at z = 0. Args: x, y, u, v, width, height,
	 * textureWidth, textureHeight
	 */
	public static void drawModalRectWithCustomSizedTexture(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight) {
		float f = 1.0F / textureWidth;
		float f1 = 1.0F / textureHeight;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos((double) x, (double) (y + height), 0.0D).tex((double) (u * f), (double) ((v + (float) height) * f1)).endVertex();
		bufferbuilder.pos((double) (x + width), (double) (y + height), 0.0D).tex((double) ((u + (float) width) * f), (double) ((v + (float) height) * f1)).endVertex();
		bufferbuilder.pos((double) (x + width), (double) y, 0.0D).tex((double) ((u + (float) width) * f), (double) (v * f1)).endVertex();
		bufferbuilder.pos((double) x, (double) y, 0.0D).tex((double) (u * f), (double) (v * f1)).endVertex();
		tessellator.draw();
	}

	/**
	 * Draws a scaled, textured, tiled modal rect at z = 0. This method isn't
	 * used anywhere in vanilla code.
	 */
	public static void drawScaledCustomSizeModalRect(int x, int y, float u, float v, int uWidth, int vHeight, int width, int height, float tileWidth, float tileHeight) {
		float f = 1.0F / tileWidth;
		float f1 = 1.0F / tileHeight;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder bufferbuilder = tessellator.getBuffer();
		bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
		bufferbuilder.pos((double) x, (double) (y + height), 0.0D).tex((double) (u * f), (double) ((v + (float) vHeight) * f1)).endVertex();
		bufferbuilder.pos((double) (x + width), (double) (y + height), 0.0D).tex((double) ((u + (float) uWidth) * f), (double) ((v + (float) vHeight) * f1)).endVertex();
		bufferbuilder.pos((double) (x + width), (double) y, 0.0D).tex((double) ((u + (float) uWidth) * f), (double) (v * f1)).endVertex();
		bufferbuilder.pos((double) x, (double) y, 0.0D).tex((double) (u * f), (double) (v * f1)).endVertex();
		tessellator.draw();
	}

	protected static void drawLine(int x1, int y1, int x2, int y2, int color) {

		float f3 = (float) (color >> 24 & 255) / 255.0F;
		float f = (float) (color >> 16 & 255) / 255.0F;
		float f1 = (float) (color >> 8 & 255) / 255.0F;
		float f2 = (float) (color & 255) / 255.0F;
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder vertexbuffer = tessellator.getBuffer();
		GlStateManager.enableBlend();
		GlStateManager.disableTexture2D();
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
		GlStateManager.color(f, f1, f2, f3);

		float angle = getAngleRad(new Point(x1,y1), new Point(x2,y2));
		
		double halfPi = Math.PI / 2;
		
		float a1 = (float) (x1 + 0.5F * Math.cos(angle + halfPi)), a2 = (float) (y1 + 0.5F * Math.sin(angle + halfPi));
		float b1 = (float) (x2 + 0.5F * Math.cos(angle + halfPi)), b2 = (float) (y2 + 0.5F * Math.sin(angle + halfPi));
		float c1 = (float) (x2 + 0.5F * Math.cos(angle - halfPi)), c2 = (float) (y2 + 0.5F * Math.sin(angle - halfPi));
		float d1 = (float) (x1 + 0.5F * Math.cos(angle - halfPi)), d2 = (float) (y1 + 0.5F * Math.sin(angle - halfPi));

		
		vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
		//new
		vertexbuffer.pos(a1, a2, 0.0D).endVertex();
		vertexbuffer.pos(b1, b2, 0.0D).endVertex();
		vertexbuffer.pos(c1, c2, 0.0D).endVertex();
		vertexbuffer.pos(d1, d2, 0.0D).endVertex();
		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}
	
	private static float getAngleRad(Point a, Point b){
		Point vec = new Point(b.getX() - a.getX(), b.getY() - a.getY());
		return (float) (Math.acos(vec.getX() / (Math.sqrt(Math.pow(vec.getX(), 2) + Math.pow(vec.getY(), 2)))));
	}
	
	public static void drawCenteredRect(int centerX, int centerY, int radius, double angle, int color){
		double rads = Math.toRadians(angle);
		double diagRadius = radius * Math.sqrt(2);
        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f, f1, f2, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos(centerX + radius * Math.cos(rads + Math.PI * 1.25), centerY + radius * Math.sin(rads + Math.PI * 1.25), 0.0D).endVertex();
        bufferbuilder.pos(centerX + radius * Math.cos(rads + Math.PI * 0.75), centerY + radius * Math.sin(rads + Math.PI * 0.75), 0.0D).endVertex();
        bufferbuilder.pos(centerX + radius * Math.cos(rads + Math.PI * 0.25), centerY + radius * Math.sin(rads + Math.PI * 0.25), 0.0D).endVertex();
        bufferbuilder.pos(centerX + radius * Math.cos(rads + Math.PI * 1.75), centerY + radius * Math.sin(rads + Math.PI * 1.75), 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
	
	/**
	 * @param posX "X" position of the top left corner
	 * @param posY "Y" position of the top left corner
	 * @param widht Size of the rect on the "X" axis
	 * @param height Size of the rect on the "Y" axis
	 * @param u "X" position for the texture, top left corner
	 * @param v "Y" position for the texture, top left corner
	 * @param uMax "X" position for the texture, bottom right corner
	 * @param vMax "Y" position for the texture, bottom right corner
	 */
	public void drawPerctentedRect(int posX, int posY, int width, int height, float u, float v, float uMax, float vMax){
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(posX, posY + height, this.zLevel).tex(u, vMax).endVertex();
        bufferbuilder.pos(posX + width, posY + height, this.zLevel).tex(uMax, vMax).endVertex();
        bufferbuilder.pos(posX + width, posY, this.zLevel).tex(uMax, v).endVertex();
        bufferbuilder.pos(posX, posY, this.zLevel).tex(u, v).endVertex();
        tessellator.draw();
    }
	
	public void drawTexturedCenteredRect(int centerX, int centerY, float u, float v, float uMax, float vMax, int radius, double angle){
		double rads = Math.toRadians(angle);
		double diagRadius = radius * Math.sqrt(2);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(centerX + radius * Math.cos(rads + Math.PI * 1.25), centerY + radius * Math.sin(rads + Math.PI * 1.25), 0.0D).tex(u, vMax).endVertex();
        bufferbuilder.pos(centerX + radius * Math.cos(rads + Math.PI * 0.75), centerY + radius * Math.sin(rads + Math.PI * 0.75), 0.0D).tex(uMax, vMax).endVertex();
        bufferbuilder.pos(centerX + radius * Math.cos(rads + Math.PI * 0.25), centerY + radius * Math.sin(rads + Math.PI * 0.25), 0.0D).tex(uMax, v).endVertex();
        bufferbuilder.pos(centerX + radius * Math.cos(rads + Math.PI * 1.75), centerY + radius * Math.sin(rads + Math.PI * 1.75), 0.0D).tex(u, v).endVertex();
        tessellator.draw();
    }
	
	public static void drawCenteredCircle(int centerX, int centerY, int radius, int resoulution, int color){
		double diagRadius = radius * Math.sqrt(2);
        float f3 = (float)(color >> 24 & 255) / 255.0F;
        float f = (float)(color >> 16 & 255) / 255.0F;
        float f1 = (float)(color >> 8 & 255) / 255.0F;
        float f2 = (float)(color & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color(f, f1, f2, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        double steps = (2D/resoulution);
        double startX = centerX + radius * Math.cos(0);
        double startY = centerY + radius * Math.sin(0);

    	bufferbuilder.pos(startX, startY, 0.0D).endVertex();

    	int i = 1;
    	for(;i < 4; i++){
        	bufferbuilder.pos(centerX + radius * Math.cos(Math.PI * (steps * (resoulution-i))), centerY + radius * Math.sin(Math.PI * (steps * (resoulution-i))), 0.0D).endVertex();
    	}
    	
    	for(; i < resoulution; i += 2){
        	bufferbuilder.pos(startX, startY, 0.0D).endVertex();
    		bufferbuilder.pos(centerX + radius * Math.cos(Math.PI * (steps * (resoulution - i + 1))), centerY + radius * Math.sin(Math.PI * (steps * (resoulution - i + 1))), 0.0D).endVertex();
    		bufferbuilder.pos(centerX + radius * Math.cos(Math.PI * (steps * (resoulution - i))), centerY + radius * Math.sin(Math.PI * (steps * (resoulution - i))), 0.0D).endVertex();
    		bufferbuilder.pos(centerX + radius * Math.cos(Math.PI * (steps * (resoulution - i - 1))), centerY + radius * Math.sin(Math.PI * (steps * (resoulution - i - 1))), 0.0D).endVertex();
    	}
		
        tessellator.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
	
	public enum ALIGNMENT {
		TOP_LEFT(0.0F, 0.0F),
		TOP(0.5F, 0.0F),
		TOP_RIGHT(1.0F, 0.0F),
		RIGHT(1.0F, 0.5F),
		BOTTOM_RIGHT(1.0F, 1.0F),
		BOTTOM(0.5F, 1.0F),
		BOTTOM_LEFT(0.0F, 1.0F),
		LEFT(0.0F, 0.5F),
		CENTER(0.5F, 0.5F);

		float alignmentX;
		float alignmentY;

		private ALIGNMENT(float x, float y) {
			this.alignmentX = x;
			this.alignmentY = y;
		}

		public int getBaseX(int width, int objWidth) {
			int basePos = (int) Math.floor(width * alignmentX);
			return (int) (alignmentX == 0.0F ? basePos
					: alignmentX == 1.0F ? basePos - objWidth : basePos + (0.5F * objWidth));
		}

		public int getBaseY(int height, int objHeight) {
			int basePos = (int) Math.floor(height * alignmentY);
			return (int) (alignmentY == 0.0F ? basePos
					: alignmentY == 1.0F ? basePos - objHeight : basePos + (0.5F * objHeight));
		}
	}

}
