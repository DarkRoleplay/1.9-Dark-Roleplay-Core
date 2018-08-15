package net.dark_roleplay.core.api.old.util;

import net.dark_roleplay.core.References;
import net.dark_roleplay.core.modules.hud.Hud;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryBuilder;

@Mod.EventBusSubscriber
public class DRPRegistries {

	private static IForgeRegistry<Hud> registryHUDs;
	
	@SubscribeEvent
	public static final void register(RegistryEvent.NewRegistry event) {

		RegistryBuilder<Hud> rbHuds = new RegistryBuilder<Hud>();
		rbHuds.setName(new ResourceLocation(References.MODID, "huds"));
		rbHuds.setType(Hud.class);
		rbHuds.disableSaving();
		registryHUDs = rbHuds.create();
		
	}
	
	public static IForgeRegistry<Hud> getHudRegistry() {
		return registryHUDs;
	}
}
