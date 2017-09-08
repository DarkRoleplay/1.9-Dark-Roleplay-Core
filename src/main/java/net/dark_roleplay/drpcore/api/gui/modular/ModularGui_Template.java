package net.dark_roleplay.drpcore.api.gui.modular;

import net.minecraft.util.ResourceLocation;

public class ModularGui_Template {

	private String name;
	
	private Modular_Background bg;
	private Modular_Buttons buttons;
	
	public ModularGui_Template(String name){
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
	/**BACKGROUND**/
	public void setModularBG(Modular_Background bg){
		this.bg = bg;
	}
	
	public Modular_Background getModularBG(){
		return this.bg;
	}
	
	/**BUTTONS**/
	public void setModularButtons(Modular_Buttons buttons){
		this.buttons = buttons;
	}
	
	public Modular_Buttons getModularButtons(){
		return this.buttons;
	}
}
