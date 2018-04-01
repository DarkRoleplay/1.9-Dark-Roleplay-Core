package net.dark_roleplay.drpcore.common.handler;

import net.dark_roleplay.drpcore.client.events.config.Event_ConfigChange;
import net.dark_roleplay.drpcore.client.events.player.Event_GuiOpen;
import net.dark_roleplay.drpcore.client.events.player.Event_Mouse;
import net.dark_roleplay.drpcore.client.events.player.Event_Tooltip;
import net.dark_roleplay.drpcore.client.events.rendering.Event_BlockHighlight;
import net.dark_roleplay.drpcore.common.objects.events.blocks.Event_BlockBreak;
import net.dark_roleplay.drpcore.common.objects.events.capabilities.Event_CapabilityEntity;
import net.dark_roleplay.drpcore.common.objects.events.entity.player.Event_PlayerClone;
import net.dark_roleplay.drpcore.common.objects.events.entity.player.Event_PlayerLoggedIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;

public class DRPCoreEvents {

	public static void preInit(FMLPreInitializationEvent event) {}

	public static void init(FMLInitializationEvent event) {
		// CLIENT ONLY
		if(event.getSide() == Side.CLIENT) {
			MinecraftForge.EVENT_BUS.register(new Event_Mouse());
			MinecraftForge.EVENT_BUS.register(new Event_BlockHighlight());
			MinecraftForge.EVENT_BUS.register(new Event_Tooltip());
			MinecraftForge.EVENT_BUS.register(new Event_GuiOpen());
		}else if(event.getSide() == Side.SERVER) {
		}
		// BOOTH

		MinecraftForge.EVENT_BUS.register(new Event_ConfigChange());
		MinecraftForge.EVENT_BUS.register(new Event_PlayerClone());
		MinecraftForge.EVENT_BUS.register(new Event_CapabilityEntity());
		MinecraftForge.EVENT_BUS.register(new Event_PlayerLoggedIn());
		MinecraftForge.EVENT_BUS.register(new Event_BlockBreak());
	}

	public static void postInit(FMLPostInitializationEvent event) {}
}
