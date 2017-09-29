package net.dark_roleplay.drpcore.api.gui.advanced;

import java.io.IOException;

import net.minecraft.client.renderer.GlStateManager;

public class Gui_ScrolledPanel extends Gui_Panel.IMPL{

	private int scrollX;
	private int scrollY;
	
	public Gui_ScrolledPanel(int posX, int posY, int width, int height){
		super(posX, posY, width, height, false);
	}
	
	public Gui_ScrolledPanel(int posX, int posY, int width, int height, boolean isHollow){
		super(posX, posY, width, height, isHollow);
	}
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTick) {
		GlStateManager.translate(scrollX, scrollY, 0);
		super.draw(mouseX, mouseY, partialTick);
		GlStateManager.translate(-scrollX, -scrollY, 0);
		
	}
	
	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		mouseX += scrollX;
		mouseY += scrollY;
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
