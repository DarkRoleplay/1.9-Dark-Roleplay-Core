package net.dark_roleplay.drpcore.common.util.jsons;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.dark_roleplay.drpcore.api.old.models.TexturedQuad;
import net.dark_roleplay.drpcore.api.old.models.entity.model.Model;
import net.dark_roleplay.drpcore.common.util.premium.PremiumData;
import net.minecraft.client.model.PositionTextureVertex;

public class Json_Model {

	public static Model readData(String jsonString){
		Gson gson = new Gson();
		JsonElement je = gson.fromJson(jsonString, JsonElement.class);
//		JsonObject json = je.getAsJsonObject();
		
		JsonArray json = je.getAsJsonArray();
		
		TexturedQuad[] quads = new TexturedQuad[json.size()];
		
		for(int i = 0; i < json.size(); i++){
			JsonArray vertexJson = json.get(i).getAsJsonArray();
			
			JsonArray[] vertexes = new JsonArray[]{vertexJson.get(0).getAsJsonArray(), vertexJson.get(1).getAsJsonArray(), vertexJson.get(2).getAsJsonArray() ,vertexJson.get(3).getAsJsonArray()};
			
			PositionTextureVertex[] verts = new PositionTextureVertex[4];
			
			for(int j = 0; j < 4; j++){
				verts[j] = new PositionTextureVertex(
						vertexes[j].get(0).getAsFloat(),
						vertexes[j].get(1).getAsFloat(),
						vertexes[j].get(2).getAsFloat(),
						vertexes[j].get(3).getAsFloat(),
						vertexes[j].get(4).getAsFloat()
						);
			}
			
			quads[i] = new TexturedQuad(verts, 16, 16);
			
		}
		
		return new Model(quads);
	}
	
}
