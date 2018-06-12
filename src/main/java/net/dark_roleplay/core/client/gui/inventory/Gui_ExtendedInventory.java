package net.dark_roleplay.core.client.gui.inventory;

import org.lwjgl.opengl.GL11;

import net.dark_roleplay.core.common.References;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class Gui_ExtendedInventory extends GuiContainer{

	protected ResourceLocation bgTexture = new ResourceLocation(References.MODID, "textures/guis/extended/extended_inventory.png");

	protected int bgWidth = 174;
	protected int bgHeight = 162;

	protected int bgOffsetX = 0;
	protected int bgOffsetY = 0;

	protected int guiTop = 0;
	protected int guiLeft = 0;
	
	public Gui_ExtendedInventory(Container inventorySlotsIn) {
		super(inventorySlotsIn);
	}

	public void initGui() {
		super.initGui();
		this.reAdjust();
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		this.reAdjust();
		this.drawBGTexture();


	}
	
	protected void drawBGTexture() {
		GL11.glDisable(GL11.GL_LIGHTING);
		this.drawDefaultBackground();
		this.mc.renderEngine.bindTexture(this.bgTexture);
		this.drawTexturedModalRect(this.guiLeft, this.guiTop, this.bgOffsetX, this.bgOffsetY, this.bgWidth,
				this.bgHeight);
		GL11.glEnable(GL11.GL_LIGHTING);
	}
	
	protected void reAdjust() {
		this.guiLeft = ((this.width - this.bgWidth) / 2) + this.bgOffsetX;
		this.guiTop = ((this.height - this.bgHeight) / 2) + this.bgOffsetY;
	}
}
