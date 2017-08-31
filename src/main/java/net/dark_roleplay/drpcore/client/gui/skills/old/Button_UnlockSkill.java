package net.dark_roleplay.drpcore.client.gui.skills.old;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;

public class Button_UnlockSkill extends GuiButton {

	private boolean unlocked = false;
	
	public Button_UnlockSkill(int buttonID, int x, int y) {
		super(buttonID, x, y, "");
		this.width = 12;
		this.height = 21;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		int x = 231;
		int y = 214;
			
		if(!this.enabled) {
			y += 21;
			if(!unlocked){
				x += 13;
			}
		}else{
			if(!this.hovered) {
				x += 13;
			}
		}
		this.drawTexturedModalRect(this.x, this.y, x, y, this.width, this.height);
	}

	public void setLocked(){
		this.unlocked = false;
	}
	
	public void setUnlocked(){
		this.unlocked = true;
	}
	
}
