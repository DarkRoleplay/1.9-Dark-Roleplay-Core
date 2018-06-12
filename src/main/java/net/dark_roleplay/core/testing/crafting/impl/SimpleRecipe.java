package net.dark_roleplay.core.testing.crafting.impl;

import java.util.Random;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.dark_roleplay.core.testing.crafting.IIcon;
import net.dark_roleplay.core.testing.crafting.IIngredient;
import net.dark_roleplay.core.testing.crafting.Recipe;
import net.dark_roleplay.core.testing.crafting.factories.RecipeFactory;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;

public class SimpleRecipe extends Recipe{

	IIcon icon;
	ItemStack[] outputs;
	IIngredient[] ingredients;
	
	
	public SimpleRecipe(String registryName, ItemStack[] outputs, IIngredient[] ingredients, boolean isModded) {
		super(registryName, isModded);
		this.outputs = outputs;
		this.ingredients = ingredients;
	}

	@Override
	public IIcon getIcon() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAllowedCraftingStation(Block station) {
		return station == Blocks.AIR || new Random().nextBoolean();
	}

	@Override
	public void setCraftingStations(Block[] stations) {
		// TODO Auto-generated method stub
		
	}
	
	public static class Factory extends RecipeFactory{

		@Override
		public Recipe readRecipe(Boolean isModded, JsonObject obj) {
			
			ResourceLocation registryName = new ResourceLocation(JsonUtils.getString(obj, "name", "drpcore:invalid"));
			
			if(registryName.toString().equals("drpcore:invalid"))
				return null;
			
			JsonArray outputs = obj.get("outputs").getAsJsonArray();
			
			return new SimpleRecipe(obj.get("name").getAsString(), new ItemStack[0], new IIngredient[0], isModded);
		}
		
	}
}
