package net.dark_roleplay.drpcore.api.gui.advanced;

import java.io.IOException;

import net.dark_roleplay.drpcore.api.gui.modular.ModularGui_Drawer;
import net.dark_roleplay.drpcore.api.gui.utility.wrappers.Variable_Int;
import net.dark_roleplay.drpcore.modules.gui.IGuiElement;
import net.minecraft.client.renderer.GlStateManager;

public class Gui_ScrolledPanel extends Gui_Panel.IMPL{

	private Variable_Int scrollX = new Variable_Int(0);
	private Variable_Int scrollY = new Variable_Int(0);
	private Gui_Scrollbar scrollBarX;
	private Gui_Scrollbar scrollBarY;
	
	public Gui_ScrolledPanel(int posX, int posY, int width, int height){
		super(posX, posY, width, height, false);

	}
	
	public Gui_ScrolledPanel(int posX, int posY, int width, int height, boolean isHollow){
		super(posX, posY, width, height, isHollow);
		scrollBarX = new Gui_Scrollbar(scrollX, 0, this.width * 2, true);
		scrollBarX.setSize(width, 10);
		scrollBarX.setPos(0, this.height - 10);
	}
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTick) {
		GlStateManager.pushMatrix();
        GlStateManager.translate((float)(this.posX), (float)(this.posY), -400.0F);
        GlStateManager.enableDepth();

		GlStateManager.depthFunc(518);
	    drawRect(0, 0, width, height, -16777216);
	    GlStateManager.depthFunc(515);
		
	    GlStateManager.color(1F, 1F, 1F);
	    
	    ModularGui_Drawer.drawBackgroundCenter(0, 0, this.width, this.height, isHollow);
	    
	    scrollBarX.draw(mouseX - scrollBarX.getPosX(), mouseY - scrollBarX.getPosY(), partialTick);
		GlStateManager.translate(scrollX.get(), scrollY.get(), 0);
	    this.drawBackground(mouseX - this.posX, mouseY - this.posY, partialTick);
	    this.drawMiddleground(mouseX - this.posX, mouseY - this.posY, partialTick);
	    this.drawForeground(mouseX - this.posX, mouseY - this.posY, partialTick);
		GlStateManager.translate(-scrollX.get(), -scrollY.get(), 0);
	    
		GlStateManager.popMatrix();
	    GlStateManager.depthFunc(515);
	    GlStateManager.disableDepth();
	}
	
	@Override
	public IGuiElement setSize(int width, int height) {
		this.width = width;
		this.height = height;
		return this;
	}
	
	public void drawMiddleground(int mouseX, int mouseY, float partialTick){
		for(IGuiElement child : this.children){
			if(child.isVisible())
				child.draw(mouseX - child.getPosX(), mouseY - child.getPosY(), partialTick);
		}
	}
	
	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton){
		if(this.children != null){
			for(IGuiElement element : this.children){
				if(element.isVisible() && (mouseX > element.getPosX() && mouseX < element.getPosX() + element.getWidth()) && (mouseY > element.getPosY() && mouseY < element.getPosY() + element.getHeight())){
					if(element.mouseClicked(mouseX - element.getPosX(), mouseY - element.getPosY(), mouseButton)){
						return true;
					}
				}
			}
		}
		return false;
	}
}
