package net.dark_roleplay.core.client.events.player;

import java.util.Random;

import net.dark_roleplay.core.common.References;
import net.dark_roleplay.core.common.handler.DRPCorePotions;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.MovementInput;
import net.minecraftforge.client.event.InputUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(value = Side.CLIENT, modid = References.MODID)
public class Event_MovementInput {

	private static float totalMovement = 0f;
	
	//TODO IMPLEMENT DRUNKEN EFFECT
	
	@SubscribeEvent
	public static void moveInput(InputUpdateEvent e) {
		PotionEffect effect = e.getEntityPlayer().getActivePotionEffect(DRPCorePotions.DRUNK);
		if(effect != null) {
			int amplifier = effect.getAmplifier() + 5;
			

			MovementInput input = e.getMovementInput();
			
		}
	}
	
}
