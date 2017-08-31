package net.dark_roleplay.drpcore.api.gui.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.dark_roleplay.drpcore.api.gui.DRPGuiScreen;
import net.minecraft.util.ResourceLocation;

public class GuiScreen_JSON extends DRPGuiScreen{

	public GuiScreen_JSON(ResourceLocation bgTexture, int bgWidth, int bgHeight, int bgOffsetX, int bgOffsetY) {
		super(bgTexture, bgWidth, bgHeight, bgOffsetX, bgOffsetY);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void drawForeground(int mouseX, int mouseY, float partialTicks) {
		// TODO Auto-generated method stub
		
	}
	
	private void readJSON(){
        Gson gson = new GsonBuilder().create();
	}

}
