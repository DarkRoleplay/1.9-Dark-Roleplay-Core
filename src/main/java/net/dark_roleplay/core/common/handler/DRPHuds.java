package net.dark_roleplay.core.common.handler;

import net.dark_roleplay.core.common.References;
import net.dark_roleplay.core.common.objects.huds.RealTimeClock;
import net.dark_roleplay.core.common.objects.huds.VariationSelection;
import net.dark_roleplay.core.modules.hud.Hud;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = References.MODID)
public class DRPHuds {

	@SubscribeEvent
	public static final void register(RegistryEvent.Register<Hud> event) {
		event.getRegistry().registerAll(
			new RealTimeClock(new ResourceLocation(References.MODID, "real_time_clock"))
//			new VariationSelection(new ResourceLocation(References.MODID, "variation_selection"))
		);
	}
	
}
