package net.dark_roleplay.drpcore.common.events.entity.player;

import java.util.Map;

import net.dark_roleplay.drpcore.common.capabilities.player.crafting.IRecipeController;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.common.handler.DRPCorePackets;
import net.dark_roleplay.drpcore.common.network.packets.crafting.SyncPlayerRecipeState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;

public class Event_PlayerLoggedIn {

	@SubscribeEvent
	public void handleEvent(EntityJoinWorldEvent event) {
		
		if(event.getEntity() instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
			IRecipeController rpC = player.getCapability(DRPCoreCapabilities.DRPCORE_RECIPE_CONTROLLER, null);
			
			for(String recipe : rpC.getUnlockedRecipes()){
				DRPCorePackets.sendTo(new SyncPlayerRecipeState(recipe,0,0F), player);
			}
			for(String recipe : rpC.getLockedRecipes()){
				DRPCorePackets.sendTo(new SyncPlayerRecipeState(recipe,1,0F), player);
			}
			Map<String,Float> progressed = rpC.getProgressedRecipes();
			for(String recipe : progressed.keySet()){
				DRPCorePackets.sendTo(new SyncPlayerRecipeState(recipe,2,progressed.get(recipe)), player);
			}
		}
	}
	
}
