package net.dark_roleplay.drpcore.modules.materials;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.common.eventhandler.Event;

public class AddResourceGenerators extends Event{
	
	List<ResourceGenerator> resourceGenerators = new ArrayList<ResourceGenerator>();
	
	public List<ResourceGenerator> getResourceGenerators(){
		return resourceGenerators;
	}
	
	public void add(ResourceGenerator resGen){
		resourceGenerators.add(resGen);
	}
	
	public void addAll(ResourceGenerator... resGens){
		for(ResourceGenerator resGen : resGens){
			resourceGenerators.add(resGen);
		}
	}
}
