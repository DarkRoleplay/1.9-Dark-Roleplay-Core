package net.dark_roleplay.drpcore.modules.work_in_progress.models;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.google.gson.stream.JsonReader;

import net.dark_roleplay.drpcore.common.DRPCoreReferences;
import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;

public class MeshModelLoader implements ICustomModelLoader {

	private IResourceManager manager;
	private final Set<String> enabledDomains = new HashSet<>();
	private final Map<ResourceLocation, IModel> cache = new HashMap<>();
	// private final Map<ResourceLocation, Exception> errors = new HashMap<>();

	public void addDomain(String domain) {
		enabledDomains.add(domain.toLowerCase());
		DRPCoreReferences.LOGGER.info("Advanced Model Loader: Domain {} has been added.", domain.toLowerCase());
	}

	@Override
	public void onResourceManagerReload(IResourceManager resourceManager) {
		this.manager = resourceManager;
		cache.clear();
		// errors.clear();
	}

	@Override
	public boolean accepts(ResourceLocation modelLocation) {
		return enabledDomains.contains(modelLocation.getResourceDomain()) && modelLocation.getResourcePath().endsWith(".mesh");
	}

	@Override
	public IModel loadModel(ResourceLocation modelLocation) throws Exception {
		ResourceLocation file = new ResourceLocation(modelLocation.getResourceDomain(), modelLocation.getResourcePath());
		if (!cache.containsKey(file)) {
			IResource resource;
			try {
				resource = manager.getResource(file);
			} catch (FileNotFoundException e) {
				if (modelLocation.getResourcePath().startsWith("models/block/")){
					resource = manager.getResource(new ResourceLocation(file.getResourceDomain(), "models/item/" + file.getResourcePath().substring("models/block/".length())));
				}else if (modelLocation.getResourcePath().startsWith("models/item/")){
					resource = manager.getResource(new ResourceLocation(file.getResourceDomain(), "models/block/" + file.getResourcePath().substring("models/item/".length())));
				}else{
					throw e;
				}
			}
//			return ModelBlock.deserialize(new InputStreamReader(resource.getInputStream()));
//			OBJModel.Parser parser = new OBJModel.Parser(resource, manager);
//			OBJModel model = null;
//			try {
//				model = parser.parse();
//			} catch (Exception e) {
//				errors.put(modelLocation, e);
//			} finally {
//				cache.put(modelLocation, model);
//			}
		}
//		IModel model = cache.get(file);
//		if (model == null)
//			throw new ModelLoaderRegistry.LoaderException("Error loading model previously: " + file, errors.get(modelLocation));
//		return model;

		return null;
	}
}