package net.dark_roleplay.drpcore.client.gui.advanced.buttons.blueprint_controll;

import java.awt.Color;
import java.io.IOException;

import net.dark_roleplay.drpcore.api.gui.advanced.Gui_Button;
import net.dark_roleplay.drpcore.api.gui.utility.wrappers.Variable_Boolean;
import net.dark_roleplay.drpcore.client.gui.advanced.wrappers.Variable_Mode;
import net.dark_roleplay.drpcore.common.tile_entities.blueprint_controller.TE_BlueprintController;
import net.minecraft.client.resources.I18n;

public class Button_ChangeMode extends Gui_Button{

	private Variable_Mode var;
	private int amount;
	
	public Button_ChangeMode(Variable_Mode var, int posX, int posY, int width, int height) {
		super(posX, posY, width, height);
		this.var = var;
		this.setText(I18n.format("gui.structure.mode." + this.var.get().getName()));
	}
	
	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		switch(this.var.get()){
			case CORNER:
				this.var.set(TE_BlueprintController.Mode.SAVE);
				break;
			case LOAD:
				this.var.set(TE_BlueprintController.Mode.CORNER);
				break;
			case SAVE:
				this.var.set(TE_BlueprintController.Mode.LOAD);
				break;
			default:
				this.var.set(TE_BlueprintController.Mode.LOAD);
				break;
		}
		this.setText(I18n.format("gui.structure.mode." + this.var.get().getName()));
		return true;
	}
}
