package net.dark_roleplay.drpcore.common.crafting.simple_recipe;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;

import net.dark_roleplay.drpcore.api.crafting.simple_recipe.SimpleRecipe;
import net.dark_roleplay.drpcore.api.schematic.Schematic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class SimpleRecipeSerializationUtil {

	public static NBTTagCompound recipeToNBT(SimpleRecipe recipe){
		NBTTagCompound tag = new NBTTagCompound();
		
		tag.setString("registry_name", recipe.getRegistryString());
		tag.setBoolean("requires_unlock", recipe.requiresUnlock());
		
		//Ingredients
		ItemStack[] ingredients = recipe.getMainIngredients();
		NBTTagList ingredientList = new NBTTagList();
		for(int i = 0; i < ingredients.length; i++){
			NBTTagCompound stack = new NBTTagCompound();
			stack = ingredients[i].writeToNBT(stack);
			ingredientList.appendTag(stack);
		}
		tag.setByte("ingredients_size", (byte) ingredients.length);
		tag.setTag("ingredients", ingredientList);
		
		//Ingredients
		ItemStack[] outputs = recipe.getMainOutput();
		NBTTagList outputList = new NBTTagList();
		for(int i = 0; i < outputs.length; i++){
			NBTTagCompound stack = new NBTTagCompound();
			stack = outputs[i].writeToNBT(stack);
			outputList.appendTag(stack);
		}
		tag.setByte("outputs_size", (byte) outputs.length);
		tag.setTag("outputs", outputList);
		
		return tag;
	}
	
	public static void writeToFile(OutputStream os, SimpleRecipe recipe){
		try {
			CompressedStreamTools.writeCompressed(recipeToNBT(recipe), os);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static NBTTagCompound readFromFile(InputStream is){
		NBTTagCompound tag;
		try {
			tag = CompressedStreamTools.readCompressed(is);
			return tag;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
