package net.dark_roleplay.drpcore.api.events.player;

import net.dark_roleplay.drpcore.common.crafting.simple_recipe.SimpleRecipe;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.eventhandler.Event;

public class Event_PlayerCraft extends Event{

	EntityPlayer player;
	SimpleRecipe recipe;
	
	public Event_PlayerCraft(EntityPlayer player, SimpleRecipe recipe){
		this.player = player;
		this.recipe = recipe;
	}

	public EntityPlayer getPlayer() {
		return player;
	}

	public SimpleRecipe getRecipe() {
		return recipe;
	}
}
