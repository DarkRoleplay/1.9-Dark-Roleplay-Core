package net.dark_roleplay.drpcore.modules.update_checker;

import net.dark_roleplay.drpcore.api.old.Modules;
import net.dark_roleplay.drpcore.common.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(modid = References.MODID)
public class Event_GuiEvents {
	
	@SubscribeEvent
	public static void guiInit(GuiScreenEvent.InitGuiEvent event) {
		if(event.getGui() instanceof GuiMainMenu && !Modules.UPDATE_CHECKER.mods.isEmpty()) {
			System.out.println("GuiMainMenu");
			GuiMainMenu menu = (GuiMainMenu) event.getGui();
			GuiButton updateAvailableButton = new GuiButton(222, menu.width - 105, 5, 100, 20, "Updates Available!" );
			event.getButtonList().add(updateAvailableButton);
		}
		
	}
	
	@SubscribeEvent
	public static void guiButtonPressed(GuiScreenEvent.ActionPerformedEvent event) {
		
		if(event.getGui() instanceof GuiMainMenu && event.getButton().id == 222) {
			Minecraft.getMinecraft().displayGuiScreen(new Gui_UpdateInformation());
		}
		
	}
	
}
