package net.dark_roleplay.drpcore.api.items;

import net.dark_roleplay.drpcore.api.items.DRPEquip.DRPEquip_TYPE;

public class DRPBackpack extends DRPEquip{

	public DRPBackpack(String name){
		this(name, null);
	}
	
	public DRPBackpack(String name, String modelFolder){
		super(name, modelFolder, DRPEquip_TYPE.TYPE_GENERAL_STORAGE);
	}
	
	
}
