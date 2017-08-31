package net.dark_roleplay.drpcore.common.crafting;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import net.dark_roleplay.drpcore.api.crafting.simple_recipe.IRecipe;
import net.dark_roleplay.drpcore.api.crafting.simple_recipe.RecipeCategory;
import net.dark_roleplay.drpcore.api.crafting.simple_recipe.SimpleCrafter;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;

public class CraftingRegistry {

	private static HashMap<ResourceLocation, IRecipe> recipes = new HashMap<ResourceLocation, IRecipe>();
	private static HashMap<Block, List<RecipeCategory>> categorys = new HashMap<Block, List<RecipeCategory>>();
	
	//ID Sync
	private static BiMap<Integer, ResourceLocation> biMap = HashBiMap.create();
	private static int registrySize;


	public static SimpleCrafter SIMPLE_CRAFTER_INSTANCE;
	
	static{
		
		SIMPLE_CRAFTER_INSTANCE = new SimpleCrafter();
		
	}
	
	public static void createIdMap(){
		Iterator<ResourceLocation> it = recipes.keySet().iterator();
		int id = 0;
		while(it.hasNext()){
			biMap.put(id, it.next());
		};
	}
	
	public static void register(RecipeCategory category){
		if(categorys.containsKey(category.getCraftingStation())){
			List<RecipeCategory> cats = categorys.get(category.getCraftingStation());
			cats.add(category);
			categorys.replace(category.getCraftingStation(), cats);
		}else
			categorys.put(category.getCraftingStation(), new ArrayList<RecipeCategory>(){{add(category);}});
		for(IRecipe recipe : category.getRecipes()){
			recipes.put(recipe.getRegistryName(), recipe);
		}
	}
	
	//---------------------------- UTILITY ----------------------------//
	
	public static List<RecipeCategory> getCategorysForBlocks(Block station){
		if(categorys.containsKey(station)){
			return categorys.get(station);
		}
		return null;
	}
	
    //---------------------------- ID Map (De-)/Serialization ----------------------------//
	public static void readIdMap(NBTTagCompound tag){
		NBTTagList list = (NBTTagList) tag.getTag("id_map");
		int size = list.tagCount();
		registrySize = size;
		for(int i = 0; i < size; i++){
			biMap.put(i, new ResourceLocation(list.getStringTagAt(i)));
		}
		
		if(registrySize < recipes.size()){
			Iterator<ResourceLocation> it = recipes.keySet().iterator();
			while(it.hasNext()){
				ResourceLocation rs = it.next();
				if(!biMap.containsValue(rs)){
					biMap.put(registrySize + 1, rs);
					registrySize++;
				}
			};
		}
	}
	
	public static NBTTagCompound writeIdMap(){
		NBTTagCompound tag = new NBTTagCompound();
		NBTTagList list = new NBTTagList();
		for(int i = 0; i < biMap.size(); i++){
			list.appendTag(new NBTTagString(biMap.get(i).toString()));
		}
		tag.setTag("id_map", list);
		
		return tag;
	}
	
	public static Collection<IRecipe> getRecipes(){
		return recipes.values();
	}
	
	public static IRecipe getRecipe(String registryString){
		if(recipes.containsKey(new ResourceLocation(registryString))){
			return recipes.get(new ResourceLocation(registryString));
		}else{
			return null;
		}
	}
	
	public static List<String> getRecipeNames(){
		Iterator<IRecipe> recipes = getRecipes().iterator();
		List<String> rec = new ArrayList<String>();
		
		while(recipes.hasNext()){
			rec.add(recipes.next().getRegistryString());
		}
		return rec;
	}
}
