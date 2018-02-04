package net.dark_roleplay.drpcore.modules.materials;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.common.eventhandler.Event;

public class AddMaterials<T extends Material> extends Event{

	protected List<T> materials = new ArrayList<T>();
	
	public void addMaterial(T material){
		this.materials.add(material);
	}
	
	public List<T> getMaterials(){
		return materials;
	}
}
