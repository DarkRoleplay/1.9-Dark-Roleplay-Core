package net.dark_roleplay.drpcore.api.gui;

import java.awt.Color;
import java.io.IOException;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.inventory.ClickType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.common.MinecraftForge;

public abstract class DRPGuiScreen extends GuiScreen{

	protected ResourceLocation bgTexture;

	protected int bgWidth = 0;
	protected int bgHeight = 0;

	protected int bgOffsetX = 0;
	protected int bgOffsetY = 0;

	protected int guiTop = 0;
	protected int guiLeft = 0;

	protected int startButtonID = 0;
	protected int startInfoFieldID = 50;
	
	protected static final int COLOR_WHITE = new Color(255,255,255).getRGB();
	protected static final int COLOR_DARK_GRAY = new Color(55,55,55).getRGB();
	
	protected GuiButton selectedButton;

	public DRPGuiScreen(ResourceLocation bgTexture, int bgWidth, int bgHeight) {
		this(bgTexture, bgWidth, bgHeight, 0, 0);
	}

	public DRPGuiScreen(ResourceLocation bgTexture, int bgWidth, int bgHeight, int bgOffsetX, int bgOffsetY) {
		this.bgTexture = bgTexture;
		this.bgWidth = bgWidth;
		this.bgHeight = bgHeight;
		this.reAdjust();
	}

	protected void reAdjust() {
		this.guiLeft = ((this.width - this.bgWidth) / 2) + this.bgOffsetX;
		this.guiTop = ((this.height - this.bgHeight) / 2) + this.bgOffsetY;
	}

	public void initGui() {
		super.initGui();
		this.reAdjust();
	}

	// -------------------------------------------------- Drawing
	// --------------------------------------------------

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.reAdjust();

		RenderHelper.enableGUIStandardItemLighting();
		// Background Texture
		this.drawBackground(mouseX, mouseY, partialTicks);

		// Slots & Buttons
		this.drawButtons(mouseX, mouseY, partialTicks);
		this.drawMiddleground(mouseX, mouseY, partialTicks);

		// Hovering Stuff and other
		this.drawForeground(mouseX, mouseY, partialTicks);
	}

	protected void drawBackground(int mouseX, int mouseY, float partialTicks) {
		this.drawBGTexture();
	}

	protected void drawMiddleground(int mouseX, int mouseY, float partialTicks) {
	}

	protected abstract void drawForeground(int mouseX, int mouseY, float partialTicks);

	protected void drawBGTexture() {
		GL11.glDisable(GL11.GL_LIGHTING);
		this.drawDefaultBackground();
		this.mc.renderEngine.bindTexture(this.bgTexture);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, this.bgOffsetX, this.bgOffsetY, this.bgWidth,
				this.bgHeight);
		GL11.glEnable(GL11.GL_LIGHTING);
	}

	protected void drawButtons(int mouseX, int mouseY, float partialTicks) {
		GL11.glDisable(GL11.GL_LIGHTING);
		for (int i = 0; i < this.buttonList.size(); ++i) {
			((GuiButton) this.buttonList.get(i)).drawButton(this.mc, mouseX, mouseY, partialTicks);
		}

		for (int j = 0; j < this.labelList.size(); ++j) {
			((GuiLabel) this.labelList.get(j)).drawLabel(this.mc, mouseX, mouseY);
		}
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	protected boolean isMouseBetween(int mouseX, int mouseY, int posX1, int posY1, int posX2, int posY2){
		return mouseX > posX1 && mouseX < posX2 && mouseY > posY1 && mouseY < posY2;
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
		GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA,
				GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE,
				GlStateManager.DestFactor.ZERO);
		GlStateManager.color(f, f1, f2, f3);

		vertexbuffer.begin(7, DefaultVertexFormats.POSITION);
		vertexbuffer.pos((double) x1 + (y1 < y2 ? -0.5D : 0.5D), (double) y1 + (x1 < x2 ? +0.5D : -0.5D), 0.0D)
				.endVertex();
		vertexbuffer.pos((double) x2 + (y1 < y2 ? -0.5D : 0.5D), (double) y2 + (x1 < x2 ? +0.5D : -0.5D), 0.0D)
				.endVertex();
		vertexbuffer.pos((double) x2 + (y1 > y2 ? -0.5D : 0.5D), (double) y2 + (x1 > x2 ? +0.5D : -0.5D), 0.0D)
				.endVertex();
		vertexbuffer.pos((double) x1 + (y1 > y2 ? -0.5D : 0.5D), (double) y1 + (x1 > x2 ? +0.5D : -0.5D), 0.0D)
				.endVertex();

		tessellator.draw();
		GlStateManager.enableTexture2D();
		GlStateManager.disableBlend();
	}
}
