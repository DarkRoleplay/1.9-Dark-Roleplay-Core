package net.dark_roleplay.core.api.old.gui;

public class Coordinates2D {

	int x;
	int y;
	
	public Coordinates2D(){
		this(0, 0);
	}
	
	public Coordinates2D(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void offset(int x, int y){
		this.x += x;
		this.y += y;
	}
	
	public void growY(int y){
		this.y += y;
	}
	
	public void growX(int x){
		this.x += x;
	}
	
	public void offsetY(int y){
		this.y += y;
	}
	
	public void offsetX(int x){
		this.x += x;
	}
	
	public void set(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getY(){
		return this.y;
	}
	
}
