package net.dark_roleplay.core.client.gui.advanced.wrappers;

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
