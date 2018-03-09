package net.dark_roleplay.drpcore.modules.hud;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import net.dark_roleplay.drpcore.api.old.Modules;
import net.dark_roleplay.drpcore.api.old.util.DRPRegistries;
import net.dark_roleplay.drpcore.common.DRPCoreReferences;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.ProgressManager.ProgressBar;

public class HudLoader {
	
	public static void initializeHuds(){
		if(Modules.HUD.isEnabled()){
			List<Hud> huds = DRPRegistries.getHudRegistry().getValues();
			ProgressBar progressBar = ProgressManager.push("Loading HUD elements", 1);
				
			for(Hud hud : huds){
				ResourceLocation regName = hud.getRegistryName();
		        progressBar.step(regName.toString());
		        
		        File hudFolder = new File(DRPCoreReferences.DARK_ROLEPLAY_CONFIGS + "\\dark roleplay\\client\\huds");
		        File hudConfig = new File(hudFolder.getPath() + "\\" + regName.getResourcePath() + ".json");
		        hudFolder.mkdirs();
		        if(hudConfig.exists()){
		        	try(Reader reader = new FileReader(hudConfig.getPath())){
		        		JsonObject json = new JsonParser().parse(reader).getAsJsonObject();
		        		hud.readFromConfig(json);
		        	} catch (IOException e) {
						e.printStackTrace();
					}
		        }else{
		        	try(Writer writer = new FileWriter(hudConfig.getPath())) {
			            Gson gson = new GsonBuilder().setPrettyPrinting().create();
			            gson.toJson(hud.writeToDefaultConfig(), writer);
			        } catch (IOException e) {
						e.printStackTrace();
					}
		        }
			}
	        ProgressManager.pop(progressBar);
		}
	}
}
