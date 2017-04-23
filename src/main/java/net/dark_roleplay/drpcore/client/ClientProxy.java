package net.dark_roleplay.drpcore.client;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import net.dark_roleplay.drpcore.api.items.DRPItem;
import net.dark_roleplay.drpcore.client.events.network.Event_ConnectServer;
import net.dark_roleplay.drpcore.client.events.player.Event_Mouse;
import net.dark_roleplay.drpcore.client.events.rendering.Event_ModelBaked;
import net.dark_roleplay.drpcore.client.keybindings.DRPCoreKeybindings;
import net.dark_roleplay.drpcore.common.handler.DRPCoreItems;
import net.dark_roleplay.drpcore.common.proxy.CommonProxy;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.MinecraftForge;
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
	
	public void preInit(FMLPreInitializationEvent event) {
		DRPCoreKeybindings.preInit(event);
		
		for(Map.Entry<DRPItem, String> entry : toRegisterMeshes.entrySet()) {
			this.registerItemMesh(entry.getValue(),entry.getKey());
		}
		this.toRegisterMeshes = null;
		
		//MinecraftForge.EVENT_BUS.register(Event_ModelBaked.instance);
		MinecraftForge.EVENT_BUS.register(new Event_Mouse());
		
	    // model to be used for rendering this item
	    ModelResourceLocation itemModelResourceLocation = new ModelResourceLocation("drpcore:medicine", "inventory");
	    ModelLoader.setCustomModelResourceLocation(DRPCoreItems.MEDICINE_BASE, 0, itemModelResourceLocation);
	}
	
	public void init(FMLInitializationEvent event) {
		DRPCoreKeybindings.init(event);
		FMLCommonHandler.instance().bus().register(new Event_ConnectServer());
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		DRPCoreKeybindings.postInit(event);
	}
	
	public void registerItemMesh(String MODID, DRPItem item) {
		if(item.getSubModelNames() != null){
			if(item.getSubModelNames() != null){
				ResourceLocation[] locations = new ResourceLocation[item.getSubModelNames().length];
				for(int i = 0; i < item.getSubModelNames().length; i++){
					locations[i] = new ResourceLocation(MODID + ":" + item.getModelFolder() + "/" + item.getSubModelNames()[i]);
				}
				ModelBakery.registerItemVariants(item,locations);
				for(int i = 0; i < locations.length; i++){
					ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(locations[i], "inventory"));
				}
			}else{
				String path = item.getModelFolder() + "/" + item.getUnlocalizedName().toString().substring(item.getUnlocalizedName().toString().indexOf(".") + 1, item.getUnlocalizedName().toString().length());
				ModelBakery.registerItemVariants(item, new ResourceLocation(MODID + ":" + path));
				ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(MODID + ":" + path, "inventory"));
			}
		}
	}
}
