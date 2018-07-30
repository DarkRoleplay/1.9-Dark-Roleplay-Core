package net.dark_roleplay.core.api.old.modules.materials;

import java.util.List;

import net.dark_roleplay.core.client.ClientProxy;
import net.dark_roleplay.core.modules.automatic_resource_generation.JsonGenerator;
import net.dark_roleplay.core.modules.automatic_resource_generation.TextureGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
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
