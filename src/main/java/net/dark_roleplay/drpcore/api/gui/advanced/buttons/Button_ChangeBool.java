package net.dark_roleplay.drpcore.api.gui.advanced.buttons;

import java.awt.Color;
import java.io.IOException;

import net.dark_roleplay.drpcore.api.gui.advanced.Gui_Button;
import net.dark_roleplay.drpcore.api.gui.utility.wrappers.Variable_Boolean;
import net.dark_roleplay.drpcore.api.gui.utility.wrappers.Variable_Int;

public class Button_ChangeBool extends Gui_Button{

	private Variable_Boolean var;
	private int amount;
	
	public Button_ChangeBool(Variable_Boolean var, int posX, int posY, int width, int height) {
		super(posX, posY, width, height);
		this.var = var;
	}
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTick) {
		this.drawRect(this.posX, this.posY, this.posX + this.width, this.posY + this.height, new Color(255,255,0).getRGB());
	}
	
	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		this.var.set(!this.var.get());
		return true;
	}
}
