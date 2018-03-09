package net.dark_roleplay.drpcore.api.old.gui.hud;

import net.dark_roleplay.drpcore.modules.gui.IGuiElement;

public class GuiHud extends IGuiElement.IMPL{

	@Override
	public void draw(int mouseX, int mouseY, float partialTicks) {
		
	}
	
	public static enum ALIGNMENT{
		CENTER,
		TOP_LEFT,
		TOP,
		TOP_RIGHT,
		RIGHT,
		BOTTOM_RIGHT,
		BOTTOM,
		BOTTOM_LEFT,
		LEFT
	}
}
