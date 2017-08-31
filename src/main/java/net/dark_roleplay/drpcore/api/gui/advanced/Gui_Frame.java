package net.dark_roleplay.drpcore.api.gui.advanced;

import java.util.List;

public class Gui_Frame extends IGuiElement.IMPL{

	private Gui_Panel mainPanel;
	
	public Gui_Frame(int posX, int posY, int width, int height){
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
		this.mainPanel = new Gui_Panel.IMPL(height, height, height, height);
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
		// TODO Auto-generated method stub
		
	}

}
