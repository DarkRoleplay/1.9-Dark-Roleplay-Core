package net.dark_roleplay.core.api.old.modules;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class Module {

	private static List<Module> modules = new ArrayList<Module>();
	
	private String name;
	
	private Module[] required;
	
	private boolean enabled = false;
	
	public Module(String name, Module... requiredModules){
		this.name = name;
		this.required = requiredModules;
		modules.add(this);
	}
	
	public void enable(){
		for(Module module : required){
			module.enable();
		}
		this.enabled = true;
	}
	
	public boolean isEnabled(){
		return enabled;
	}	
	
	public void preInit(FMLPreInitializationEvent event){}
	
	public void init(FMLInitializationEvent event){}
	
	public void postInit(FMLPostInitializationEvent event){}
	
	public String getName(){
		return this.name;
	}
	
	public static List<Module> getModules(){
		return modules;
	}
}
