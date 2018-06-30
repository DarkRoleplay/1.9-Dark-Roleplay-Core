package net.dark_roleplay.core.modules.crops;

public class CropInfo {

	private int age;
	
	public CropInfo(){
		this.age = 0;
	}
	
	public CropInfo(int age){
		this.age = age;
	}
	
	public int getAge(){
		return age;
	}
	
	public void age(int amount){
		this.age += amount;
	}
}
