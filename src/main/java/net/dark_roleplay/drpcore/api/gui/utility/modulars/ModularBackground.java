package net.dark_roleplay.drpcore.api.gui.utility.modulars;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.dark_roleplay.drpcore.client.ClientProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class ModularBackground {

	public static void drawModular(Gui gui, int posX, int posY, int width, int height) {
		drawModular(gui, posX, posY, width, height, true, false);
	}
	
	public static void drawModular(Gui gui, int posX, int posY, int width, int height, boolean drawCenter, boolean isHollow) {
		if(!isHollow)
			Minecraft.getMinecraft().renderEngine.bindTexture(ClientProxy.currentGui.getBackground());
		else
			Minecraft.getMinecraft().renderEngine.bindTexture(ClientProxy.currentGui.getBackgroundHollow());

		int leftLine1 = ClientProxy.currentGui.getLeft(), leftLine2 = ClientProxy.currentGui.getRight(), topLine1 = ClientProxy.currentGui.getTop(), topLine2 = ClientProxy.currentGui.getBottom(), textureHeight = 32, textureWidth = 32;
		// Corner TOP LEFT
		gui.drawModalRectWithCustomSizedTexture(posX, posY, 0, 0, leftLine1, topLine1, 32, 32);
		// Corner TOP RIGHT
		gui.drawModalRectWithCustomSizedTexture(posX + width - (textureWidth - leftLine2), posY, leftLine2, 0, textureWidth - leftLine2, topLine1, 32, 32);
		// Corner BOTTOM LEFT
		gui.drawModalRectWithCustomSizedTexture(posX, posY + height - (textureHeight - topLine2), 0, topLine2, leftLine1, topLine1, 32, 32);
		// Corner BOTTOM RIGHT
		gui.drawModalRectWithCustomSizedTexture(posX + width - (textureWidth - leftLine2), posY + height - (textureHeight - topLine2), leftLine2, topLine2, textureWidth - leftLine2, textureHeight - topLine2, 32, 32);

		// Line TOP & BOTTOM
		int lineWidth = (leftLine2 - leftLine1);
		int lineHeight = (topLine2 - topLine1);
		int lineMaxX = width - (textureWidth - leftLine2);
		int lineMaxY = height - (textureHeight - topLine2);
		
		
		for (int i = leftLine1; i < lineMaxX; i += lineWidth) {
			// Line TOP
			gui.drawModalRectWithCustomSizedTexture(posX + i, posY, leftLine1, 0, i + lineWidth > lineMaxX ? lineMaxX - i : lineWidth, topLine1, 32, 32);

			// Line BOTTOM
			gui.drawModalRectWithCustomSizedTexture(posX + i, posY + ( height - topLine1 ) , leftLine1, topLine2, i + lineWidth > lineMaxX ? lineMaxX - i : lineWidth, textureHeight - topLine2, 32, 32);
		}

		for (int j = topLine1; j < lineMaxY; j += lineHeight) {
			// Line LEFT
			gui.drawModalRectWithCustomSizedTexture(posX, posY + j, 0, topLine1, leftLine1, j + lineHeight > lineMaxY ? lineMaxY - j : lineHeight, 32, 32);

			// Line RIGHT
			gui.drawModalRectWithCustomSizedTexture(posX + ( width - leftLine1), posY + j , leftLine2, topLine1, textureWidth - leftLine2, j + lineHeight > lineMaxY ? lineMaxY - j : lineHeight, 32, 32);
			
			if(drawCenter)
				
			for (int i = leftLine1; i < lineMaxX; i += lineWidth) {
				gui.drawModalRectWithCustomSizedTexture(posX + i, posY + j, leftLine1, topLine1, i + lineWidth > lineMaxX ? lineMaxX - i : lineWidth, j + lineHeight > lineMaxY ? lineMaxY - j : lineHeight, 32, 32);
			}
		}
	}
	
	public static void drawModularCenter(Gui gui, int posX, int posY, int width, int height, boolean isHollow) {
		if(!isHollow)
			Minecraft.getMinecraft().renderEngine.bindTexture(ClientProxy.currentGui.getBackground());
		else
			Minecraft.getMinecraft().renderEngine.bindTexture(ClientProxy.currentGui.getBackgroundHollow());
		
		int leftLine1 = ClientProxy.currentGui.getLeft(), leftLine2 = ClientProxy.currentGui.getRight(), topLine1 = ClientProxy.currentGui.getTop(), topLine2 = ClientProxy.currentGui.getBottom(), textureHeight = 32, textureWidth = 32;

		
		int lineWidth = (leftLine2 - leftLine1);
		int lineHeight = (topLine2 - topLine1);
		int lineMaxX = width;
		int lineMaxY = height;
		
		for (int j = 0; j < lineMaxY; j += lineHeight) {
			for (int i = 0; i < lineMaxX; i += lineWidth) {
				gui.drawModalRectWithCustomSizedTexture(posX + i, posY + j, leftLine1, topLine1, i + lineWidth > lineMaxX ? lineMaxX - i : lineWidth, j + lineHeight > lineMaxY ? lineMaxY - j : lineHeight, 32, 32);
			}
		}
	}


}
