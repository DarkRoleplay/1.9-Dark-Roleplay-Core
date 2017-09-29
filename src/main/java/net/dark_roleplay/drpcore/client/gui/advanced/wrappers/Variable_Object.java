package net.dark_roleplay.drpcore.client.gui.advanced.wrappers;

import net.dark_roleplay.drpcore.api.skills.Skill;

public class Variable_Object<T extends Object> {

	private T value;
	
	public Variable_Object(){}
	
	public Variable_Object(T value){
		this.value = value;
	}
	
	public void set(T value){
		this.value = value;
	}
	
	public T get(){
		return this.value;
	}
	
}
