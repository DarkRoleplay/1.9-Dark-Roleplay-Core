package net.dark_roleplay.drpcore.client;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.dark_roleplay.drpcore.api.items.DRPItem;
import net.dark_roleplay.drpcore.api.items.ItemApi;
import net.dark_roleplay.drpcore.client.events.network.Event_ConnectServer;
import net.dark_roleplay.drpcore.client.events.player.Event_Mouse;
import net.dark_roleplay.drpcore.client.events.rendering.Event_BlockHighlight;
import net.dark_roleplay.drpcore.client.events.rendering.Event_ModelBaked;
import net.dark_roleplay.drpcore.client.keybindings.DRPCoreKeybindings;
import net.dark_roleplay.drpcore.client.renderer.tileentities.Renderer_StructureController;
import net.dark_roleplay.drpcore.client.resources.ModularGui_Handler;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.dark_roleplay.drpcore.common.handler.DRPCoreItems;
import net.dark_roleplay.drpcore.common.proxy.CommonProxy;
import net.dark_roleplay.drpcore.common.tile_entities.blueprint_controller.TE_BlueprintController;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
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
	
	public static ModularGui_Template currentGui;
	
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
//		Minecraft.getMinecraft().getResourceManager().
	}
	
	public void init(FMLInitializationEvent event) {
		DRPCoreKeybindings.init(event);
		FMLCommonHandler.instance().bus().register(new Event_ConnectServer());
		
		IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
		if(manager instanceof IReloadableResourceManager) {
		    ((IReloadableResourceManager)manager).registerReloadListener(new ModularGui_Handler());
		}
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		DRPCoreKeybindings.postInit(event);

//		try {
//			getResources(new ResourceLocation(DRPCoreInfo.MODID, "recipes/"));
//		} catch (IOException | URISyntaxException e) {
//			e.printStackTrace();
//		}
	}
	
//	public void registerItemMesh(String MODID, DRPItem item) {
//		if(item.getSubModelNames() != null){
//			if(item.getSubModelNames() != null){
//				ResourceLocation[] locations = new ResourceLocation[item.getSubModelNames().length];
//				for(int i = 0; i < item.getSubModelNames().length; i++){
//					locations[i] = new ResourceLocation(MODID + ":" + item.getModelFolder() + "/" + item.getSubModelNames()[i]);
//				}
//				ModelBakery.registerItemVariants(item,locations);
//				for(int i = 0; i < locations.length; i++){
//					ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(locations[i], "inventory"));
//				}
//			}else{
//				String path = item.getModelFolder() + "/" + item.getUnlocalizedName().toString().substring(item.getUnlocalizedName().toString().indexOf(".") + 1, item.getUnlocalizedName().toString().length());
//				ModelBakery.registerItemVariants(item, new ResourceLocation(MODID + ":" + path));
//				ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(MODID + ":" + path, "inventory"));
//			}
//		}
//	}
	
	public static IResource getResource(ResourceLocation location) throws IOException {
        IResourceManager manager = Minecraft.getMinecraft().getResourceManager();
        IResource res = manager.getResource(location);
        System.out.println(res.getResourceLocation());
        return res;
    }
	
	public static List<IResource> getResources(ResourceLocation location) throws IOException, URISyntaxException{
		List<IResource> resources = new ArrayList<IResource>();
		
		File folder = new File(ClientProxy.class.getClassLoader().getResource("/assets/" + location.getResourceDomain() + "/" + location.getResourcePath()).toURI());
		
		for (final File file : folder.listFiles()) {
	        if (file.isDirectory()) {
	        } else {
	        	resources.add(getResource(new ResourceLocation(location.getResourceDomain(), location.getResourcePath() + file.getName())));
	            System.out.println(file.getName());
	        }
	    }
		
		return resources;
	}
}
