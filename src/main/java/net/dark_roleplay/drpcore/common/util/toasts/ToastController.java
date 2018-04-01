package net.dark_roleplay.drpcore.common.util.toasts;

import java.util.HashMap;
import java.util.Map;

import net.dark_roleplay.drpcore.common.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.IToast;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class ToastController {

	private static Map<String, IToast> toasts = new HashMap<String, IToast>();
	
	public static void displayInfoToast(String title, String undertitle){
		if(References.SIDE.isClient()) {
			SystemToast.addOrUpdate(Minecraft.getMinecraft().getToastGui(), SystemToast.Type.TUTORIAL_HINT, new TextComponentTranslation(title), undertitle == null ? null : new TextComponentTranslation(undertitle));
		}
	}
	
	public static void showQuestToast(){
		Minecraft.getMinecraft().getToastGui().clear();
//		Minecraft.getMinecraft().getToastGui().add(new QuestToast(new IQuest()));
	}
	
}
