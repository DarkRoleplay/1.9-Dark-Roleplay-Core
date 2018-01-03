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
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton){
		this.var.set(!this.var.get());
		this.setText(String.valueOf(this.var.get()).toUpperCase());
		return true;
	}
}
