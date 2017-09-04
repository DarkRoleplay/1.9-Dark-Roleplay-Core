package net.dark_roleplay.drpcore.api.gui.utility.wrappers;

public class Variable_Int {

	private int value;
	
	public Variable_Int(){
		this.value = 0;
	}
	
	public Variable_Int(int value){
		this.value = value;
	}
	
	public void add(int amount){
		this.value += amount;
	}
	
	public void remove(int amount){
		this.add(-amount);
	}
	
	public void set(int value){
		this.value = value;
	}
	
	public int get(){
		return this.value;
	}
}
