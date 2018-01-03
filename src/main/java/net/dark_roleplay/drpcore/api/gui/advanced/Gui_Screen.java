package net.dark_roleplay.drpcore.api.gui.advanced;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import net.dark_roleplay.drpcore.modules.gui.IGuiElement;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiLabel;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.init.SoundEvents;

public class Gui_Screen extends GuiScreen{

	protected List<IGuiElement> elements = new ArrayList<IGuiElement>();

	protected IGuiElement focused;
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		for(int i = 0; i < this.elements.size(); ++i){
			this.elements.get(i).draw(mouseX, mouseY, partialTicks);
		}
    }
	
	public void addElement(IGuiElement element){
		this.elements.add(element);
	}
	
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException{
        if (mouseButton == 0){
        	for(int i = 0; i < this.elements.size(); ++i){
    			IGuiElement element = this.elements.get(i);
    			if((mouseX > element.getPosX() && mouseX < element.getPosX() + element.getWidth()) && (mouseY > element.getPosY() && mouseY < element.getPosY() + element.getHeight())){
					if(element.mouseClicked(mouseX - element.getPosX(), mouseY - element.getPosY(), mouseButton)){
						this.mc.getSoundHandler().playSound(PositionedSoundRecord.getMasterRecord(SoundEvents.UI_BUTTON_CLICK, 1.0F));
					}
				}
    		}
        }
    }
}
