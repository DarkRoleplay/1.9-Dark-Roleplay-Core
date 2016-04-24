package net.drpcore.client.events;

import net.drpcore.common.util.crafting.CraftingManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;

/**
 * Currently unused but will be Used to Load an unload Crafting Recipes when they will be
 * configurable
 * 
 * @author JTK222
 */
public class ClientDisconnectFromServer {

	@SubscribeEvent
	public void ClientDisconnectFromServerEvent(ClientDisconnectionFromServerEvent event) {}

}
