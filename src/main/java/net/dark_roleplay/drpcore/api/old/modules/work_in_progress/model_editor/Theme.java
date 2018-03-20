package net.dark_roleplay.drpcore.api.old.modules.work_in_progress.model_editor;

public class Theme {

	private int backgroundColor;
	private int foregroundColor;
	private int borderColor;
	
	public Theme(int bg, int fg, int borderColor){
		this.backgroundColor = bg;
		this.foregroundColor = fg;
		this.borderColor = borderColor;
	}

	public int getBG() {
		return backgroundColor;
	}

	public int getFG() {
		return foregroundColor;
	}

	public int getBorderColor() {
		return borderColor;
	}
}
