package net.dark_roleplay.core.api.old.items;

public class DRPBackpack extends DRPEquip{

	public DRPBackpack(String name){
		this(name, null);
	}
	
	public DRPBackpack(String name, String modelFolder){
		super(name, modelFolder, TYPE.TYPE_GENERAL_STORAGE);
	}
	
	
}
