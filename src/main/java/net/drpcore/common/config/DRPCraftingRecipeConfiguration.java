package net.drpcore.common.config;

import java.io.File;
import java.util.ArrayList;

import net.drpcore.common.util.crafting.CraftingRecipe;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.Configuration;

public class DRPCraftingRecipeConfiguration {

	public static void writeMainRecipe(CraftingRecipe recipe){
		Block station = recipe.getStation();
		ItemStack DefaultOutput = recipe.getOutput(null);
		ItemStack[] mainInput = recipe.getMainIngredients();
		ItemStack[] additionalInput = recipe.getAdditionalIngredients();
		
		Configuration CRCategoryConfig = new Configuration(new File(ConfigurationManager.CRecipeConfigFolder + "\\" + station.getUnlocalizedName().split("[.]")[1] + ".cfg"));
		
		//Creating Categorys
		ConfigCategory stationCategory = new ConfigCategory("crafting_station");
		
		ConfigCategory recipesCategory = new ConfigCategory("recipes");
		
		ConfigCategory categoryCategory = new ConfigCategory(recipe.getCategory(),recipesCategory);
		
		ConfigCategory recipeCategory = new ConfigCategory(DefaultOutput.getDisplayName(), categoryCategory);
		
		ConfigCategory defaultOutputCategory = new ConfigCategory("default_output", recipeCategory);
		
		ConfigCategory primaryInputCategory = new ConfigCategory("primar_input", recipeCategory);
		
		ConfigCategory secondaryInputCategory = new ConfigCategory("secondary_input", recipeCategory);
		
		ConfigCategory dependingOutputCategory = new ConfigCategory("depending_output", recipeCategory);

		CRCategoryConfig.load();
		
		//Writing Station Name to Config
		String stationName = station.getRegistryName().toString();
		
		CRCategoryConfig.get("crafting_station_name", "station", station.getRegistryName().toString());
		
		//Writing Recipe
		
		//IsEnabled
		CRCategoryConfig.get(categoryCategory.getQualifiedName(), "is_enabled", true);
		
		//Default Output
		CRCategoryConfig.get(defaultOutputCategory.getQualifiedName(), "name", DefaultOutput.getItem().getRegistryName().toString());
		CRCategoryConfig.get(defaultOutputCategory.getQualifiedName(), "amount", DefaultOutput.stackSize);
		CRCategoryConfig.get(defaultOutputCategory.getQualifiedName(), "metadata", DefaultOutput.getMetadata());
		
		//Primary Ingredients
		for(int i = 0; i < mainInput.length; i++){
			CRCategoryConfig.get(primaryInputCategory.getQualifiedName() + "." + i , "name", mainInput[i].getItem().getRegistryName().toString());
			CRCategoryConfig.get(primaryInputCategory.getQualifiedName() + "." + i , "amount", mainInput[i].stackSize);
			CRCategoryConfig.get(primaryInputCategory.getQualifiedName() + "." + i , "metadata", mainInput[i].getMetadata());
		}
		
		//Secondary Ingredients
		if(additionalInput != null)
			for(int i = 0; i < additionalInput.length; i++){
				CRCategoryConfig.get(secondaryInputCategory.getQualifiedName() + "." + i , "name", additionalInput[i].getItem().getRegistryName().toString());
				CRCategoryConfig.get(secondaryInputCategory.getQualifiedName() + "." + i , "amount", additionalInput[i].stackSize);
				CRCategoryConfig.get(secondaryInputCategory.getQualifiedName() + "." + i , "metadata", additionalInput[i].getMetadata());		
			}
		
		//Depending Output
		for(int i = 1 ; i < (int) Math.pow(2, additionalInput.length); i++){
			ArrayList<ItemStack> needetIngredients = new ArrayList<ItemStack>();
			int j = i;
			int k = 0;
			while(j > 0){
				if(j % 2 == 1)
					needetIngredients.add(additionalInput[k]);
				k++;
				j = j/2;
			}
			ItemStack[] usedIngredients = new ItemStack[needetIngredients.size()];
			usedIngredients = needetIngredients.toArray(usedIngredients);
		    ItemStack dependingOutput = recipe.getOutput(usedIngredients);
		    if(!dependingOutput.equals(DefaultOutput) && dependingOutput != null){
		    	String writtenIngredients = null;
		    	for(ItemStack items: usedIngredients){
		    		if(writtenIngredients == null){
		    			writtenIngredients = items.getItem().getRegistryName().toString();
		    		}else{
		    			writtenIngredients = writtenIngredients + "&" + items.getItem().getRegistryName();
		    		}
		    	}
		    	
		    	CRCategoryConfig.get(dependingOutputCategory.getQualifiedName() + "." + i , "needetIngredients", writtenIngredients);
		    	CRCategoryConfig.get(dependingOutputCategory.getQualifiedName() + "." + i , "name", dependingOutput.getItem().getRegistryName().toString());
				CRCategoryConfig.get(dependingOutputCategory.getQualifiedName() + "." + i , "amount", dependingOutput.stackSize);
				CRCategoryConfig.get(dependingOutputCategory.getQualifiedName() + "." + i , "metadata", dependingOutput.getMetadata());		
			
		    }
		}
		
		CRCategoryConfig.save();
	}
	
}
