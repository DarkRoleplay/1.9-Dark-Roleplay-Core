package net.dark_roleplay.drpcore.client.gui.crafting_new.creation;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;

public class ArrowButton extends GuiButton{

	//false = all recipes
	private boolean direction = false;
	
	public ArrowButton(int buttonId, int x, int y, boolean dir) {
		super(buttonId, x, y, 11, 17, null);
		this.direction = dir;
	}
	
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks){
        if (this.visible){
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            
            this.drawTexturedModalRect(this.x, this.y, this.direction ? 235 : 224, this.hovered ? 40 : 23, 11, 17);
        }
    }
}