package net.drpcore.client.events;

import net.drpcore.client.util.UpdateCheck;
import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.config.ConfigurationManager;
import net.drpcore.common.craftingOld.CraftingController;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.ClickEvent.Action;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;


/**
 * Currently unused but will be Used to Load and unload Crafting Recipes when they will be
 * configurable
 * 
 * @author JTK222
 */
public class ClientConnectedToServer {

	@SubscribeEvent
	public void ClientConnectedToServerEvent(ClientConnectedToServerEvent event) {

		CraftingController.loadRecipes();
		
		if(event.isLocal()) {
			DarkRoleplayCore.log.debug("Player has joined a Singleplayer World!");
		} else {
			DarkRoleplayCore.log.debug("Player has joined a Multiplayer World!");
		}
	}
}
