package net.dark_roleplay.drpcore.modules.crafting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import net.dark_roleplay.drpcore.api.old.crafting.simple_recipe.RecipeCategory;
import net.dark_roleplay.drpcore.api.old.crafting.simple_recipe.SimpleRecipe;
import net.dark_roleplay.drpcore.common.crafting.CraftingRegistry;
import net.dark_roleplay.drpcore.modules.Module;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class Module_Crafting extends Module{

	List<String> modids = new ArrayList<String>();
	
	HashMap<String, RecipeCategory> categorys = new HashMap<String, RecipeCategory>();
	
	public Module_Crafting(String name) {
		super(name);
	}
	
	public void addMod(String modid){
		modids.add(modid);
	}

	public void init(FMLInitializationEvent event){
		File modFolder = new File("./mods/");
		
		for(File jar : modFolder.listFiles()){
			if(jar.isFile()){
				if(jar.getName().endsWith(".jar")){
					try {
						InputStream[] streams = getRecipeFiles(new URL("jar:file:/" + jar.getCanonicalPath() + "!/data/drp/recipes/"));
						for(InputStream stream : streams){
							readRecipe(getResourceAsJson(stream));
						}
					}catch (URISyntaxException|IOException e) {
						e.printStackTrace();
					}
					
				}
			}
		}
		
		for(String key : categorys.keySet()){
			CraftingRegistry.register(categorys.get(key));
		}
	}
	
	public void readRecipe(JsonObject obj){
		if(obj == null)
			return;
		
		SimpleRecipe rec;
		ItemStack[] output;
		JsonArray outputArr = obj.get("outputs").getAsJsonArray();
		output = new ItemStack[outputArr.size()];
		for(int i = 0; i < outputArr.size(); i++){
			output[i] = deserializeItem(outputArr.get(i).getAsJsonObject());
		}

		ItemStack[] input;
		JsonArray inputArr = obj.get("ingredients").getAsJsonArray();
		input = new ItemStack[inputArr.size()];
		for(int i = 0; i < inputArr.size(); i++){
			input[i] = deserializeItem(inputArr.get(i).getAsJsonObject());
		}
		
		rec = new SimpleRecipe(new ResourceLocation("custom:" + obj.get("name").getAsString()),output, input);
		
		String station = obj.has("station") ? obj.get("station").getAsString() : "minecraft:air";
		
		Block block = Block.REGISTRY.getObject(new ResourceLocation(station));
		
        if (block == null) {
        	block = Blocks.AIR;
        }
		
		String category = station.replace(":", ".") + "." + obj.get("category").getAsString();
		if(categorys.containsKey(category)){
			categorys.get(category).add(rec);
		}else{
			RecipeCategory cat = new RecipeCategory(block, category);
			cat.add(rec);
			categorys.put(category, cat);
		}
	}
	
	public static JsonObject getResourceAsJson(InputStream is ){
		Gson gson = new Gson();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		try{
			JsonObject je = gson.fromJson(reader, JsonObject.class);
			return je;
		}catch(Exception e){
			
		}
		return null;
	}
	
	public static ItemStack deserializeItem(JsonObject stackObj) {
        String s = stackObj.get("item").getAsString();
        Item item = Item.REGISTRY.getObject(new ResourceLocation(s));

        if (item == null) {
//            throw new JsonSyntaxException("Unknown item '" + s + "'");
        	return ItemStack.EMPTY;
        }else{
            int i = stackObj.has("data") ? stackObj.get("data").getAsInt() : 0;
            int j = stackObj.has("count") ? stackObj.get("count").getAsInt() : 1;
            return new ItemStack(item, j, i);
        }
    }
	
	InputStream[] getRecipeFiles(URL url) throws URISyntaxException, IOException{
	        String jarPath = url.getPath().substring(5, url.getPath().indexOf("!"));
	        JarFile jar = new JarFile(URLDecoder.decode(jarPath, "UTF-8"));
	        Enumeration<JarEntry> entries = jar.entries();
	        Set<InputStream> result = new HashSet<InputStream>();
	        while(entries.hasMoreElements()) {
	          String name = entries.nextElement().getName();

	          if (name.startsWith("data/drp/recipes/")) {
	        	  if(name.endsWith("data/drp/recipes/"))
	        			  continue;
	        	  result.add(jar.getInputStream(jar.getJarEntry(name)));
	          }
	        }
//	        jar.close();
	        return result.toArray(new InputStream[result.size()]);
	  }
}
