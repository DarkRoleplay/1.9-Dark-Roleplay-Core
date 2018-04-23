package net.dark_roleplay.drpcore.client.resources;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public class DRPModelLoader implements ICustomModelLoader{

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		//TOIMPLEMENT
	}

	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		return modelLocation instanceof ModelResourceLocation && modelLocation.getResourcePath().contains("advanced_model");
	}

	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception {
		IResource res = Minecraft.getMinecraft().getResourceManager().getResource(modelLocation);
		JsonObject obj = new Gson().fromJson(new BufferedReader(new InputStreamReader(res.getInputStream())), JsonObject.class);
		return new AdvancedModel(obj);
	}
}
