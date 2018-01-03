package net.dark_roleplay.drpcore.api.gui.advanced;

import net.dark_roleplay.drpcore.client.gui.ModularGuiHandler;
import net.dark_roleplay.drpcore.modules.gui.IGuiElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class Gui_Icon extends IGuiElement.IMPL{

	private ResourceLocation icon;
	
	public Gui_Icon(ResourceLocation icon){
		this.icon = icon;
	}
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTicks) {
		if(icon != null){
			Minecraft.getMinecraft().renderEngine.bindTexture(this.icon);
			this.drawPerctentedRect(this.posX, this.posY, this.width, this.height, 0F, 0F, 1F, 1F);
		}
	}

	public Gui_Icon getClone(){
		return new Gui_Icon(this.icon);
	}
}
