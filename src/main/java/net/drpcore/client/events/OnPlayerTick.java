package net.drpcore.client.events;

import net.drpcore.client.util.UpdateCheck;
import net.drpcore.common.DarkRoleplayCore;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.ClickEvent.Action;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;

public class OnPlayerTick {
	
	private boolean hasShownUp = false;
	
	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event){
		displayUpdate();
	}

	public void displayUpdate(){
		if(UpdateCheck.isNewVersionAvailable() && !hasShownUp /*&& Minecraft.getMinecraft().currentScreen == null*/){
			ClickEvent downloadURL = new ClickEvent(Action.OPEN_URL, UpdateCheck.getDownloadURL());
			ClickEvent changelogURL = new ClickEvent(Action.OPEN_URL, UpdateCheck.getChangelogURL());
 			 
			TextComponentString separator = new TextComponentString(TextFormatting.DARK_PURPLE + "-------------------------------------");
			TextComponentString line1 = new TextComponentString(TextFormatting.GOLD + "Dark Roleplay Core:");
			TextComponentString line2 = new TextComponentString(TextFormatting.GOLD + "There is a new Version available!");
			TextComponentString line3 = new TextComponentString(TextFormatting.GOLD + "Your version: "+DarkRoleplayCore.VERSION + " latest version: "+ UpdateCheck.getVersion());
			TextComponentString line4 = new TextComponentString(TextFormatting.GOLD + "Click here to open the download page");
			TextComponentString line5 = new TextComponentString(TextFormatting.GOLD + "Click here to open the changelog");
			
			line4.getChatStyle().setChatClickEvent(downloadURL);
			line5.getChatStyle().setChatClickEvent(changelogURL);
		
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(separator);
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(line1);
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(line2);
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(line3);
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(line4);
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(line5);
			Minecraft.getMinecraft().thePlayer.addChatComponentMessage(separator);
			hasShownUp = true;
		}
	}
}

