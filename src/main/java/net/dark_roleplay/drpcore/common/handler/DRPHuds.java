package net.dark_roleplay.drpcore.common.handler;

import java.util.Calendar;

import net.dark_roleplay.drpcore.api.old.modules.hud.Hud;
import net.dark_roleplay.drpcore.common.References;
import net.dark_roleplay.drpcore.common.objects.blocks.blueprint_controller.BlueprintController;
import net.dark_roleplay.drpcore.common.objects.huds.VariationSelection;
import net.dark_roleplay.drpcore.common.objects.huds.RealTimeClock;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class DRPHuds {

	@SubscribeEvent
	public static final void register(RegistryEvent.Register<Hud> event) {
		event.getRegistry().registerAll(
			new RealTimeClock(new ResourceLocation(References.MODID, "real_time_clock")),
			new VariationSelection(new ResourceLocation(References.MODID, "variation_selection"))
		);
	}
	
}
