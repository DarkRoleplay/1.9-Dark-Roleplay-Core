package net.dark_roleplay.core.api.old.modules.gui;

import java.util.List;

import net.dark_roleplay.core.api.old.gui.modular.ModularGui_Drawer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;

public class Panel extends IGuiElement.IMPL{
	
	protected int bgColor = 0x1A000000;
	
	protected int mouseY = -1;
	protected int mouseX = -1;
	
	public Panel(int posX, int posY, int width, int height){
		this(posX, posY, width, height, 0x1A000000);
	}
	
	public Panel(int posX, int posY, int width, int height, int bgColor){
		this.setSize(width, height);
		this.setPos(posX, posY);
		this.bgColor = bgColor;
	}
	
	@Override
	public int addChild(IGuiElement child) {
		try{
			children.add(child);
			return children.size() - 1;
		}catch(Exception e){
			return -1;
		}
	}
	
	@Override
	public void setChild(int id, IGuiElement newChild) {
		this.children.set(id, newChild);
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
	    	    
	    this.drawBackground(mouseX - this.posX, mouseY - this.posY, partialTick);
	    this.drawMiddleground(mouseX - this.posX, mouseY - this.posY, partialTick);
	    this.drawForeground(mouseX - this.posX, mouseY - this.posY, partialTick);

	    GlStateManager.disableDepth();
		GlStateManager.popMatrix();
	}
	
	public void drawBackground(int mouseX, int mouseY, float partialTick){
		
	}
	
	public void drawMiddleground(int mouseX, int mouseY, float partialTick){
		for(IGuiElement child : this.children){
			if(child.isVisible())
				child.draw(mouseX - child.getPosX(), mouseY - child.getPosY(), partialTick);
		}
	}
	
	public void drawForeground(int mouseX, int mouseY, float partialTick){
		
	}
}
