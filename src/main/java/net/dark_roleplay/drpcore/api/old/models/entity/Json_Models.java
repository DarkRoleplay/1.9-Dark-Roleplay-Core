package net.dark_roleplay.drpcore.api.old.models.entity;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.dark_roleplay.drpcore.client.ClientProxy;
import net.minecraft.util.ResourceLocation;

public class Json_Models {

	public static void readModelFromJson(ResourceLocation loc, Bone[] bones){
		JsonObject obj = ClientProxy.getResourceAsJson(loc).getAsJsonObject();
		if(obj != null){
			if(obj.has("textures")){
				
			}
			if(obj.has("models")){
				JsonObject boneModels = obj.get("models").getAsJsonObject();
				for(Bone bone : bones){
					readModelsForBone(bone, boneModels);
				}
			}
		}
	}
	
	private static void readModelsForBone(Bone bone, JsonObject boneModels){
		String name = bone.getName();
		if(boneModels.has(name)){
			JsonArray models = boneModels.get(name).getAsJsonArray();
			for(int i = 0; i < models.size(); i++){
				JsonObject model = models.get(i).getAsJsonObject();
				if(model.has("type")){
					String type = model.get("type").getAsString();
					Model mdl = null;
					switch(type){
						case "cube":
							if(model.has("position") && model.has("size") && model.has("uv")){
								JsonArray pos = model.get("position").getAsJsonArray();
								JsonArray size = model.get("size").getAsJsonArray();
								JsonArray uv = model.get("uv").getAsJsonArray();
								mdl = new ModelCube(pos.get(0).getAsFloat(), pos.get(1).getAsFloat(), pos.get(2).getAsFloat(),
										size.get(0).getAsFloat(), size.get(1).getAsFloat(), size.get(2).getAsFloat(),
										uv.get(0).getAsInt(), uv.get(1).getAsInt(), 64, 64);
							}
							break;
						default:
							break;
					}
					
					if(mdl != null)
						bone.addModel(mdl);
				}
			}
		}
		for(Bone child : bone.getChildren()){
			readModelsForBone(child, boneModels);
		}
	}
}
