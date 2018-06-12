package net.dark_roleplay.core.client.events.network;

import net.dark_roleplay.core.common.References;
import net.dark_roleplay.core.common.config.SyncedConfigRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;

public class Event_ConnectServer {

	@SubscribeEvent
	public void connectServer(FMLNetworkEvent.ClientConnectedToServerEvent event) {
		if(!event.isLocal())
			SyncedConfigRegistry.establishServerConnection();
	}
	

	@SubscribeEvent
	public void disconnectServer(FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
			SyncedConfigRegistry.disconnectFromServer();
	}
}
