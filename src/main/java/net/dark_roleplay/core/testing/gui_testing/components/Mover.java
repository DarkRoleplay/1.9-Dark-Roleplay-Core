package net.dark_roleplay.core.testing.gui_testing.components;

import net.dark_roleplay.core.References;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class Mover extends Component{

	private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/guis/gui_elements.png");

	public Mover(int posX, int posY) {
		super(16, 16, 16, 16);
		this.posX = posX;
		this.posY = posY;
	}
	
	@Override
	public ResourceLocation getTexture() {
		return texture;
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(this.getTexture());
		
		boolean hover = this.isHovered(mouseX, mouseY);
		
		this.drawGradientRect(this.posX, this.posY, this.posX + this.width, this.posY + this.height, 0xFF000000, 0xFF000000);		
	}

	public boolean mouseHold(int mouseX, int mouseY, int mouseButton, long ticksHold) { 
		
		this.posX = mouseX - (this.width/2);
		this.posY = mouseY - (this.height/2);
		
		return true;
	}
}
