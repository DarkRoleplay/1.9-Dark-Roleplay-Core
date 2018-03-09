package net.dark_roleplay.drpcore.api.old.gui.utility.wrappers;

public class Variable_Boolean{

	private boolean value;
	
	public Variable_Boolean(){
		this.value = false;
	}
	
	public Variable_Boolean(boolean value){
		this.value = value;
	}
	
	public void set(boolean value){
		this.value = value;
	}
	
	public boolean get(){
		return this.value;
	}
}
