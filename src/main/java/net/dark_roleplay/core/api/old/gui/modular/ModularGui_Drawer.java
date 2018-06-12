package net.dark_roleplay.core.api.old.gui.modular;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.dark_roleplay.core.client.ClientProxy;
import net.dark_roleplay.core.client.gui.ModularGuiHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ModularGui_Drawer {

	private static void drawModular(int posX, int posY, int width, int height, int leftLine1, int leftLine2, int topLine1, int topLine2, int textureWidth, int textureHeight){
		drawModular(posX, posY, width, height, leftLine1, leftLine2, topLine1, topLine2, textureWidth, textureHeight, true);
	}

	
	private static void drawModular(int posX, int posY, int width, int height, int leftLine1, int leftLine2, int topLine1, int topLine2, int textureWidth, int textureHeight, boolean drawCenter){
		Gui gui = Minecraft.getMinecraft().currentScreen;
		// Corner TOP LEFT
		gui.drawModalRectWithCustomSizedTexture(posX, posY, 0, 0, leftLine1, topLine1, textureWidth, textureHeight);
		// Corner TOP RIGHT
		gui.drawModalRectWithCustomSizedTexture(posX + width - (textureWidth - leftLine2), posY, leftLine2, 0, textureWidth - leftLine2, topLine1, textureWidth, textureHeight);
		// Corner BOTTOM LEFT
		gui.drawModalRectWithCustomSizedTexture(posX, posY + height - (textureHeight - topLine2), 0, 0 + topLine2, leftLine1, topLine1, textureWidth, textureHeight);
		// Corner BOTTOM RIGHT
		gui.drawModalRectWithCustomSizedTexture(posX + width - (textureWidth - leftLine2), posY + height - (textureHeight - topLine2), leftLine2, topLine2, textureWidth - leftLine2, textureHeight - topLine2, textureWidth, textureHeight);

		// Line TOP & BOTTOM
		int lineWidth = (leftLine2 - leftLine1);
		int lineHeight = (topLine2 - topLine1);
		int lineMaxX = width - (textureWidth - leftLine2);
		int lineMaxY = height - (textureHeight - topLine2);
		
		for (int i = leftLine1; i < lineMaxX; i += lineWidth) {
			// Line TOP
			gui.drawModalRectWithCustomSizedTexture(posX + i, posY, leftLine1, 0, i + lineWidth > lineMaxX ? lineMaxX - i : lineWidth, topLine1, textureWidth, textureHeight);

			// Line BOTTOM
			gui.drawModalRectWithCustomSizedTexture(posX + i, posY + ( height - topLine1 ) , leftLine1, topLine2, i + lineWidth > lineMaxX ? lineMaxX - i : lineWidth, textureHeight - topLine2, textureWidth, textureHeight);
		}

		for (int j = topLine1; j < lineMaxY; j += lineHeight) {
			// Line LEFT
			gui.drawModalRectWithCustomSizedTexture(posX, posY + j, 0, topLine1, leftLine1, j + lineHeight > lineMaxY ? lineMaxY - j : lineHeight, textureWidth, textureHeight);

			// Line RIGHT
			gui.drawModalRectWithCustomSizedTexture(posX + ( width - leftLine1), posY + j , leftLine2, topLine1, textureWidth - leftLine2, j + lineHeight > lineMaxY ? lineMaxY - j : lineHeight, textureWidth, textureHeight);
			
			if(drawCenter)
				
			for (int i = leftLine1; i < lineMaxX; i += lineWidth) {
				gui.drawModalRectWithCustomSizedTexture(posX + i, posY + j, leftLine1, topLine1, i + lineWidth > lineMaxX ? lineMaxX - i : lineWidth, j + lineHeight > lineMaxY ? lineMaxY - j : lineHeight, textureWidth, textureHeight);
			}
		}
	}
	
	public static void drawBackground(int posX, int posY, int width, int height) {
		drawBackground(posX, posY, width, height, true, false);
	}
	
	public static void drawBackground(int posX, int posY, int width, int height, boolean drawCenter, boolean isHollow) {
		if(!isHollow)
			Minecraft.getMinecraft().renderEngine.bindTexture(ModularGuiHandler.currentGui.getModularBG().getPath());
		else
			Minecraft.getMinecraft().renderEngine.bindTexture(ModularGuiHandler.currentGui.getModularBG().getPathHollow());

		int leftLine1 = ModularGuiHandler.currentGui.getModularBG().getLeft();
		int	leftLine2 = ModularGuiHandler.currentGui.getModularBG().getRight();
		int topLine1 = ModularGuiHandler.currentGui.getModularBG().getTop();
		int topLine2 = ModularGuiHandler.currentGui.getModularBG().getBottom();
		int textureWidth = ModularGuiHandler.currentGui.getModularBG().getWidth();
		int textureHeight = ModularGuiHandler.currentGui.getModularBG().getHeight();
		
		drawModular(posX, posY, width, height, leftLine1, leftLine2, topLine1, topLine2, textureWidth, textureHeight, drawCenter);
	}
	
	public static void drawBackgroundCenter(int posX, int posY, int width, int height, boolean isHollow) {
		Gui gui = Minecraft.getMinecraft().currentScreen;
		if(!isHollow)
			Minecraft.getMinecraft().renderEngine.bindTexture(ModularGuiHandler.currentGui.getModularBG().getPath());
		else
			Minecraft.getMinecraft().renderEngine.bindTexture(ModularGuiHandler.currentGui.getModularBG().getPathHollow());
		
		int leftLine1 = ModularGuiHandler.currentGui.getModularBG().getLeft();
		int	leftLine2 = ModularGuiHandler.currentGui.getModularBG().getRight();
		int topLine1 = ModularGuiHandler.currentGui.getModularBG().getTop();
		int topLine2 = ModularGuiHandler.currentGui.getModularBG().getBottom();
		int textureWidth = ModularGuiHandler.currentGui.getModularBG().getWidth();
		int textureHeight = ModularGuiHandler.currentGui.getModularBG().getHeight();
		
		int lineWidth = (leftLine2 - leftLine1);
		int lineHeight = (topLine2 - topLine1);
		int lineMaxX = width;
		int lineMaxY = height;
		
		for (int j = 0; j < lineMaxY; j += lineHeight) {
			for (int i = 0; i < lineMaxX; i += lineWidth) {
				gui.drawModalRectWithCustomSizedTexture(posX + i, posY + j, leftLine1, topLine1, i + lineWidth > lineMaxX ? lineMaxX - i : lineWidth, j + lineHeight > lineMaxY ? lineMaxY - j : lineHeight, textureWidth, textureHeight);
			}
		}
	}

	public static void drawButtons(int posX, int posY, int width, int height, State state) {
		switch(state){
			case DISABLED:		
				Minecraft.getMinecraft().renderEngine.bindTexture(ModularGuiHandler.currentGui.getModularButtons().getPathDisabled());
				break;
			case ENABLED:
				Minecraft.getMinecraft().renderEngine.bindTexture(ModularGuiHandler.currentGui.getModularButtons().getPathEnabled());
				break;
			case HOVERED:
				Minecraft.getMinecraft().renderEngine.bindTexture(ModularGuiHandler.currentGui.getModularButtons().getPathHovered());
				break;
		}


		int leftLine1 = ModularGuiHandler.currentGui.getModularButtons().getLeft();
		int	leftLine2 = ModularGuiHandler.currentGui.getModularButtons().getRight();
		int topLine1 = ModularGuiHandler.currentGui.getModularButtons().getTop();
		int topLine2 = ModularGuiHandler.currentGui.getModularButtons().getBottom();
		int textureWidth = ModularGuiHandler.currentGui.getModularButtons().getWidth();
		int textureHeight = ModularGuiHandler.currentGui.getModularButtons().getHeight();

		drawModular(posX, posY, width, height, leftLine1, leftLine2, topLine1, topLine2, textureWidth, textureHeight, true);
	}
	
	public static enum State{
		DISABLED,
		ENABLED,
		HOVERED;
	}
}
