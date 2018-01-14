package net.dark_roleplay.drpcore.modules.crafting;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.dark_roleplay.drpcore.modules.Module;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

public class Module_Crafting extends Module{

	List<String> modids = new ArrayList<String>();
	
	public Module_Crafting(String name) {
		super(name);
	}
	
	public void addMod(String modid){
		modids.add(modid);
	}

	public void init(FMLInitializationEvent event){
		for(String modid : modids){
			URL path = this.getClass().getResource("/assets/" + modid + "/recipes/");
			try {
				File fl = new File(path.toURI());
				for(File file : fl.listFiles()){
					generateCategories(file.getName().replace(".json", ""), getResourceAsJson(new FileInputStream(file)));
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void generateCategories(String blockID, JsonArray arr){
		System.out.println(blockID);
		
	}
	
	public static JsonArray getResourceAsJson(InputStream is ){
		Gson gson = new Gson();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		JsonArray je = gson.fromJson(reader, JsonArray.class);
		return je;
	}
}
