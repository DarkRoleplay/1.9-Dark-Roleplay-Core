package net.drpcore.common.events;

import net.drpcore.client.events.ClientConnectedToServer;
import net.drpcore.client.events.ClientDisconnectFromServer;
import net.drpcore.client.events.OnPlayerTick;
import net.drpcore.client.events.RenderPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

/**
 * Used to do all event stuff (Registration)
 * 
 * @author JTK222
 */
public class DRPCoreEvents {

	public static void preInit(FMLPreInitializationEvent event) {}

	public static void init(FMLInitializationEvent event) {

		// CLIENT ONLY
		if(event.getSide() == Side.CLIENT){
			MinecraftForge.EVENT_BUS.register(new OnPlayerTick());
			MinecraftForge.EVENT_BUS.register(new ClientConnectedToServer());
			MinecraftForge.EVENT_BUS.register(new ClientDisconnectFromServer());
			MinecraftForge.EVENT_BUS.register(new RenderPlayer());
		}

		// SERVER ONLY
		else if(event.getSide() == Side.SERVER){

		}

		// BOOTH
		MinecraftForge.EVENT_BUS.register(new AttachCapabilitiesEntity());
		MinecraftForge.EVENT_BUS.register(new EntityJoinWorld());
		MinecraftForge.EVENT_BUS.register(new LivingDrop());

	}

	public static void postInit(FMLPostInitializationEvent event) {}

}
