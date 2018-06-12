package net.dark_roleplay.core.common.objects.events.entity.player;

import net.dark_roleplay.core.common.References;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = References.MODID)
public class Event_PlayerClone {

	
	@SubscribeEvent
	public static void onConfigChanged(PlayerEvent.Clone event) {
		if(event.isWasDeath()){
//			IRecipeController recipesOld = event.getOriginal().getCapability(DRPCoreCapabilities.DRPCORE_RECIPE_CONTROLLER, null); 
//			IRecipeController recipesNew = event.getEntityPlayer().getCapability(DRPCoreCapabilities.DRPCORE_RECIPE_CONTROLLER, null); 
////			ISkillController skillsOld = event.getOriginal().getCapability(DRPCoreCapabilities.DRPCORE_SKILL_CONTROLLER, null);
////			ISkillController skillsNew = event.getEntityPlayer().getCapability(DRPCoreCapabilities.DRPCORE_SKILL_CONTROLLER, null);
//			
//			for(String locked : recipesOld.getLockedRecipes()){
//				recipesNew.lockRecipe(locked);
//			}
//			
//			for(String unlocked : recipesOld.getUnlockedRecipes()){
//				recipesNew.unlockRecipe(unlocked);
//			}
//			
//			Map<String, Float> progressedRecipes = recipesOld.getProgressedRecipes();
//			for(String progressed : progressedRecipes.keySet()){
//				recipesNew.progressRecipe(progressed, progressedRecipes.get(progressed));
//			}
			
//			skillsNew.unlockSkills(skillsOld.getUnlockedSkills());
//			skillsNew.addPoints(skillsOld.getSkillPoints());
			
		}
	}
}
