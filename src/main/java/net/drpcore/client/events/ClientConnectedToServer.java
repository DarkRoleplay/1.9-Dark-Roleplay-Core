package net.drpcore.client.events;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.config.ConfigurationManager;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;

public class ClientConnectedToServer{

	@SubscribeEvent
	public void ClientConnectedToServerEvent(ClientConnectedToServerEvent event){
		if(event.isLocal()){
			ConfigurationManager configManager = DarkRoleplayCore.configManager;
			configManager.readMainRecipes();
			System.out.println("DARK ROLEPLAY DEBUG | DU BIST IM SINGLEPLAYER GEJOINT");
		}else{
			System.out.println("DARK ROLEPLAY DEBUG | DU BIST DEM MULTIPLAYER GEJOINT");
		}
	}
}
