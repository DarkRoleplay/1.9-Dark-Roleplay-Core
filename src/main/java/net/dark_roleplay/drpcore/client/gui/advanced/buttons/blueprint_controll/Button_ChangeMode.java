package net.dark_roleplay.drpcore.client.gui.advanced.buttons.blueprint_controll;

import java.awt.Color;
import java.io.IOException;

import net.dark_roleplay.drpcore.api.gui.advanced.Gui_Button;
import net.dark_roleplay.drpcore.api.gui.utility.wrappers.Variable_Boolean;
import net.dark_roleplay.drpcore.common.tile_entities.blueprint_controller.TE_BlueprintController;

public class Button_ChangeMode extends Gui_Button{

	private TE_BlueprintController.Mode var;
	private int amount;
	
	public Button_ChangeMode(TE_BlueprintController.Mode var, int posX, int posY, int width, int height) {
		super(posX, posY, width, height);
		this.var = var;
	}
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTick) {
		this.drawRect(this.posX, this.posY, this.posX + this.width, this.posY + this.height, new Color(255,255,0).getRGB());
	}
	
	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		switch(this.var){
			case CORNER:
				this.var = TE_BlueprintController.Mode.LOAD;
				break;
			case LOAD:
				this.var = TE_BlueprintController.Mode.SAVE;
				break;
			case SAVE:
				this.var = TE_BlueprintController.Mode.LOAD;//TODO ADD CORNER MODE
				break;
			default:
				this.var = TE_BlueprintController.Mode.SAVE;
				break;
		}
		return true;
	}
}
