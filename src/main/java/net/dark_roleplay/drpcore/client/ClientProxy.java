package net.dark_roleplay.drpcore.client;

import java.util.HashMap;
import java.util.Map;

import net.dark_roleplay.drpcore.common.items.DRPItem;
import net.dark_roleplay.drpcore.common.proxy.CommonProxy;
import net.minecraft.client.renderer.block.model.ModelBakery;
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
			for(int i = 0; i < item.getSubModelNames().length; i++){
				
			}
			for(String name : item.getSubModelNames()){
				
			}
			ModelBakery.registerItemVariants(item,new ResourceLocation(MODID + ":" + item.get));
		}
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(DarkRoleplayMedieval.MODID + ":" + path, "inventory"));
	}
}
