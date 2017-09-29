package net.dark_roleplay.drpcore.common.handler;

import net.dark_roleplay.drpcore.client.events.player.Event_GuiOpen;
import net.dark_roleplay.drpcore.client.events.player.Event_Mouse;
import net.dark_roleplay.drpcore.client.events.player.Event_Tooltip;
import net.dark_roleplay.drpcore.client.events.rendering.Event_BlockHighlight;
import net.dark_roleplay.drpcore.client.events.rendering.Event_RenderHud;
import net.dark_roleplay.drpcore.client.events.ticks.Event_ClientTick;
import net.dark_roleplay.drpcore.common.events.blocks.Event_BlockBreak;
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
			MinecraftForge.EVENT_BUS.register(new Event_ClientTick());
			MinecraftForge.EVENT_BUS.register(new Event_GuiOpen());

			MinecraftForge.EVENT_BUS.register(new Event_Mouse());
			MinecraftForge.EVENT_BUS.register(new Event_BlockHighlight());
			MinecraftForge.EVENT_BUS.register(new Event_Tooltip());
			MinecraftForge.EVENT_BUS.register(new Event_RenderHud());
		}else if(event.getSide() == Side.SERVER) {
			
		}
		// BOOTH

		MinecraftForge.EVENT_BUS.register(new Event_BlockBreak());
	}

	public static void postInit(FMLPostInitializationEvent event) {}
}
