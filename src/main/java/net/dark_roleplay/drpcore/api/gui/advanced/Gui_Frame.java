package net.dark_roleplay.drpcore.api.gui.advanced;

import java.io.IOException;
import java.util.List;

import net.dark_roleplay.drpcore.api.gui.modular.ModularGui_Drawer;

public class Gui_Frame extends IGuiElement.IMPL{

	private Gui_Panel mainPanel;
	private Gui_Screen parent;
	
	public Gui_Frame(Gui_Screen parent, int posX, int posY, int width, int height){
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.mainPanel = new Gui_Panel.IMPL(posX + 5, posY + 5, width - 10, height - 10);
		this.parent = parent;
	}
	
	@Override
	public int addChild(IGuiElement child) {
		return this.mainPanel.addChild(child);
	}

	@Override
	public IGuiElement getChild(int id) {
		return this.mainPanel.getChild(id);
	}

	@Override
	public List<IGuiElement> getChildren() {
		return this.mainPanel.getChildren();
	}
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTick) {
		ModularGui_Drawer.drawBackground(this.posX, this.posY, this.width, this.height);
		this.mainPanel.draw(mouseX, mouseY, partialTick);
		for(IGuiElement child : this.children){
			if(child.isVisible())
				child.draw(mouseX, mouseY, partialTick);
		}
	}
	
	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		return this.mainPanel.mouseClicked(mouseX - 5, mouseY - 5, mouseButton);
	}
	
	@Override
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		this.mainPanel.setSize(width - 10, height - 10);
	}
	
	@Override
	public void setPos(int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
		this.mainPanel.setPos(posX + 5, posY + 5);
	}
}
