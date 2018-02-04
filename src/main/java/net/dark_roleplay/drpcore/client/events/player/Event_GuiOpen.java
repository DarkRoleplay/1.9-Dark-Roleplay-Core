package net.dark_roleplay.drpcore.client.events.player;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import net.dark_roleplay.drpcore.client.gui.info.Gui_FirstRun;
import net.dark_roleplay.drpcore.client.gui.info.Gui_UpdateAvailable;
import net.dark_roleplay.drpcore.common.DRPCoreReferences;
import net.dark_roleplay.drpcore.common.handler.DRPCoreConfigs;
import net.dark_roleplay.drpcore.modules.work_in_progress.tutorial.GuiTutorial;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.common.ForgeVersion.Status;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Event_GuiOpen {

	public static boolean hasOpened = false;
	
	@SubscribeEvent
	public void onEvent(GuiOpenEvent  event) {
//		if(/**!hasOpened &&**/ event.getGui() instanceof GuiMainMenu){
//			
//			event.setGui(new GuiTutorial(event.getGui()));
//
//			if(DRPCoreConfigs.GENERAL.FIRST_INSTALL){
//				DRPCoreConfigs.GENERAL.FIRST_INSTALL = false;
//				ConfigManager.sync(DRPCoreInfo.MODID, Config.Type.INSTANCE);
//				event.setGui(new Gui_FirstRun());
//			}else if(DRPCoreConfigs.GENERAL.UPDATE_GUI && DRPCoreInfo.VERSION_STATUS.status == Status.OUTDATED || DRPCoreInfo.VERSION_STATUS.status == Status.BETA_OUTDATED){
//				event.setGui(new Gui_UpdateAvailable());
//				hasOpened = true;
//			}
//		}
	}
	
}
