package net.dark_roleplay.drpcore.common.items;

import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;

public class DRPItem extends Item{

	//Method for 
	private final String modelFolder;
	
	private String[] subModelNames;
	
	public DRPItem(String modelFolder){
		this.modelFolder = modelFolder;
	}
	
	public DRPItem setSubModelNames(String[] subModelNames){
		this.subModelNames = subModelNames;
		return this;
	}
	
	public String[] getSubModelNames(){
		return this.subModelNames;
	}
	
	public String getModelFolder(){
		return this.modelFolder;
	}
}
