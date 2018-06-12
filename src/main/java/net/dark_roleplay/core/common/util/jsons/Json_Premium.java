package net.dark_roleplay.core.common.util.jsons;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.dark_roleplay.core.common.util.premium.PremiumData;

public class Json_Premium {

	public static PremiumData readData(String jsonString){
		Gson gson = new Gson();
		JsonElement je = gson.fromJson(jsonString, JsonElement.class);
		JsonObject json = je.getAsJsonObject();
		
		String type;
		String registryName;
		String category;
		
		JsonElement json_pp = json.get("premium_points");
		JsonElement json_bought = json.get("bought");
		
		if(json_bought != null){
			JsonArray jsar_bought = json_bought.getAsJsonArray();
			long[] flags = new long[jsar_bought.size()];
			for(int i = 0; i < jsar_bought.size(); i++){
				flags[i] = jsar_bought.get(i).getAsLong();
			}
			
			return new PremiumData(json_pp == null ? 0 : json_pp.getAsInt(), flags);
		}
		
		return null;
	}
	
}
