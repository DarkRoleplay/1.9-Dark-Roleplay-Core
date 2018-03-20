package net.dark_roleplay.drpcore.api.old.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;

import net.dark_roleplay.drpcore.api.old.modules.gui.IGuiElement;
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

	protected boolean hasBG = true;
	
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
	
	protected List<IGuiElement> elements = new ArrayList<IGuiElement>();

	protected GuiButton selectedButton;

	public DRPGuiScreen(){
		this.hasBG = false;
	}
	
	public DRPGuiScreen(ResourceLocation bgTexture, int bgWidth, int bgHeight) {
		this(bgTexture, bgWidth, bgHeight, 0, 0);
	}

	public DRPGuiScreen(ResourceLocation bgTexture, int bgWidth, int bgHeight, int bgOffsetX, int bgOffsetY) {
		this.bgTexture = bgTexture;
		this.bgWidth = bgWidth;
		this.bgHeight = bgHeight;
	}

	protected void reAdjust() {
		this.guiLeft = ((this.width - this.bgWidth) / 2) + this.bgOffsetX;
		this.guiTop = ((this.height - this.bgHeight) / 2) + this.bgOffsetY;
	}

	public void initGui() {
		super.initGui();
		this.reAdjust();
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state){
		super.mouseReleased(mouseX, mouseY, state);
		for(IGuiElement element : elements){
			element.mouseReleased(mouseX - element.getPosX(), mouseY - element.getPosY());
		}
	}
	
	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
		super.mouseClicked(mouseX, mouseY, mouseButton);
		for(IGuiElement element : elements){
			if(element.isHovered(mouseX - element.getPosX(), mouseY - element.getPosY()) ){
				element.mousePressed(this.mc, mouseX - element.getPosX(), mouseY - element.getPosY());
				element.mouseClicked(mouseX - element.getPosX(), mouseY - element.getPosY(), mouseButton);
			}
		}
	}

	@Override
	public void handleMouseInput() throws IOException{
		super.handleMouseInput();
		for(IGuiElement element : elements){
			element.handleMouseInput();
		}
    }
	
	// -------------------------------------------------- Drawing --------------------------------------------------

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.reAdjust();

		RenderHelper.enableGUIStandardItemLighting();
		// Background Texture
		this.drawBackground(mouseX, mouseY, partialTicks);

		for(IGuiElement element : elements){
			element.mouseDragged(this.mc, mouseX - element.getPosX(), mouseY - element.getPosY());
		}
		// Slots & Buttons
		this.drawButtons(mouseX, mouseY, partialTicks);
		this.drawMiddleground(mouseX, mouseY, partialTicks);

		// Hovering Stuff and other
		this.drawForeground(mouseX, mouseY, partialTicks);
	}

	protected void drawBackground(int mouseX, int mouseY, float partialTicks) {
		if(this.hasBG)
			this.drawBGTexture();
		else
			this.drawDefaultBackground();
	}

	protected void drawMiddleground(int mouseX, int mouseY, float partialTicks) {
		for(IGuiElement element : elements){
			element.draw(mouseX, mouseY, partialTicks);
		}
	}

	protected abstract void drawForeground(int mouseX, int mouseY, float partialTicks);

	protected void drawBGTexture() {
		GL11.glDisable(GL11.GL_LIGHTING);
		this.drawDefaultBackground();
		this.mc.renderEngine.bindTexture(this.bgTexture);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, this.bgOffsetX, this.bgOffsetY, this.bgWidth,this.bgHeight);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	protected void drawTiled(int posX, int posY, int width, int height, int u, int v, int tileWidth, int tileHeight, int textureWidth, int textureHeight){
		if(height > tileHeight)
			for(int i = 0; i < height; i += tileHeight){
				this.drawScaledCustomSizeModalRect(posX, posY + i, u, v, tileWidth, height - i >= tileHeight ? tileHeight : height - i, tileWidth, height - i >= tileHeight ? tileHeight : height - i, textureWidth, textureHeight);
			}
		if(width > tileWidth)
			for(int i = 0; i < width; i += tileWidth){
				this.drawScaledCustomSizeModalRect(posX + i, posY, u, v,  width - i >= tileWidth ? tileWidth : width - i, tileHeight, width - i >= tileWidth ? tileWidth : width - i, tileHeight, textureWidth, textureHeight);
			}
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
	
	private static float getAngle(Point a, Point b){
		return (float) (getAngleRad(a, b) * 180 / Math.PI);
	}
}
