package net.dark_roleplay.drpcore.common.handler;

import net.dark_roleplay.drpcore.api.items.DRPItem;
import net.dark_roleplay.drpcore.common.DarkRoleplayCore;
import net.dark_roleplay.drpcore.common.blocks.blueprint_controller.BlueprintController;
import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class DRPCoreBlocks  {
	/**---------- EXAMPLE BLOCKS ---------**/
	
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
	
	public static BlueprintController schem;
	
	@SubscribeEvent
	public static final void register(RegistryEvent.Register<Block> event) {
		event.getRegistry().register((schem = (BlueprintController) new BlueprintController().setRegistryName("blueprint_controller").setUnlocalizedName("blueprint_controller")));
		DRPCoreItems.addBlockItem((ItemBlock) new ItemBlock(schem).setRegistryName(schem.getRegistryName()));
	}
}
