package net.drpcore.client.events;

import net.drpcore.client.util.UpdateCheck;
import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.util.localization.DPRCoreLocalization;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.ClickEvent.Action;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.PlayerTickEvent;


/**
 * Only used once to Inform the Player update available Updates
 * 
 * @author JTK222
 */
public class OnPlayerTick {

	private boolean hasShownUp = false;

	@SubscribeEvent
	public void onPlayerTick(PlayerTickEvent event) {

		displayUpdate(event.player);
	}

	public void displayUpdate(EntityPlayer player) {
		/*
		 * if(UpdateCheck.isNewVersionAvailable() && !hasShownUp){
		 * ClickEvent downloadURL = new ClickEvent(Action.OPEN_URL, UpdateCheck.getDownloadURL());
		 * ClickEvent changelogURL = new ClickEvent(Action.OPEN_URL, UpdateCheck.getChangelogURL());
		 * TextComponentString separator = new TextComponentString(TextFormatting.DARK_PURPLE +
		 * "-------------------------------------");
		 * TextComponentString line1 = new TextComponentString(TextFormatting.GOLD +
		 * I18n.translateToLocal(DPRCoreLocalization.modName)+":");
		 * TextComponentString line2 = new TextComponentString(TextFormatting.GOLD +
		 * I18n.translateToLocal(DPRCoreLocalization.updateNewVersionAvailable));
		 * TextComponentString line3 = new TextComponentString(TextFormatting.GOLD +
		 * I18n.translateToLocal(DPRCoreLocalization.updateCurrentVersion)+": " +
		 * DarkRoleplayCore.VERSION + " " +
		 * I18n.translateToLocal(DPRCoreLocalization.updateLatestVersion)+": " +
		 * UpdateCheck.getVersion());
		 * TextComponentString line4 = new TextComponentString(TextFormatting.GOLD +
		 * I18n.translateToLocal(DPRCoreLocalization.updateOpenDownload));
		 * TextComponentString line5 = new TextComponentString(TextFormatting.GOLD +
		 * I18n.translateToLocal(DPRCoreLocalization.updateOpenChangelog));
		 * line4.getChatStyle().setChatClickEvent(downloadURL);
		 * line5.getChatStyle().setChatClickEvent(changelogURL);
		 * player.addChatComponentMessage(separator);
		 * player.addChatComponentMessage(line1);
		 * player.addChatComponentMessage(line2);
		 * player.addChatComponentMessage(line3);
		 * player.addChatComponentMessage(line4);
		 * player.addChatComponentMessage(line5);
		 * player.addChatComponentMessage(separator);
		 * hasShownUp = true;
		 * }
		 */
	}
}
