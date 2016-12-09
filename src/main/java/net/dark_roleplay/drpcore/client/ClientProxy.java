package net.dark_roleplay.drpcore.client;

import java.util.HashMap;
import java.util.Map;

import net.dark_roleplay.drpcore.api.items.DRPItem;
import net.dark_roleplay.drpcore.common.proxy.CommonProxy;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy{

	private Map<DRPItem,String> toRegisterMeshes = new HashMap<DRPItem,String>();
	
	public void preInit(FMLPreInitializationEvent event) {
		for(Map.Entry<DRPItem, String> entry : toRegisterMeshes.entrySet()) {
			this.registerItemMesh(entry.getValue(),entry.getKey());
		}
		this.toRegisterMeshes = null;
	}
	
	public void init(FMLInitializationEvent event) {}
	
	public void postInit(FMLPostInitializationEvent event) {}
	
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
