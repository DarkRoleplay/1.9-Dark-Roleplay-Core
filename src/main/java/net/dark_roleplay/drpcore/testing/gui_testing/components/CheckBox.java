package net.dark_roleplay.drpcore.testing.gui_testing.components;

import java.io.IOException;

import net.dark_roleplay.drpcore.common.References;
import net.dark_roleplay.library.wrapper.BooleanWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

public class CheckBox extends Component{

	private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/guis/gui_elements.png");
	
	private int u = 0, v = 208;
	private BooleanWrapper bool;
	
	public CheckBox(int posX, int posY, BooleanWrapper bool) {
		super(12, 12, 12, 12);
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
		
		this.drawTexturedModalRect(this.posX, this.posY, this.u + (bool.getValue() ? this.width : 0), this.v + (hover ? this.height : 0), this.width, this.height);		
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
