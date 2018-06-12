package net.dark_roleplay.core.api.old.modules.work_in_progress.premium;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.dark_roleplay.core.api.old.modules.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ProgressManager;
import net.minecraftforge.fml.common.ProgressManager.ProgressBar;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

public class Module_Premium extends Module{

	public Module_Premium(String name) {
		super(name);
	}

	private static PremiumAddon[] addons;
	
	private static HashMap<UUID, Equiped> equiped = new HashMap<UUID, Equiped>();

	private static final byte version = 0;
	
	public void postInit(FMLPostInitializationEvent event){
//		if(event.getSide().isServer())
//			return;
//		
//		ProgressBar progressBar = ProgressManager.push("Gadgets", 2);
//
//		progressBar.step("Loading Gadget List");
//		JsonObject json = objFromLink("http://dark-roleplay.net/premium_addons/request.php?version=" + version + "&type=addon_list");
//
//
//		progressBar.step("Generating Gadgets");
//		JsonArray jsonAddons = json.get("addons").getAsJsonArray();
//		addons = new PremiumAddon[jsonAddons.size()];
//		for(int i = 0; i < jsonAddons.size(); i++){
//			JsonObject addon = jsonAddons.get(i).getAsJsonObject();
//			addons[addon.get("id").getAsInt()] = new PremiumAddon(addon.get("id").getAsInt(), addon.get("name").getAsString(), addon.get("price").getAsInt(), addon.get("desc").getAsString());
//		}
//		ProgressManager.pop(progressBar);
	}
	
	public PremiumAddon[] getAddons(){
		return addons;
	}
	
	public static Equiped getEquiped(EntityPlayer player){
		if(equiped.containsKey(player.getPersistentID())){
			return equiped.get(player.getPersistentID());
		}
		return null;
	}
	
	public static void setEquiped(EntityPlayer player, int addon, int style){
//		equiped.put(player.getPersistentID(), new Equiped(addons.get(addon), style));
	}
	
	public static void initializeAddon(int addon){
		JsonObject json = objFromLink("http://dark-roleplay.net/premium_addons/request.php?type=addon&id=" + addon);
		
		String name = json.get("name").getAsString();
		int	position = json.get("position").getAsInt();
		int price = json.get("price").getAsInt();
		String description = json.get("description").getAsString();
		String modelURL = json.get("model_url").getAsString();
		JsonArray textures = json.get("texture_urls").getAsJsonArray();
		
		String[] styles = new String[textures.size()];
		
		for(int i = 0; i < styles.length; i++){
			styles[i] = textures.get(i).getAsString();
		}
		
//		addons.replace(addon, new PremiumAddon(addon, position, name, price, description, new Attachment_Premium().loadFromJson(objFromLink("http://dark-roleplay.net/premium_addons/models/" + modelURL + ".json")), styles));
	}
	
	private static JsonObject objFromLink(String url){
		try {
			Gson gson = new Gson();
			InputStream in = new URL(url).openStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			JsonElement je = gson.fromJson(reader, JsonElement.class);
			JsonObject json = je.getAsJsonObject();
			return json;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static class Equiped{
		PremiumAddon equiped;
		int style;
		
		public Equiped(PremiumAddon equiped){
			this(equiped, 0);
		}
		
		public Equiped(PremiumAddon equiped, int style){
			this.equiped = equiped;
			this.style = style;
		}
		
		public void getResourceLocation(){
			
		}
		
		public PremiumAddon getEquiped(){
			return this.equiped;
		}
	}
}
