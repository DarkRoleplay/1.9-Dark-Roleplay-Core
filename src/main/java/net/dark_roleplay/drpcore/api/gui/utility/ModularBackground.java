package net.dark_roleplay.drpcore.api.gui.utility;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import net.dark_roleplay.drpcore.client.ClientProxy;
import net.minecraft.client.gui.Gui;

public class ModularBackground {

	public static void drawModular(Gui gui, int posX, int posY, int width, int height, int leftLine1, int leftLine2, int topLine1, int topLine2, int textureHeight, int textureWidth) {
		drawModular(gui, posX, posY, width, height, leftLine1, leftLine2, topLine1, topLine2, textureHeight, textureWidth, true);
	}
	
	public static void drawModular(Gui gui, int posX, int posY, int width, int height, int leftLine1, int leftLine2, int topLine1, int topLine2, int textureHeight, int textureWidth, boolean drawCenter) {
		// Corner TOP LEFT
		gui.drawTexturedModalRect(posX, posY, 0, 0, leftLine1, topLine1);
		// Corner TOP RIGHT
		gui.drawTexturedModalRect(posX + width - (textureWidth - leftLine2), posY, leftLine2, 0, textureWidth - leftLine2, topLine1);
		// Corner BOTTOM LEFT
		gui.drawTexturedModalRect(posX, posY + height - (textureHeight - topLine2), 0, topLine2, leftLine1, topLine1);
		// Corner BOTTOM RIGHT
		gui.drawTexturedModalRect(posX + width - (textureWidth - leftLine2), posY + height - (textureHeight - topLine2), leftLine2, topLine2, textureWidth - leftLine2, textureHeight - topLine2);

		// Line TOP & BOTTOM
		int lineWidth = (leftLine2 - leftLine1);
		int lineHeight = (topLine2 - topLine1);
		int lineMaxX = width - (textureWidth - leftLine2);
		int lineMaxY = height - (textureHeight - topLine2);
		
		
		for (int i = leftLine1; i < lineMaxX; i += lineWidth) {
			// Line TOP
			gui.drawTexturedModalRect(posX + i, posY, leftLine1, 0, i + lineWidth > lineMaxX ? lineMaxX - i : lineWidth, topLine1);

			// Line BOTTOM
			gui.drawTexturedModalRect(posX + i, posY + ( height - topLine1 ) , leftLine1, topLine2, i + lineWidth > lineMaxX ? lineMaxX - i : lineWidth, textureHeight - topLine2);
		}

		for (int j = topLine1; j < lineMaxY; j += lineHeight) {
			// Line LEFT
			gui.drawTexturedModalRect(posX, posY + j, 0, topLine1, leftLine1, j + lineHeight > lineMaxY ? lineMaxY - j : lineHeight);

			// Line RIGHT
			gui.drawTexturedModalRect(posX + ( width - leftLine1), posY + j , leftLine2, topLine1, textureWidth - leftLine2, j + lineHeight > lineMaxY ? lineMaxY - j : lineHeight);
			
			if(drawCenter)
				if(Calendar.getInstance().get(Calendar.MONTH) == Calendar.APRIL && Calendar.getInstance().get(Calendar.DAY_OF_MONTH) == 1){
					for (int i = leftLine1; i < lineMaxX; i += lineWidth) {
						gui.drawTexturedModalRect(posX + i, posY + j, 240, 0 + (ClientProxy.currentTick <= 4 ? 0 : ClientProxy.currentTick - 4), i + 16 > lineMaxX ? lineMaxX - i : 16, j + 16 > lineMaxY ? lineMaxY - j : 16);
					}
				}else{
					for (int i = leftLine1; i < lineMaxX; i += lineWidth) {
						gui.drawTexturedModalRect(posX + i, posY + j, leftLine1, topLine1, i + lineWidth > lineMaxX ? lineMaxX - i : lineWidth, j + lineHeight > lineMaxY ? lineMaxY - j : lineHeight);
					}
				}
		}
	}

}
