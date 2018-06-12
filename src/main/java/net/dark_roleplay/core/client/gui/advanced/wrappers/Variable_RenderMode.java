package net.dark_roleplay.core.client.gui.advanced.wrappers;

import net.dark_roleplay.core.common.objects.tile_entities.blueprint_controller.TE_BlueprintController;

public class Variable_RenderMode {

	private TE_BlueprintController.RenderMode value;
	
	public Variable_RenderMode(){
		this.value = TE_BlueprintController.RenderMode.BOUNDING_BOX;
	}
	
	public Variable_RenderMode(TE_BlueprintController.RenderMode value){
		this.value = value;
	}
	
	public void set(TE_BlueprintController.RenderMode value){
		this.value = value;
	}
	
	public TE_BlueprintController.RenderMode get(){
		return this.value;
	}
}
