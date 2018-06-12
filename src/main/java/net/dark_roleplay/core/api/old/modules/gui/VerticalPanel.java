package net.dark_roleplay.core.api.old.modules.gui;

import java.io.IOException;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class VerticalPanel extends Panel{

	protected IntegerWrapper scrollAmount;
	
	protected VerticalScrollBar scrollBar;
	
	protected float scrollMultiplier;
	
	public VerticalPanel(int posX, int posY, int width, int height, int scrollHeight) {
		super(posX, posY, width, height);
		scrollAmount = new IntegerWrapper(0, 0, scrollHeight);
		scrollBar = new VerticalScrollBar(width - 7, 0, 7, height, scrollAmount);
		scrollMultiplier = -(((float) scrollAmount.getMax()) / ((float)height)) * 4;
	}
	
	

	@Override
	public void draw(int mouseX, int mouseY, float partialTick) {
		this.mouseY = mouseY - this.posX;
		this.mouseX = mouseX - this.posX;
		GlStateManager.pushMatrix();
        GlStateManager.translate((float)(this.posX), (float)(this.posY), -400.0F);

        GlStateManager.enableDepth();
		GlStateManager.depthFunc(518);
	    drawRect(0, 0, width, height, this.bgColor);
	    GlStateManager.depthFunc(515);
	    
	    GlStateManager.color(1F, 1F, 1F);
	    
	    scrollBar.draw(mouseX - scrollBar.getPosX(), mouseY - scrollBar.getPosY(), partialTick);
	    
	    GlStateManager.translate(0, -scrollAmount.get(), 0);
	    	    
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
			element.mouseDragged(mc, mouseX, mouseY + scrollAmount.get());
		}
	}
	
	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton){
		if(scrollBar.isHovered(mouseX - scrollBar.getPosX(), mouseY - scrollBar.getPosY())){
			scrollBar.mouseClicked(mouseX - scrollBar.getPosX(), mouseY - scrollBar.getPosY(), mouseButton);
		}
		if(this.children != null){
			for(IGuiElement element : this.children){
				if(element.isVisible() && (mouseX > element.getPosX() && mouseX < element.getPosX() + element.getWidth()) && (mouseY + scrollAmount.get() > element.getPosY() && mouseY + scrollAmount.get() < element.getPosY() + element.getHeight())){
					if(element.mouseClicked(mouseX - element.getPosX(), mouseY + scrollAmount.get() - element.getPosY(), mouseButton)){
						return true;
					}
				}
			}
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
	
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY){
		if(scrollBar.isHovered(mouseX - scrollBar.getPosX(), mouseY - scrollBar.getPosY())){
			scrollBar.mousePressed(mc, mouseX - scrollBar.getPosX(), mouseY - scrollBar.getPosY());
		}
		if(this.enabled && this.visible && mouseX >= 0 && mouseY >= 0 && mouseX < this.width && mouseY < this.height){
			if(this.children != null){
				for(IGuiElement element : this.children){
					if(element.isVisible() && (mouseX > element.getPosX() && mouseX < element.getPosX() + element.getWidth()) && (mouseY + scrollAmount.get() > element.getPosY() && mouseY + scrollAmount.get()< element.getPosY() + element.getHeight())){
						if(element.mousePressed(mc, mouseX - element.getPosX(), mouseY + scrollAmount.get() - element.getPosY())){
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
	public void drawMiddleground(int mouseX, int mouseY, float partialTick){
		for(IGuiElement child : this.children){
			if(child.isVisible() && 
					(child.getPosX() + child.getWidth() > 0 && child.getPosY() + child.getHeight() > scrollAmount.get()) &&
					(child.getPosX() < this.width && child.getPosY() < this.height + scrollAmount.get()) ){
				child.draw(mouseX - child.getPosX(), mouseY - child.getPosY() + scrollAmount.get(), partialTick);
			}
		}
	}
	
	public void mouseReleased(int mouseX, int mouseY){
		scrollBar.mouseReleased(mouseX - scrollBar.getPosX(), mouseY - scrollBar.getPosY());
		for(IGuiElement element : this.children){
			element.mouseReleased(mouseX, mouseY + scrollAmount.get());
		}
	}
}
