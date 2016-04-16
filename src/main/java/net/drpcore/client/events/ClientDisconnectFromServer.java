package net.drpcore.client.events;

import net.drpcore.common.util.crafting.CraftingManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientDisconnectionFromServerEvent;

public class ClientDisconnectFromServer {

	@SubscribeEvent
	public void ClientDisconnectFromServerEvent(ClientDisconnectionFromServerEvent event){
		//CraftingManager.unloadAllRecipes();
	}
	
}
