package net.dark_roleplay.drpcore.common.handler;

import java.util.ArrayList;
import java.util.List;

import net.dark_roleplay.drpcore.api.old.items.DRPInstrument;
import net.dark_roleplay.drpcore.api.old.items.DRPLockable;
import net.dark_roleplay.drpcore.api.old.items.Seed;
import net.dark_roleplay.drpcore.api.old.modules.locks.ILock;
import net.dark_roleplay.drpcore.common.References;
import net.dark_roleplay.drpcore.common.DarkRoleplayCore;
import net.dark_roleplay.drpcore.testing.Test_PaintItem;
import net.dark_roleplay.library.items.DRPItem;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.ObjectHolder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistry;

@ObjectHolder(value="drpcore")
@Mod.EventBusSubscriber
public class DRPCoreItems {
	
	private static List<ItemBlock> blockItems = new ArrayList<ItemBlock>();

	public static void addBlockItem(ItemBlock item){
		blockItems.add(item);
	}
	
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
	
	public static final Item TEST_PAINT = null;
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public static final void registerEvent(RegistryEvent.Register<Item> event) {
//		if(DRPCoreConfigs.ENABLE_DEBUG_ITEMS){
			//Register here all Debug Item
//		}
		
		event.getRegistry().registerAll(
//			new DRPLockable("test_lock", ILock.TYPE.LOCK, 1),
//			new DRPLockable("test_key", ILock.TYPE.KEY, 1),
//			new Test_PaintItem("test_paint", 1),
//			new DRPInstrument("test_instrument_1", "HARP", 1),
//			new DRPInstrument("test_instrument_2", "FLUTE", 1)
		);
		
		for(ItemBlock b : blockItems){
			event.getRegistry().register(b);
		}
	}
	
	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event){
		for(Item i : blockItems){
			System.out.println(i.getUnlocalizedName());
			ModelLoader.setCustomModelResourceLocation(i, 0, new ModelResourceLocation(References.MODID + ":" + i.getUnlocalizedName().toString().substring(i.getUnlocalizedName().toString().indexOf(".") + 1, i.getUnlocalizedName().toString().length()), "inventory"));
		}
	}

	public static final void register(IForgeRegistry<Item> registry, DRPItem item){
		registry.register(item);
		DarkRoleplayCore.proxy.registerItemMesh(item.getRegistryName().getResourceDomain(), item);
	}
	
}
