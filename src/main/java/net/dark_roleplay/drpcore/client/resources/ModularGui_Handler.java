package net.dark_roleplay.drpcore.client.resources;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.dark_roleplay.drpcore.client.ClientProxy;
import net.dark_roleplay.drpcore.client.ModularGui_Template;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.util.ResourceLocation;

public class ModularGui_Handler implements IResourceManagerReloadListener {
	
	@Override
	public void onResourceManagerReload(IResourceManager manager) {	
		DRPCoreInfo.LOGGER.info("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");
		DRPCoreInfo.LOGGER.info("Starting to load Dark Roleplay modular guis");
		DRPCoreInfo.LOGGER.info("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");
		
//		ClientProxy.modularGuis.add();
		ClientProxy.currentGui = loadModularGui(new ResourceLocation("drpcore:textures/guis/modular_gui/vanilla.json"));//ClientProxy.modularGuis.get(0);

		DRPCoreInfo.LOGGER.info("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");
		DRPCoreInfo.LOGGER.info("Finished loading of Dark Roleplay modular guis");
		DRPCoreInfo.LOGGER.info("+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=");
	}
	
	public static ModularGui_Template loadModularGui(ResourceLocation loc){
		InputStream in;
		try {
			Gson gson = new Gson();
			in = Minecraft.getMinecraft().getResourceManager().getResource(loc).getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			JsonElement je = gson.fromJson(reader, JsonElement.class);
			JsonObject json = je.getAsJsonObject();
			
			JsonElement version_json = json.get("version");
			JsonElement name_json = json.get("name");
			JsonObject background_json = json.getAsJsonObject("background");
			
			JsonElement path = background_json.get("path");
			JsonElement path_hollow = background_json.get("path_hollow");
			JsonElement left_json = background_json.get("left");
			JsonElement right_json = background_json.get("right");
			JsonElement top_json = background_json.get("top");
			JsonElement bottom_json = background_json.get("bottom");


			if(version_json != null && name_json != null && background_json != null){
				if(path != null && path_hollow != null && left_json != null && right_json != null && top_json != null && bottom_json != null){
					System.out.println(name_json.getAsString());
					System.out.println(path.getAsString());
					System.out.println(path_hollow.getAsString());
					System.out.println(left_json.getAsInt());
					System.out.println(right_json.getAsInt());
					System.out.println(top_json.getAsInt());
					System.out.println(bottom_json.getAsInt());

					return new ModularGui_Template(
							name_json.getAsString(),
							new ResourceLocation(path.getAsString()),
							new ResourceLocation(path_hollow.getAsString()),
							left_json.getAsInt(), right_json.getAsInt(),
							top_json.getAsInt(), bottom_json.getAsInt()
						);
				}
			}
			
			
		} catch (IOException e) {
			DRPCoreInfo.LOGGER.catching(e);
		}
		return null;
	}
}
