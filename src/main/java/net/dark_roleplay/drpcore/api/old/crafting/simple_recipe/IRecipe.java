package net.dark_roleplay.drpcore.api.old.crafting.simple_recipe;

import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public interface IRecipe{

	public String getRegistryString();

	public ResourceLocation getRegistryName();
	
	public boolean requiresUnlock();

	public ItemStack[] getDisplayItems();
	
	public boolean canCraft(EntityPlayer player);
	
	public class Impl implements IRecipe{
		
		protected ResourceLocation registryName;
		protected boolean requiresUnlock = false;
		protected ItemStack[] displayItems;
		
		@Override
		public String getRegistryString(){
			return this.registryName.toString();
		}

		@Override
		public ResourceLocation getRegistryName() {
			return registryName;
		}

		@Override
		public boolean requiresUnlock(){
			return requiresUnlock;
		}

		@Override
		public ItemStack[] getDisplayItems() {
			return displayItems;
		}
		
		@Override
		public boolean canCraft(EntityPlayer player){
			return (this.requiresUnlock && player.getCapability(DRPCoreCapabilities.DRPCORE_RECIPE_CONTROLLER, null).isRecipeUnlocked(this.registryName.toString()) || !this.requiresUnlock);
		}
	}
	
}
