package net.dark_roleplay.drpcore.api.gui.advanced;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

public class Gui_Button extends IGuiElement.IMPL{

	protected boolean enabled = true;
	
	public Gui_Button(int posX, int posY, int width, int height){
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTick) {
		this.drawRect(this.posX, this.posY, this.posX + this.width, this.posY + this.height, new Color(0,0,0).getRGB());
	}

	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		return true;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
}
