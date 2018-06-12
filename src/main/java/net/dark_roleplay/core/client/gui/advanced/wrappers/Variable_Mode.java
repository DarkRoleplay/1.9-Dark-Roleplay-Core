package net.dark_roleplay.core.client.gui.advanced.wrappers;

import net.dark_roleplay.core.common.objects.tile_entities.blueprint_controller.TE_BlueprintController;

public class Variable_Mode {

	private TE_BlueprintController.Mode value;
	
	public Variable_Mode(){
		this.value = TE_BlueprintController.Mode.SAVE;
	}
	
	public Variable_Mode(TE_BlueprintController.Mode value){
		this.value = value;
	}
	
	public void set(TE_BlueprintController.Mode value){
		this.value = value;
	}
	
	public TE_BlueprintController.Mode get(){
		return this.value;
	}
}
