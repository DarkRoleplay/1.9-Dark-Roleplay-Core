package net.dark_roleplay.core.client.gui.advanced.buttons.blueprint_controll;

import java.io.IOException;

import net.dark_roleplay.core.api.old.gui.advanced.Gui_Button;
import net.dark_roleplay.core.client.gui.advanced.wrappers.Variable_Mode;
import net.dark_roleplay.core.client.gui.advanced.wrappers.Variable_RenderMode;
import net.dark_roleplay.core.common.objects.tile_entities.blueprint_controller.TE_BlueprintController;
import net.minecraft.client.resources.I18n;

public class Button_ChangeRenderMode extends Gui_Button{

	private Variable_RenderMode var;
	private int amount;
	
	public Button_ChangeRenderMode(Variable_RenderMode var, int posX, int posY, int width, int height) {
		super(posX, posY, width, height);
		this.var = var;
		this.setText(I18n.format("gui.structure.render_mode." + this.var.get().getName()));
	}
	
	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton){
		switch(this.var.get()){
			case NONE:
				this.var.set(TE_BlueprintController.RenderMode.BOUNDING_BOX);
				break;
			case BOUNDING_BOX:
				this.var.set(TE_BlueprintController.RenderMode.INVISIBLE);
				break;
			case INVISIBLE:
				this.var.set(TE_BlueprintController.RenderMode.NONE);
				break;
			default:
				this.var.set(TE_BlueprintController.RenderMode.BOUNDING_BOX);
				break;
		}
		this.setText(I18n.format("gui.structure.render_mode." + this.var.get().getName()));
		return true;
	}
}
