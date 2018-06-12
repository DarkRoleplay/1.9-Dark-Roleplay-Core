package net.dark_roleplay.core.testing.gui_testing.components;

import java.io.IOException;

import net.dark_roleplay.core.common.References;
import net.dark_roleplay.library_old.wrapper.BooleanWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

public class RadioButton extends Component{

	private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/guis/gui_elements.png");
	
	private int u = 24, v = 208;
	private boolean selected = false;
	private RadioGroup group;
	private int id;
	
	public RadioButton(int posX, int posY, int id) {
		super(12, 12, 12, 12);
		this.posX = posX;
		this.posY = posY;
		this.id = id;
	}
	
	@Override
	public ResourceLocation getTexture() {
		return texture;
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		Minecraft.getMinecraft().getTextureManager().bindTexture(this.getTexture());
		
		boolean hover = this.isHovered(mouseX, mouseY);
		
		this.drawTexturedModalRect(this.posX, this.posY, this.u + (this.selected ? this.width : 0), this.v + (hover ? this.height : 0), this.width, this.height);
	}

	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		if(this.isHovered(mouseX, mouseY)) {
			if(this.group != null)
				this.group.selectButton(this);
		}       
		
		Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
		
		return this.isHovered(mouseX, mouseY);
	}
	
	public void deselect() {
		this.selected = false;
	}
	
	public void select() {
		this.selected = true;
	}
	
	public void setGroup(RadioGroup group) {
		this.group = group;
	}
}
