package net.dark_roleplay.drpcore.api.old.modules.work_in_progress.premium;

import net.dark_roleplay.drpcore.api.old.modules.gui.IGuiElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class Addon extends IGuiElement.IMPL{
	
	private PremiumAddon addon;
	private FontRenderer fontRenderer;
	
	public Addon(PremiumAddon addon, int posX, int posY, int width){
		this.addon = addon;
		this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
		int height = 10 + (fontRenderer.listFormattedStringToWidth(addon.getDescription(), width).size() * 9);
		this.setSize(width, height);
		this.setPos(posX, posY);
	}

	@Override
	public void draw(int mouseX, int mouseY, float partialTicks) {
		this.fontRenderer.drawString(addon.getName(), this.posX + 1, this.posY + 1, 0xFFFFFFFF);
		this.fontRenderer.drawSplitString(addon.getDescription(), this.posX + 1, this.posY + 11, this.width, 0xFFFFFFFF);
		this.fontRenderer.drawString("Price: " + addon.getPrice(), this.posX + this.width - fontRenderer.getStringWidth("Price: " + addon.getPrice()), this.posY + 1, 0xFFFFFFFF);
	}

}
