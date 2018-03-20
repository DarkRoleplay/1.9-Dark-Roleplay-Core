package net.dark_roleplay.drpcore.client.gui.crafting_new.creation;

import java.io.IOException;

import net.dark_roleplay.drpcore.common.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;

public class Crafting5 extends GuiScreen{
	
	public static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/guis/crafting/crafting_gui.png");
	
	ModeButton modeButton;
	GuiButton pageLeft;
	GuiButton pageRight;
	GuiButton variantLeft;
	GuiButton variantRight;
	GuiButton craft;
	
	public void initGui(){
		int i = 0;
		
		int posY = (this.height - 166) / 2;
		int posX = (this.width / 2) - 173;
		this.addButton(new ModeButton(i++, posX + 134, posY + 12));
		this.addButton(new ArrowButton(i++, posX + 50, posY + 136, false));
		this.addButton(new ArrowButton(i++, posX + 110, posY + 136, true));
		

		posX += 175;
		this.addButton(new ArrowButton(i++, posX + 11, posY + 133, false));
		this.addButton(new ArrowButton(i++, posX + 50, posY + 133, true));
    }

	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		int posY = (this.height - 166) / 2;
		int posX = (this.width / 2) - 173;
		this.drawDefaultBackground();
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		
		//Left Side
		this.drawTexturedModalRect(posX, posY, 0, 0, 172, 166);
		this.drawTexturedModalRect(posX + 9, posY + 12, 172, 97, 12, 12);

		for(int x = 0; x < 6; x++){
			for(int y = 0; y < 4; y++){
				this.drawTexturedModalRect(posX + 11 + (x * 25), posY + 31 + (y * 25), 172, 32, 25, 25);
			}
		}
		
		//Right Side
		posX += 175;
		this.drawTexturedModalRect(posX, posY, 0, 0, 172, 166);
		

		for(int x = 0; x < 6; x++)
			this.drawTexturedModalRect(posX + 11 + (x * 25), posY + 98, 172, 32, 25, 25);
		
		for(int x = 0; x < 6; x++)
			this.drawTexturedModalRect(posX + 11 + (x * 25), posY + 22, 172, 32, 25, 25);

		for(int x = 0; x < 6; x++)
			this.drawTexturedModalRect(posX + 11 + (x * 25), posY + 60, 172, 32, 25, 25);
		
		this.drawTexturedModalRect(posX + 24, posY + 129, 172, 32, 25, 25);
		this.drawTexturedModalRect(posX + 137, posY + 129, 196, 57, 24, 24);

		
		this.drawString(this.fontRenderer, I18n.format("Output"), posX + 11, posY +  11, 0xFFFFFFFF);
		this.drawString(this.fontRenderer, I18n.format("Ingredients"), posX + 11, posY +  49, 0xFFFFFFFF);
		this.drawString(this.fontRenderer, I18n.format("Tools"), posX + 11, posY +  87, 0xFFFFFFFF);

		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		super.drawScreen(mouseX, mouseY, partialTicks);
    }
	
	protected void actionPerformed(GuiButton button) throws IOException{
		
    }
}
