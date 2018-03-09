package net.dark_roleplay.drpcore.client.gui.premium;

import net.dark_roleplay.drpcore.api.old.gui.advanced.Gui_Frame;
import net.dark_roleplay.drpcore.api.old.gui.advanced.Gui_Panel;
import net.dark_roleplay.drpcore.api.old.gui.advanced.Gui_Screen;
import net.dark_roleplay.drpcore.api.old.gui.advanced.Gui_ScrolledPanel;
import net.minecraft.client.resources.I18n;

public class Gui_Shop extends Gui_Screen{

	private Gui_Frame mainFrame;
	private Gui_ScrolledPanel itemList;
	private Gui_Panel preview;
	
	private boolean initialized = false;

	public void initGui(){
		if(!initialized){
			this.mainFrame = new Gui_Frame(this, 0, 0, 300, 200, false);
			
			this.itemList = new Gui_ScrolledPanel(0, 23, 143, 165, true);
			this.mainFrame.addChild(this.itemList);
			
			this.preview = new Gui_Panel.IMPL(145, 23, 143, 165, true);
			this.mainFrame.addChild(this.preview);
			
			this.addElement(mainFrame);
			
			this.initialized = true;
		}	
		
		this.mainFrame.setPos((this.width - this.mainFrame.getWidth()) / 2, (this.height -  this.mainFrame.getHeight()) / 2);
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		this.drawGradientRect(0, 0, this.width, this.height, -1072689136, -804253680);
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
}
