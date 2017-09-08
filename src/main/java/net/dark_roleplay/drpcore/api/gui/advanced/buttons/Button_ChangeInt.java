package net.dark_roleplay.drpcore.api.gui.advanced.buttons;

import java.awt.Color;
import java.io.IOException;

import net.dark_roleplay.drpcore.api.gui.advanced.Gui_Button;
import net.dark_roleplay.drpcore.api.gui.utility.wrappers.Variable_Int;

public class Button_ChangeInt extends Gui_Button{

	private Variable_Int var;
	private int amount;
	
	public Button_ChangeInt(Variable_Int var, int amount, int posX, int posY, int width, int height) {
		super(posX, posY, width, height);
		this.var = var;
		this.amount = amount;
	}
	
	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		this.var.add(amount);
		return true;
	}
}
