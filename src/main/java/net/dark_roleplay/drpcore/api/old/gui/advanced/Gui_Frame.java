package net.dark_roleplay.drpcore.api.old.gui.advanced;

import java.io.IOException;
import java.util.List;

import net.dark_roleplay.drpcore.api.old.gui.modular.ModularGui_Drawer;
import net.dark_roleplay.drpcore.client.gui.ModularGuiHandler;
import net.dark_roleplay.drpcore.modules.gui.IGuiElement;

public class Gui_Frame extends IGuiElement.IMPL{

	private Gui_Panel mainPanel;
	private Gui_Screen parent;
	
	private boolean isHollow;
	
	public Gui_Frame(Gui_Screen parent, int posX, int posY, int width, int height){
		this(parent, posX, posY, width, height, false);
	}
	
	public Gui_Frame(Gui_Screen parent, int posX, int posY, int width, int height, boolean isHollow){
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		int left = ModularGuiHandler.currentGui.getModularBG().getLeft();
		int top = ModularGuiHandler.currentGui.getModularBG().getTop();
		this.mainPanel = new Gui_Panel.IMPL(posX + left, posY + top, width - left * 2, height - top * 2, isHollow);
		this.parent = parent;
		this.isHollow = isHollow;
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
	public void setChild(int id, IGuiElement newChild) {
		this.mainPanel.setChild(id, newChild);
	}

	@Override
	public List<IGuiElement> getChildren() {
		return this.mainPanel.getChildren();
	}
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTick) {
		ModularGui_Drawer.drawBackground(this.posX, this.posY, this.width, this.height, false, isHollow);
		this.mainPanel.draw(mouseX, mouseY, partialTick);
		for(IGuiElement child : this.children){
			if(child.isVisible())
				child.draw(mouseX, mouseY, partialTick);
		}
	}
	
	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton){
		return this.mainPanel.mouseClicked(mouseX - 5, mouseY - 5, mouseButton);
	}
	
	@Override
	public IGuiElement setSize(int width, int height) {
		int left = ModularGuiHandler.currentGui.getModularBG().getLeft();
		int top = ModularGuiHandler.currentGui.getModularBG().getTop();
		this.width = width;
		this.height = height;
		this.mainPanel.setSize(width - left * 2, height - top * 2);
		return this;
	}
	
	@Override
	public void setPos(int posX, int posY) {
		int left = ModularGuiHandler.currentGui.getModularBG().getLeft();
		int top = ModularGuiHandler.currentGui.getModularBG().getTop();
		this.posX = posX;
		this.posY = posY;
		this.mainPanel.setPos(posX + left, posY + top);
	}
}
