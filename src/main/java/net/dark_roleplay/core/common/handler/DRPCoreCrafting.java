package net.dark_roleplay.core.common.handler;

import net.dark_roleplay.core.api.old.crafting.simple_recipe.RecipeCategory;
import net.dark_roleplay.core.api.old.crafting.simple_recipe.SimpleRecipe;
import net.dark_roleplay.core.api.old.crafting.simple_recipe.StackList;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class DRPCoreCrafting {

	public static void init(FMLInitializationEvent event) {
		RecipeCategory cat = new RecipeCategory("test");
		
		cat.add(
			new SimpleRecipe(new ResourceLocation("drpcore:test1"), new StackList(new ItemStack(Items.APPLE), new ItemStack(Items.SADDLE)).asArr(), new StackList(new ItemStack(Items.BAKED_POTATO)).asArr()),
			new SimpleRecipe(new ResourceLocation("drpcore:test2"), new StackList(new ItemStack(Items.BED)).asArr(), new StackList(new ItemStack(Items.BAKED_POTATO), new ItemStack(Items.BEETROOT, 3)).asArr()),
			new SimpleRecipe(new ResourceLocation("drpcore:test3"), new StackList(new ItemStack(Items.ACACIA_DOOR)).asArr(), new StackList(new ItemStack(Items.BOAT)).asArr()),
			new SimpleRecipe(new ResourceLocation("drpcore:test4"), new StackList(new ItemStack(Items.BEEF)).asArr(), new StackList(new ItemStack(Items.ARROW)).asArr())
		);
		
		
//		CraftingRegistry.register(cat);;
	}
	
}
