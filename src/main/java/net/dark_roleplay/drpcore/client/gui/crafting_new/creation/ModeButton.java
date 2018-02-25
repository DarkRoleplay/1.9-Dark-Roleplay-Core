package net.dark_roleplay.drpcore.client.gui.crafting_new.creation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class ModeButton extends GuiButton{

	//false = all recipes
	private boolean mode = false;
	
	public ModeButton(int buttonId, int x, int y) {
		super(buttonId, x, y, 26, 16, null);
		
	}
	
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks){
        if (this.visible){
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            
            this.drawTexturedModalRect(this.x, this.y, this.mode ? 198 : 172, this.hovered ? 16 : 0, 26, 16);
        }
    }
	
	public boolean getMode(){
		return this.mode;
	}
	
	public boolean mousePressed(Minecraft mc, int mouseX, int mouseY){
		boolean pressed = this.enabled && this.visible && mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
		if(pressed) mode = !mode;
        return pressed;
    }
}
