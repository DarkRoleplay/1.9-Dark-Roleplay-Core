package net.dark_roleplay.drpcore.client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.dark_roleplay.drpcore.api.entities.renderer.RendererTest;
import net.dark_roleplay.drpcore.api.gui.modular.ModularGui_Template;
import net.dark_roleplay.drpcore.api.items.DRPItem;
import net.dark_roleplay.drpcore.api.items.ItemApi;
import net.dark_roleplay.drpcore.client.events.network.Event_ConnectServer;
import net.dark_roleplay.drpcore.client.events.player.Event_Mouse;
import net.dark_roleplay.drpcore.client.events.rendering.Event_BlockHighlight;
import net.dark_roleplay.drpcore.client.events.rendering.Event_ModelBaked;
import net.dark_roleplay.drpcore.client.keybindings.DRPCoreKeybindings;
import net.dark_roleplay.drpcore.client.renderer.players.RenderLayer_PremiumAddon;
import net.dark_roleplay.drpcore.client.renderer.tileentities.Renderer_StructureController;
import net.dark_roleplay.drpcore.client.resources.ModularGui_Handler;
import net.dark_roleplay.drpcore.common.DRPCoreReferences;
import net.dark_roleplay.drpcore.common.handler.DRPCoreItems;
import net.dark_roleplay.drpcore.common.objects.entities.util.sitting.Sittable;
import net.dark_roleplay.drpcore.common.objects.tile_entities.blueprint_controller.TE_BlueprintController;
import net.dark_roleplay.drpcore.common.proxy.CommonProxy;
import net.dark_roleplay.drpcore.testing.Testing_Entity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResource;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.client.resources.data.TextureMetadataSectionSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ClientProxy extends CommonProxy{
	
	private Map<DRPItem,String> toRegisterMeshes = new HashMap<DRPItem,String>();
	
	public static boolean useRecipeData = false;
	public static int recipePage = 0;
	public static int categoryOffset = 0;
	public static short selectedCategory = 0;
	public static byte currentTick = 0;
	
	public static List<ModularGui_Template> modularGuis = new ArrayList<ModularGui_Template>();
		
	public void preInit(FMLPreInitializationEvent event) {
		DRPCoreKeybindings.preInit(event);
		ItemApi.registerItemMeshs();

		for(Map.Entry<DRPItem, String> entry : toRegisterMeshes.entrySet()) {
			this.registerItemMesh(entry.getValue(),entry.getKey());
		}
		this.toRegisterMeshes = null;
		
		//MinecraftForge.EVENT_BUS.register(Event_ModelBaked.instance);
		MinecraftForge.EVENT_BUS.register(new Event_Mouse());
		MinecraftForge.EVENT_BUS.register(new Event_BlockHighlight());
		
	    // model to be used for rendering this item
	    ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation("drpcore:medicine", "inventory");

		
		ClientRegistry.bindTileEntitySpecialRenderer(TE_BlueprintController.class, new Renderer_StructureController());
		//TODO REMOVE
		RenderingRegistry.registerEntityRenderingHandler(Testing_Entity.class, new IRenderFactory<Testing_Entity>(){
			@Override
			public Render<? super Testing_Entity> createRenderFor(RenderManager manager) {
				return new RendererTest(manager, new ResourceLocation(DRPCoreReferences.MODID, "entities/test/bones.json"), new ResourceLocation(DRPCoreReferences.MODID, "entities/test/model.json"), new ResourceLocation(DRPCoreReferences.MODID, "entities/test/animations.json"));
			}
		});
//		Minecraft.getMinecraft().getResourceManager().
	}

	public void init(FMLInitializationEvent event) {
		DRPCoreKeybindings.init(event);
		FMLCommonHandler.instance().bus().register(new Event_ConnectServer());
		
		RenderPlayer steve = ((RenderPlayer)Minecraft.getMinecraft().getRenderManager().getSkinMap().get("default"));
		RenderPlayer alex = ((RenderPlayer)Minecraft.getMinecraft().getRenderManager().getSkinMap().get("slim"));
		steve.addLayer(new RenderLayer_PremiumAddon(steve));
		alex.addLayer(new RenderLayer_PremiumAddon(alex));
		
		IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
		if(manager instanceof IReloadableResourceManager) {
		    ((IReloadableResourceManager)manager).registerReloadListener(new ModularGui_Handler());
		}
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		DRPCoreKeybindings.postInit(event);
	}
	

	
	public static IResource getResource(ResourceLocation location){
        IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
        IResource res = null;
		try {
			res = manager.getResource(location);
		} catch (IOException e) {
			e.printStackTrace();
		}
        return res;
    }
	
	public static List<IResource> getResources(ResourceLocation location){
		List<IResource> resources = new ArrayList<IResource>();
		
		File folder;
		try {
			folder = new File(ClientProxy.class.getClassLoader().getResource("/assets/" + location.getResourceDomain() + "/" + location.getResourcePath()).toURI());
			
			for (final File file : folder.listFiles()) {
		        if (file.isDirectory()) {
		        } else {
		        	resources.add(getResource(new ResourceLocation(location.getResourceDomain(), location.getResourcePath() + file.getName())));
		        }
		    }
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		return resources;
	}
	
	public static JsonElement getResourceAsJson(ResourceLocation location){
		IResource res = getResource(location);
		Gson gson = new Gson();
		BufferedReader reader = new BufferedReader(new InputStreamReader(res.getInputStream()));
		JsonElement je = gson.fromJson(reader, JsonElement.class);
		return je;
	}
}
