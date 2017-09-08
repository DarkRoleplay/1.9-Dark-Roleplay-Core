package net.dark_roleplay.drpcore.api.gui.utility.wrappers;

public class Variable_String {

	private String value;
	
	public Variable_String(){
		this.value = "";
	}
	
	public Variable_String(String value){
		this.value = value;
	}
	
	public void set(String value){
		this.value = value;
	}
	
	public String get(){
		return this.value;
	}
}
