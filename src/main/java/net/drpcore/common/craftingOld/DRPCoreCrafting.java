package net.drpcore.common.craftingOld;

import net.drpcore.common.DarkRoleplayCore;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;


/**
 * Used to do all Crafting stuff (Registration and Listing them)
 * 
 * @author JTK222
 */
public class DRPCoreCrafting {

	public static void preInit(FMLPreInitializationEvent event) {}

	public static void init(FMLInitializationEvent event) {
		
		/*CraftingController cc = new CraftingController();
		cc.registerRecipe("TEST", 
				new AdvancedRecipe(DarkRoleplayCore.MODID,
						new ItemStack(Items.BONE, 1), 
						new ItemStack(Items.DIAMOND_SWORD, 1), 
						new ItemStack[] {
								new ItemStack(Items.WATER_BUCKET, 1),
								new ItemStack(Blocks.PLANKS, 2)
								},
						new ItemStack[] {
								new ItemStack(Blocks.GLASS, 1),
								new ItemStack(Blocks.DIRT, 2)
								}
				).setCraftingTime(20));*/

	}

	public static void postInit(FMLPostInitializationEvent event) {}
}
