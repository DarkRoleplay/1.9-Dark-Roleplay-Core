package net.dark_roleplay.drpcore.api.old.models.entity.serialization;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import net.dark_roleplay.drpcore.api.old.models.entity.Bone;
import net.dark_roleplay.drpcore.client.ClientProxy;
import net.dark_roleplay.drpcore.common.References;
import net.minecraft.util.ResourceLocation;

public class Json_Bones {

	public static Bone[] readBonesFromJson(ResourceLocation loc){
		JsonElement element = ClientProxy.getResourceAsJson(loc);
		Bone[] bones;
		if(element != null){
			if(element.isJsonArray()){
				JsonArray arr = element.getAsJsonArray();
				bones = new Bone[arr.size()];
				for(int i = 0; i < arr.size(); i++){
					bones[i] = new Bone(arr.get(i).getAsJsonObject());
				}
				return bones;
			}else{
				bones = new Bone[1];
				bones[0] = new Bone(element.getAsJsonObject());
				return bones;
			}
		}
		return new Bone[0];
	}
}
