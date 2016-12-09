package net.dark_roleplay.drpcore.common.handler;

import net.dark_roleplay.drpcore.api.items.DRPItem;
import net.dark_roleplay.drpcore.common.DarkRoleplayCore;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class DRPCoreItems {
	/**---------- EXAMPLE ITEM ---------**/
	
	/**---------- A ----------**/
	/**---------- B ----------**/
	/**---------- C ----------**/
	/**---------- D ----------**/
	/**---------- E ----------**/
	/**---------- F ----------**/
	/**---------- G ----------**/
	/**---------- H ----------**/
	/**---------- I ----------**/
	/**---------- J ----------**/
	/**---------- K ----------**/
	/**---------- L ----------**/
	/**---------- M ----------**/
	/**---------- N ----------**/
	/**---------- O ----------**/
	/**---------- P ----------**/
	/**---------- Q ----------**/
	/**---------- R ----------**/
	/**---------- S ----------**/
	/**---------- T ----------**/
	/**---------- U ----------**/
	/**---------- V ----------**/
	/**---------- W ----------**/
	/**---------- X ----------**/
	/**---------- Y ----------**/
	/**---------- Z ----------**/
	
	public static final void preInit(FMLPreInitializationEvent event) {
		/**---------- EXAMPLE ITEM ---------**/
		if(DRPCoreConfigs.ENABLE_DEBUG_ITEMS){
			//Register here all Debug Item
			System.out.println("Debug Items are Registered Now!");
		}
	}
	
	public static final void init(FMLInitializationEvent event) {}

	public static final void postInit(FMLPostInitializationEvent event) {}

	public static final void registerItem(String modid,DRPItem item){
		registerItem(modid,item,true);
	}
	
	public static final void registerItem(String modid,DRPItem item, boolean registerModel){
		GameRegistry.register(item);
		
		if(registerModel){
			DarkRoleplayCore.proxy.registerItemMesh(modid, item);
		}
	}
}
