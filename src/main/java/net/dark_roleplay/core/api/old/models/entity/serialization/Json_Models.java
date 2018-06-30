package net.dark_roleplay.core.api.old.models.entity.serialization;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import net.dark_roleplay.core.api.old.models.entity.Bone;
import net.dark_roleplay.core.api.old.models.entity.model.Model;
import net.dark_roleplay.core.api.old.models.entity.model.ModelCube;
import net.dark_roleplay.core.client.ClientProxy;
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
								mdl = new ModelCube(pos.get(0).getAsFloat() * 0.9375F, pos.get(1).getAsFloat() * 0.9375F, pos.get(2).getAsFloat() * 0.9375F,
										size.get(0).getAsFloat() * 0.9375F, size.get(1).getAsFloat() * 0.9375F, size.get(2).getAsFloat() * 0.9375F,
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
