package net.dark_roleplay.drpcore.api.gui.utility;

import java.io.IOException;
import java.util.List;

import net.dark_roleplay.drpcore.api.gui.advanced.IGuiElement;

public class DRPButton extends IGuiElement.IMPL{

	protected boolean enabled = true;
	
	public DRPButton(int posX, int posY, int width, int height){
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTick) {
		
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
