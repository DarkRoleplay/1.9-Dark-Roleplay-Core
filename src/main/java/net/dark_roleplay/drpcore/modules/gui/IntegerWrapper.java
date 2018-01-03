package net.dark_roleplay.drpcore.modules.gui;

public class IntegerWrapper{

	private int var = 0;
	private int min = 0;
	private int max = 0;
	
	public IntegerWrapper(int var, int min, int max){
		this.var = var;
		this.min = min;
		this.max = max;
	}
	
	public void set(int var){
		if(var <= min) this.var = min;
		else if(max > min && var >= max) this.var = max;
		else this.var = var;
	}

	public int get(){
		return this.var;
	}

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}
}
