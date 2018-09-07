package net.dark_roleplay.core.testing.gui_testing.components;

import java.io.IOException;

import net.dark_roleplay.core.References;
import net.dark_roleplay.core.api.old.modules.gui.IntegerWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class SliderBox extends Component{

	private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/guis/gui_elements.png");
	
	private IntegerWrapper wrapper = new IntegerWrapper(50, 0, 100);
	
	public SliderBox(int posX, int posY) {
		super(106, 16, 106, 16);
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
		
		this.drawGradientRect(this.posX, this.posY, this.posX + this.width, this.posY + this.height, 0xFFFF0000, 0xFFFF0000);		
		this.drawGradientRect(this.posX + wrapper.get() + 1, this.posY, this.posX + wrapper.get() + 5, this.posY + this.height, 0xFF0000FF, 0xFF0000FF);	
	}

	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException { 
		wrapper.set(mouseX - this.posX - 2);
		
		return true; 
	}

	
	@Override
	public boolean mouseHold(int mouseX, int mouseY, int mouseButton, long ticksHold) { 
		wrapper.set(mouseX - this.posX - 2);
		
		return true;
	}
}
