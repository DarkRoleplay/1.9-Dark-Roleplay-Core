package net.dark_roleplay.drpcore.api.old.models.entity;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import it.unimi.dsi.fastutil.Arrays;
import net.dark_roleplay.drpcore.api.old.models.TexturedQuad;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;

public class Bone {

	private String name;
	
	private List<Model> models;
	
	private ResourceLocation texture;
	
	private Bone[] children;
	
	private Vec3d position;
	private Vec3d rotation;
	private Vec3d size;

	public Bone(JsonObject json){
		if(json.has("name")){
			this.name = json.get("name").getAsString();
		}else{
			this.name = "undefined";
		}
		if(json.has("position")){
			JsonArray position = json.get("position").getAsJsonArray();
			if(position.size() >= 3)
				this.position = new Vec3d(position.get(0).getAsDouble(), position.get(1).getAsDouble(), position.get(2).getAsDouble());
			else
				this.position = new Vec3d(0D, 0D, 0D);
		}else{
			this.position = new Vec3d(0D, 0D, 0D);
		}
		if(json.has("rotation")){
			JsonArray rotation = json.get("rotation").getAsJsonArray();
			if(rotation.size() >= 3)
				this.rotation = new Vec3d(rotation.get(0).getAsDouble(), rotation.get(1).getAsDouble(), rotation.get(2).getAsDouble());
			else
				this.rotation = new Vec3d(0D, 0D, 0D);
		}else{
			this.rotation = new Vec3d(0D, 0D, 0D);
		}
		if(json.has("size")){
			JsonArray size = json.get("size").getAsJsonArray();
			if(size.size() >= 3)
				this.size = new Vec3d(size.get(0).getAsDouble(), size.get(1).getAsDouble(), size.get(2).getAsDouble());
			else
				this.size = new Vec3d(1D, 1D, 1D);
		}else{
			this.size = new Vec3d(1D, 1D, 1D);
		}
		if(json.has("children")){
			JsonArray children = json.get("children").getAsJsonArray();
			this.children = new Bone[children.size()];
			for(int i = 0; i < children.size(); i++){
				this.children[i] = new Bone(children.get(i).getAsJsonObject());
			}
		}else{
			this.children = new Bone[0];
		}
		this.models = new ArrayList<Model>();
	}
	
	public String getName(){
		return this.name;
	}
	
	public void addModel(Model model){
		this.models.add(model);
	}
	
	public Bone[] getChildren(){
		return this.children;
	}
	
	public void render(BufferBuilder render, float scale) {
		GlStateManager.translate(this.position.x, this.position.y, this.position.z);
		GlStateManager.rotate((float) this.rotation.x, 1F, 0F, 0F);
		GlStateManager.rotate((float) this.rotation.y, 0F, 1F, 0F);
		GlStateManager.rotate((float) this.rotation.z, 0F, 0F, 1F);

		GlStateManager.scale(this.size.x, this.size.y, this.size.z);
		
		for(Model mdl : models){
			mdl.render(render, scale);
		}
		for(Bone child : children){
			child.render(render, scale);
		}
		
		GlStateManager.scale(1F / this.size.x, 1F / this.size.y, 1F / this.size.z); 

		GlStateManager.rotate((float) -this.rotation.x, 1F, 0F, 0F);
		GlStateManager.rotate((float) -this.rotation.y, 0F, 1F, 0F);
		GlStateManager.rotate((float) -this.rotation.z, 0F, 0F, 1F);
		GlStateManager.translate(-this.position.x, -this.position.y, -this.position.z);
	}
	
	public void renderSingle(BufferBuilder render, float scale) {
		GlStateManager.translate(this.position.x, this.position.y, this.position.z);
		GlStateManager.rotate((float) this.rotation.x, 1F, 0F, 0F);
		GlStateManager.rotate((float) this.rotation.y, 0F, 1F, 0F);
		GlStateManager.rotate((float) this.rotation.z, 0F, 0F, 1F);

		GlStateManager.scale(this.size.x, this.size.y, this.size.z);
		
		for(Model mdl : models){
			mdl.render(render, scale);
		}
		
		GlStateManager.scale(1F / this.size.x, 1F / this.size.y, 1F / this.size.z); 

		GlStateManager.rotate((float) -this.rotation.x, 1F, 0F, 0F);
		GlStateManager.rotate((float) -this.rotation.y, 0F, 1F, 0F);
		GlStateManager.rotate((float) -this.rotation.z, 0F, 0F, 1F);
		GlStateManager.translate(-this.position.x, -this.position.y, -this.position.z);
	}
}
