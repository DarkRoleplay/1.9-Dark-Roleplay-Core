package net.dark_roleplay.core.api.old.modules.gui;

import net.dark_roleplay.core.common.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class VerticalScrollBar extends IGuiElement.IMPL{

	private IntegerWrapper currentScroll;
	private boolean mousePressed = false;
	
	private static final ResourceLocation SCROLL_BAR_TEXTURE = new ResourceLocation(References.MODID, "textures/guis/modular_gui/slider_vertical.png");
	private float renderMultiplier = 1F;
	private float scrollMultiplier = 1F;
	
	
	public VerticalScrollBar(int posX, int posY, int width, int height, IntegerWrapper currentScroll){
		this.setPos(posX, posY);
		this.setSize(width, height);
		this.currentScroll = currentScroll;
		this.renderMultiplier = (this.height - 13F)  / ((float)currentScroll.getMax());
		this.scrollMultiplier = ((float)currentScroll.getMax() / (this.height - 13F));

	}
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTick) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(SCROLL_BAR_TEXTURE);
        GlStateManager.color(1F, 1F, 1F);
        
        this.drawTiled(posX, posY + 1, width, height - 2, 0, 1, 7, 62, 12, 64);
		this.drawScaledCustomSizeModalRect(posX, posY, 0, 0, 7, 1, 7, 1, 12, 64);
		this.drawScaledCustomSizeModalRect(posX, posY, 0, 63, 7, 1, 7, 1, 12, 64);

		this.drawScaledCustomSizeModalRect(posX + 1, (int) (posY + (currentScroll.get() * renderMultiplier) + 1), 7, 0, 5, 11, 5, 11, 12, 64);
	}
	 
	@Override
	public void mouseDragged(Minecraft mc, int mouseX, int mouseY){
		if(this.mousePressed){
			this.currentScroll.set((int) ((mouseY - this.posY - 5) * scrollMultiplier));
		}
	}
	
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY){
		if(this.isHovered(mouseX, mouseY) && super.mousePressed(mc, mouseX, mouseY)){
        	this.mousePressed = true;
        	return true;
		}
		return false;
	}
	
	public void mouseReleased(int mouseX, int mouseY){
		this.mousePressed = false;
	}
}
