package net.dark_roleplay.drpcore.common.objects.events.entity.player;

import java.util.Map;

import net.dark_roleplay.drpcore.common.capabilities.player.crafting.IRecipeController;
import net.dark_roleplay.drpcore.common.capabilities.player.skill.ISkillController;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Event_PlayerClone {

	
	@SubscribeEvent
	public void onConfigChanged(PlayerEvent.Clone event) {
		if(event.isWasDeath()){
			IRecipeController recipesOld = event.getOriginal().getCapability(DRPCoreCapabilities.DRPCORE_RECIPE_CONTROLLER, null); 
			IRecipeController recipesNew = event.getEntityPlayer().getCapability(DRPCoreCapabilities.DRPCORE_RECIPE_CONTROLLER, null); 
			ISkillController skillsOld = event.getOriginal().getCapability(DRPCoreCapabilities.DRPCORE_SKILL_CONTROLLER, null);
			ISkillController skillsNew = event.getEntityPlayer().getCapability(DRPCoreCapabilities.DRPCORE_SKILL_CONTROLLER, null);
			
			for(String locked : recipesOld.getLockedRecipes()){
				recipesNew.lockRecipe(locked);
			}
			
			for(String unlocked : recipesOld.getUnlockedRecipes()){
				recipesNew.unlockRecipe(unlocked);
			}
			
			Map<String, Float> progressedRecipes = recipesOld.getProgressedRecipes();
			for(String progressed : progressedRecipes.keySet()){
				recipesNew.progressRecipe(progressed, progressedRecipes.get(progressed));
			}
			
//			skillsNew.unlockSkills(skillsOld.getUnlockedSkills());
//			skillsNew.addPoints(skillsOld.getSkillPoints());
			
		}
	}
}
