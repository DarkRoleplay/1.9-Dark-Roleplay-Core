package net.dark_roleplay.drpcore.modules.gui;

import java.io.IOException;

import org.lwjgl.input.Mouse;

import net.dark_roleplay.drpcore.api.old.gui.modular.ModularGui_Drawer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;

public class HorizontalPanel extends Panel{

	protected IntegerWrapper scrollAmount;
	
	protected HorizontalScrollBar scrollBar;

	private float scrollMultiplier;
	
	public HorizontalPanel(int posX, int posY, int width, int height, int scrollWidth) {
		super(posX, posY, width, height);
		scrollAmount = new IntegerWrapper(0, 0, scrollWidth);
		scrollBar = new HorizontalScrollBar(0, height -7, width, 7, scrollAmount);
		scrollMultiplier = (scrollAmount.getMax() / (this.width));
	}

	@Override
	public void draw(int mouseX, int mouseY, float partialTick) {
		this.mouseY = mouseY - this.posX;
		this.mouseX = mouseX - this.posX;
		GlStateManager.pushMatrix();
        GlStateManager.translate((float)(this.posX), (float)(this.posY), -400.0F);

        GlStateManager.enableDepth();
		GlStateManager.depthFunc(518);
	    drawRect(0, 0, width, height, bgColor);
	    GlStateManager.depthFunc(515);
	    
	    GlStateManager.color(1F, 1F, 1F);
	    
	    scrollBar.draw(mouseX - scrollBar.getPosX(), mouseY - scrollBar.getPosY(), partialTick);
	    
	    GlStateManager.translate(-scrollAmount.get(), 0, 0);
	    	    
	    this.drawBackground(mouseX - this.posX, mouseY - this.posY, partialTick);
	    this.drawMiddleground(mouseX - this.posX, mouseY - this.posY, partialTick);
	    this.drawForeground(mouseX - this.posX, mouseY - this.posY, partialTick);
	    

	    GlStateManager.disableDepth();
		GlStateManager.popMatrix();
	}
	
	@Override
	public void mouseDragged(Minecraft mc, int mouseX, int mouseY){
		scrollBar.mouseDragged(mc, mouseX - scrollBar.getPosX(), mouseY - scrollBar.getPosY());
		for(IGuiElement element : this.children){
			element.mouseDragged(mc, mouseX + scrollAmount.get(), mouseY + scrollAmount.get());
		}
	}
	
	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton){
		if(scrollBar.isHovered(mouseX - scrollBar.getPosX(), mouseY - scrollBar.getPosY())){
			scrollBar.mouseClicked(mouseX - scrollBar.getPosX(), mouseY - scrollBar.getPosY(), mouseButton);
		}
		if(this.children != null){
			for(IGuiElement element : this.children){
				if(element.isVisible() && (mouseX + scrollAmount.get() > element.getPosX() && mouseX + scrollAmount.get() < element.getPosX() + element.getWidth()) && (mouseY > element.getPosY() && mouseY < element.getPosY() + element.getHeight())){
					if(element.mouseClicked(mouseX + scrollAmount.get() - element.getPosX(), mouseY - element.getPosY(), mouseButton)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY){
		if(scrollBar.isHovered(mouseX - scrollBar.getPosX(), mouseY - scrollBar.getPosY())){
			scrollBar.mousePressed(mc, mouseX - scrollBar.getPosX(), mouseY - scrollBar.getPosY());
		}
		if(this.enabled && this.visible && mouseX >= 0 && mouseY >= 0 && mouseX < this.width && mouseY < this.height){
			if(this.children != null){
				for(IGuiElement element : this.children){
					if(element.isVisible() && (mouseX + scrollAmount.get() > element.getPosX() && mouseX + scrollAmount.get() < element.getPosX() + element.getWidth()) && (mouseY > element.getPosY() && mouseY < element.getPosY() + element.getHeight())){
						if(element.mousePressed(mc, mouseX + scrollAmount.get() - element.getPosX(), mouseY + scrollAmount.get() - element.getPosY())){
							return true;
						}
					}
				}
			}
			return true;
		}
		return false;
	}
	
	@Override
	public void handleMouseInput() throws IOException{
		if(this.isHovered(this.mouseX, this.mouseY)){
			int scrollAmount = Mouse.getEventDWheel();
	
			if(scrollAmount > 0){
				scrollAmount = 1;
			}else if(scrollAmount < 0){
				scrollAmount = -1;
			}
		
			this.scrollAmount.set((int) (this.scrollAmount.get() + (scrollAmount * scrollMultiplier)));
		}
    }
	
	public void mouseReleased(int mouseX, int mouseY){
		scrollBar.mouseReleased(mouseX - scrollBar.getPosX(), mouseY - scrollBar.getPosY());
		for(IGuiElement element : this.children){
			element.mouseReleased(mouseX + scrollAmount.get(), mouseY);
		}
	}
	
	@Override
	public void drawMiddleground(int mouseX, int mouseY, float partialTick){
		for(IGuiElement child : this.children){
			if(child.isVisible() && 
					(child.getPosX() + child.getWidth() > scrollAmount.get() && child.getPosY() + child.getHeight() > 0) &&
					(child.getPosX() < this.width + scrollAmount.get() && child.getPosY() < this.height) ){
				child.draw(mouseX - child.getPosX() + scrollAmount.get(), mouseY - child.getPosY(), partialTick);
			}
		}
	}
}
