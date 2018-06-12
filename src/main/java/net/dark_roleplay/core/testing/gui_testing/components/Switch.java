package net.dark_roleplay.core.testing.gui_testing.components;

import java.io.IOException;

import net.dark_roleplay.core.common.References;
import net.dark_roleplay.library_old.wrapper.BooleanWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

public class Switch extends Component{

	private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/guis/gui_elements.png");
	
	private int u = 0, v = 232;
	private BooleanWrapper bool;
	
	public Switch(int posX, int posY, BooleanWrapper bool) {
		super(24, 12, 24, 12);
		this.posX = posX;
		this.posY = posY;
		this.bool = bool;
	}
	
	@Override
	public ResourceLocation getTexture() {
		return texture;
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(this.getTexture());
		
		boolean hover = this.isHovered(mouseX, mouseY);
		
		this.drawTexturedModalRect(this.posX, this.posY, this.u + (hover ? this.width : 0), this.v + (bool.getValue() ? this.height : 0), this.width, this.height);
		
	}

	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if(this.isHovered(mouseX, mouseY)) {
			this.bool.setValue(!this.bool.getValue());
		}       
		
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		
		return this.isHovered(mouseX, mouseY);
	}

}
