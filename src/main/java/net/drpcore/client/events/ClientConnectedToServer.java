package net.drpcore.client.events;

import net.drpcore.client.util.UpdateCheck;
import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.config.ConfigurationManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.ClickEvent.Action;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent.ClientConnectedToServerEvent;

public class ClientConnectedToServer{

	@SubscribeEvent
	public void ClientConnectedToServerEvent(ClientConnectedToServerEvent event){
		if(event.isLocal()){
			//ConfigurationManager configManager = DarkRoleplayCore.configManager;
			//configManager.readMainRecipes();
			System.out.println("DARK ROLEPLAY DEBUG | Player joint Singleplayer");
		}else{
			System.out.println("DARK ROLEPLAY DEBUG | Player joint Multiplayer");
		}
	}
}
