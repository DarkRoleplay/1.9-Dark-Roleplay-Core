package net.dark_roleplay.core.api.old.gui.advanced;

import net.dark_roleplay.core.api.old.modules.gui.IGuiElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class Gui_Label extends IGuiElement.IMPL{

	protected String text;
	
	protected String[] cache;
	
	protected FontRenderer fr;
	
	protected int color;
	
	protected boolean hasShadow = true;
	
	public Gui_Label(String text, int color){
		this.text = text;
		this.fr = Minecraft.getMinecraft().fontRenderer;
		this.color = color;
	}
	
	public Gui_Label(String text, int color, int posX, int posY){
		this.text = text;
		this.fr = Minecraft.getMinecraft().fontRenderer;
		this.color = color;
		this.setPos(posX, posY);
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
	public IGuiElement setSize(int width, int height) {
		this.width = width;
		this.height = height;
		this.cache = null;
		return this;
	}
}
