package net.dark_roleplay.drpcore.common.util.toasts;

import net.dark_roleplay.drpcore.common.DRPCoreReferences;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.toasts.SystemToast;
import net.minecraft.util.text.TextComponentString;

public class ToastController {

	public static void displayInfoToast(String title, String undertitle){
		if(DRPCoreReferences.SIDE.isClient()){
			Minecraft.getMinecraft().getToastGui().add(new SystemToast(SystemToast.Type.TUTORIAL_HINT, new TextComponentString(title), undertitle == null ? null : new TextComponentString(undertitle)));
		}
	}
	
	public static void showQuestToast(){
		Minecraft.getMinecraft().getToastGui().clear();
//		Minecraft.getMinecraft().getToastGui().add(new QuestToast(new IQuest()));
	}
	
}
