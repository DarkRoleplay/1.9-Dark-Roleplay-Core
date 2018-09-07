package net.dark_roleplay.core.testing.gui_testing.components;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.objectweb.asm.tree.analysis.Value;

import net.dark_roleplay.core.References;
import net.dark_roleplay.core.api.old.modules.gui.IntegerWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class DoubleSliderBox extends Component{

	private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/guis/gui_elements.png");

	private IntegerWrapper left = new IntegerWrapper(25, 0, 100);
	private IntegerWrapper right = new IntegerWrapper(75, 0, 100);

	public Component focused = null;
	
	private boolean springy = false;
	
	public DoubleSliderBox(int posX, int posY, boolean springy) {
		super(106, 16, 106, 16);
		this.posX = posX;
		this.posY = posY;
		this.springy = springy;
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
		this.drawGradientRect(this.posX + left.get() + 2, this.posY + 1, this.posX + right.get() + 4, this.posY + this.height - 1, 0xFF00FFFF, 0xFF00FFFF);	
		this.drawGradientRect(this.posX + left.get() + 1, this.posY, this.posX + left.get() + 5, this.posY + this.height, 0xFF0000FF, 0xFF0000FF);			
		this.drawGradientRect(this.posX + right.get() + 1, this.posY, this.posX + right.get() + 5, this.posY + this.height, 0xFF00FF00, 0xFF00FF00);	
	}

	int selectedButton = 0;
	
	int initLeftOffset = 0;
	int initRightOffset = 0;
	
	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException { 
		int value = mouseX - this.posX - 2;
		
		if(value < left.get() || value <= left.get() + 2)
			this.selectedButton = 1;
		else if(value > right.get() || value >= right.get() - 2)			
			this.selectedButton = 2;
		else if(value > left.get() && value < right.get()) {
			this.selectedButton = 3;
			initLeftOffset = -(this.left.get() - value);
			initRightOffset = this.right.get() - value;
		}
		
		return true;
	}
	
	@Override
	public boolean mouseHold(int mouseX, int mouseY, int mouseButton, long ticksHold) { 
		int value = mouseX - this.posX - 2;

		switch(selectedButton) {
			case 1:
				left.set(value > right.get() ? right.get() - 1 : value);
				break;
			case 2:
				right.set(value < left.get() ? left.get() + 1 : value);
				break;
			case 3:
				if(!springy){
					if(value - initLeftOffset < 0)
						value -= value - initLeftOffset;
					if(value + initRightOffset > right.getMax())
						value -= value + initRightOffset - right.getMax();
				}
				this.left.set(value - initLeftOffset);
				this.right.set(value + initRightOffset);
				break;
			default:
				break;
		}
		return true;
	}
}
