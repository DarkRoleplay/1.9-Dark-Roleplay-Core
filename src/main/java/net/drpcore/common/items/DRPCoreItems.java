package net.drpcore.common.items;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.capabilities.IRecipeController;
import net.drpcore.common.capabilitiesOld.DRPCoreCapabilities;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;


public class DRPCoreItems {

	public static void preInit(FMLPreInitializationEvent event) {
		GameRegistry.register(new Item(){

			 @Override
		        public ActionResult<ItemStack> onItemRightClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand){
		            if (!world.isRemote){
		                    final IRecipeController sleep = player.getCapability(DRPCoreCapabilities.DRPCORE_RECIPE_CONTROLLER, null);
		                    if (sleep != null)
			                    System.out.println(sleep.getUnlockedRecipes());
		                        sleep.unlockRecipe("Test");
		                    return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
		                }
		            return ActionResult.newResult(EnumActionResult.PASS, stack);
		        }
			
		}.setRegistryName("Test").setCreativeTab(CreativeTabs.COMBAT));

	}

	public static void init(FMLInitializationEvent event) {}

	public static void postInit(FMLPostInitializationEvent event) {}
}
