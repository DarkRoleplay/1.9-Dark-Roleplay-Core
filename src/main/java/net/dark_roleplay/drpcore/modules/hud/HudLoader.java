package net.dark_roleplay.drpcore.modules.hud;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.dark_roleplay.drpcore.api.util.DRPRegistries;
import net.dark_roleplay.drpcore.api.util.sitting.Modules;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.ProgressManager.ProgressBar;

public class HudLoader {

	public static void initializeHuds(){
//		if(Modules.HUD.isEnabled()){
//			List<Hud> huds = DRPRegistries.getRegistryHUDs().getValues();
			ProgressBar progressBar = ProgressManager.push("Loading HUD elements", 1);
			
//			huds.add(new Hud(new ResourceLocation("drpcore:test")));
//			
//			for(Hud hud : huds){
			
			Hud hud = new Hud(new ResourceLocation("drpcore:test"));
				ResourceLocation regName = hud.getRegistryName();
		        progressBar.step(regName.toString());
		        
		        System.out.println("DEBUG");
		        System.out.println(DRPCoreInfo.DARK_ROLEPLAY_CONFIGS + "\\Output.json");
		        new File(DRPCoreInfo.DARK_ROLEPLAY_CONFIGS + "\\Dark Roleplay Core\\Client\\").mkdirs();
		        try (Writer writer = new FileWriter(DRPCoreInfo.DARK_ROLEPLAY_CONFIGS + "\\Dark Roleplay Core\\Client\\" + regName.getResourcePath() + ".json")) {
		            Gson gson = new GsonBuilder().setPrettyPrinting().create();
		            gson.toJson(hud.writeToDefaultConfig(), writer);
		        } catch (IOException e) {
					e.printStackTrace();
				}
		        
		        try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
//			}
	        ProgressManager.pop(progressBar);
//		}
	}
	
}
