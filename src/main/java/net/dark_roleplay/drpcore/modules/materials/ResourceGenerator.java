package net.dark_roleplay.drpcore.modules.materials;

import java.util.List;

import net.dark_roleplay.drpcore.client.ClientProxy;
import net.dark_roleplay.drpcore.modules.argh.JsonGenerator;
import net.dark_roleplay.drpcore.modules.argh.TextureGenerator;
import net.minecraft.util.ResourceLocation;

public class ResourceGenerator {

	String resourceType;
	protected JsonGenerator jsonGenerator;
	protected TextureGenerator textureGenerator;
		
	public ResourceGenerator(String resourceType, ResourceLocation jsonGenerator, ResourceLocation textureGenerator){
		this.resourceType = resourceType;
		
		if(textureGenerator != null)
		try{
			this.textureGenerator = new TextureGenerator(ClientProxy.getResourceAsJson(textureGenerator));
		}catch(Exception e){
			System.out.println("Failed to load Texture Generator file: " + textureGenerator.toString());
		}
		
		if(jsonGenerator != null)
			try{
			this.jsonGenerator = new JsonGenerator(ClientProxy.getResourceAsJson(jsonGenerator));
		}catch(Exception e){
			System.out.println("Failed to load Json Generator file: " + jsonGenerator.toString());
		}
	}
	
	public void generate(List<Material> materials){
		if(this.textureGenerator != null){
			try{
				this.textureGenerator.generateTextures(materials);
			}catch(Exception e){
				System.out.println("Failed to generate Texture files: " + textureGenerator.toString());
				e.printStackTrace();
			}
		}
		if(this.jsonGenerator != null){
			try{
				this.jsonGenerator.generateModels(materials);
			}catch(Exception e){
				System.out.println("Failed to generate Json files: " + jsonGenerator.toString());
			}
		}
	}
	
	public String getResourceType(){
		return resourceType;
	}
}
