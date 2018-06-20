package net.dark_roleplay.core.api.old.models.entity.serialization;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.dark_roleplay.core.api.old.models.entity.Bone;
import net.dark_roleplay.core.api.old.models.entity.animation.Animation;
import net.dark_roleplay.core.api.old.models.entity.animation.AnimationState;
import net.dark_roleplay.core.api.old.models.entity.animation.BoneAnimation;
import net.dark_roleplay.core.client.ClientProxy;
import net.minecraft.util.ResourceLocation;

public class Json_Animation {

	public static AnimationState readStateFromJson(ResourceLocation loc, Bone[] bones){
		JsonObject element = ClientProxy.getResourceAsJson(loc).getAsJsonObject();
		
		AnimationState state = null;
		
		if(element != null){
			JsonArray animationKeys = element.get("animations").getAsJsonArray();
			Map<String, Animation> animations = new HashMap<String, Animation>();
			
			for(int i = 0; i < animationKeys.size(); i++) {
				animations.put(animationKeys.get(i).getAsString(), Json_Animation.readAnimationFromJson(new ResourceLocation(loc.getResourceDomain(), "entities/" + loc.getResourcePath().split("/")[1] + "/animations/" + animationKeys.get(i).getAsString() + ".json"), bones));
			}
			
			state = new AnimationState(animations, animations.get(element.get("default").getAsString()));
		}
		
		return state;
	}
	
	public static Animation readAnimationFromJson(ResourceLocation loc, Bone[] bones){
		JsonObject element = ClientProxy.getResourceAsJson(loc).getAsJsonObject();
		
		int fps = element.get("fps").getAsInt();
		int frames = element.get("frames").getAsInt();
		
		JsonObject subAnimations = element.get("animation").getAsJsonObject();
		Map<Bone, BoneAnimation> boneAnimation = new HashMap<Bone, BoneAnimation>();		

		for(Bone bone : bones) {
			if(subAnimations.has(bone.getName())) {
				boneAnimation.put(bone, readBoneAnimationFromJson(subAnimations.get(bone.getName()).getAsJsonObject(), fps, frames));
			}
		}
		
		
		return new Animation(fps, frames, boneAnimation);
	}
	
	public static BoneAnimation readBoneAnimationFromJson(JsonObject obj, int fps, int frames){
		
		float[] rotX = new float[0];
		if(obj.has("rotation_x")) {
			JsonObject subObj = obj.get("rotation_x").getAsJsonObject();
			JsonArray values = subObj.get("values").getAsJsonArray();
			rotX = new float[values.size()];
			for(int i = 0; i < values.size(); i++) {
				rotX[i] = values.get(i).getAsFloat();
			}
		}else {
			rotX = new float[] {0F};
		}

		float[] rotY = new float[0];
		if(obj.has("rotation_y")) {
			JsonObject subObj = obj.get("rotation_y").getAsJsonObject();
			JsonArray values = subObj.get("values").getAsJsonArray();
			rotY = new float[values.size()];
			for(int i = 0; i < values.size(); i++) {
				rotY[i] = values.get(i).getAsFloat();
			}
		}else {
			rotY = new float[] {0F};
		}

		float[] rotZ = new float[0];
		if(obj.has("rotation_z")) {
			JsonObject subObj = obj.get("rotation_z").getAsJsonObject();
			JsonArray values = subObj.get("values").getAsJsonArray();
			rotZ = new float[values.size()];
			for(int i = 0; i < values.size(); i++) {
				rotZ[i] = values.get(i).getAsFloat();
			}
		}else {
			rotZ = new float[] {0F};
		}
		
		return new BoneAnimation(fps, frames, rotX, rotY, rotZ);
	}
	
}
