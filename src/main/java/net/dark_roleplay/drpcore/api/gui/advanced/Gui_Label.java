package net.dark_roleplay.drpcore.api.gui.advanced;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class Gui_Label extends IGuiElement.IMPL{

	private String text;
	
	private String[] cache;
	
	private FontRenderer fr;
	
	private int color;
	
	private boolean hasShadow = true;
	
	public Gui_Label(String text, int color){
		this.text = text;
		this.fr = Minecraft.getMinecraft().fontRenderer;
		this.color = color;
	}
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTicks) {
		if(cache == null && text != null && !text.isEmpty()){
			cache = new String[5];
			cache = fr.listFormattedStringToWidth(text, this.width).toArray(cache);
		}
		
		if(text != null && !text.isEmpty()){
			for(int i = 0; i < cache.length; i++){
				if(hasShadow)
					this.fr.drawStringWithShadow(cache[i], this.posX, this.posY + (8 * i), color);
				else
					this.fr.drawString(cache[i], this.posX, this.posY + (8 * i), color);
			}
		}
		GlStateManager.color(1f, 1f, 1f);
	}
	
	public void disableShadow(){
		this.hasShadow = false;
	}
	
	public void setText(String text){
		this.text = text;
		this.cache = null;
	}
	
	public String getText(){
		return this.text;
	}
	
	@Override
	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		this.cache = null;
	}
}
