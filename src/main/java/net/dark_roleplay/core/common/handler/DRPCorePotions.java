package net.dark_roleplay.core.common.handler;

import net.dark_roleplay.core.References;
import net.dark_roleplay.core.common.objects.potions.PotionDrunk;
import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;

@ObjectHolder("drpcore")
@Mod.EventBusSubscriber(modid = References.MODID)
public class DRPCorePotions  {
		
	public static final Potion DRUNK = null;
	 
	@SubscribeEvent
	public static void register(RegistryEvent.Register<Potion> event) {
		event.getRegistry().register(new PotionDrunk().setRegistryName("drunk"));
	}
}
