package net.dark_roleplay.drpcore.client.gui.advanced.labels.blueprint_controll;

import net.dark_roleplay.drpcore.api.gui.advanced.Gui_Label;
import net.dark_roleplay.drpcore.api.gui.utility.wrappers.Variable_Int;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

public class Label_AxisInt extends Gui_Label{

	private Variable_Int var;
	
	public Label_AxisInt(Variable_Int var, String translationKey, int color){
		super(translationKey, color);
		this.var = var;
	}
	
	public Label_AxisInt(Variable_Int var, String translationKey, int color, int posX, int posY){
		super(translationKey, color, posX, posY);
		this.var = var;
	}
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTicks) {
		if(text != null && !text.isEmpty()){
			String localizedText = I18n.format(text, var.get());
			int width = this.fr.getStringWidth(localizedText);
			if(hasShadow){
				this.fr.drawStringWithShadow(localizedText, this.posX + (this.width - width) / 2, this.posY, color);
			}else{
				this.fr.drawString(localizedText, this.posX + (this.width - width) / 2, this.posY, color);
			}
		}
		GlStateManager.color(1f, 1f, 1f);
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public String getText(){
		return this.text;
	}
	
}
