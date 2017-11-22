package net.dark_roleplay.drpcore.common.handler;

import java.util.Calendar;

import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.dark_roleplay.drpcore.common.objects.blocks.blueprint_controller.BlueprintController;
import net.dark_roleplay.drpcore.common.objects.huds.RealTimeClock;
import net.dark_roleplay.drpcore.modules.hud.Hud;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class DRPHuds {

	@SubscribeEvent
	public static final void register(RegistryEvent.Register<Hud> event) {
		event.getRegistry().register(
			new RealTimeClock(new ResourceLocation(DRPCoreInfo.MODID, "real_time_clock"))
		);
	}
	
}
