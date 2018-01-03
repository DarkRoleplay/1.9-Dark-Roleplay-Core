package net.dark_roleplay.drpcore.api.gui.advanced;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import net.dark_roleplay.drpcore.api.gui.modular.ModularGui_Drawer;
import net.dark_roleplay.drpcore.modules.gui.IGuiElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;

public class Gui_Button extends IGuiElement.IMPL{

	protected boolean enabled = true;
	protected boolean hovered = false;
	protected String text = "";
	
	public Gui_Button(int posX, int posY, int width, int height){
		this.posX = posX;
		this.posY = posY;
		this.width = width;
		this.height = height;
	}
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTicks) {
		if((mouseX >= this.posX && mouseX < this.posX + this.width) && (mouseY >= this.posY && mouseY < this.posY + this.height))
			this.hovered = true;
		else
			this.hovered = false;
		GlStateManager.color(1F, 1F, 1F);
		ModularGui_Drawer.drawButtons(this.posX, this.posY, this.width, this.height, this.enabled ? (this.hovered ? ModularGui_Drawer.State.HOVERED : ModularGui_Drawer.State.ENABLED) : ModularGui_Drawer.State.DISABLED);
		
		FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
		
		fontRenderer.drawStringWithShadow(this.text, (int) (this.posX + ((this.width / 2) - (fontRenderer.getStringWidth(this.text)/2))), this.posY + (this.height / 2) - 4, new Color(255,255,255).getRGB());
	}

	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton){
		return true;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public Gui_Button setText(String text){
		this.text = text;
		return this;
	}
}
