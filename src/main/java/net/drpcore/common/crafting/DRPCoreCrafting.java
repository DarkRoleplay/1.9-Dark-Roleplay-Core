package net.drpcore.common.crafting;

import net.drpcore.common.DarkRoleplayCore;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
		
		CraftingController cc = new CraftingController();
		
		cc.registerRecipe(new AdvancedRecipe(DarkRoleplayCore.MODID, Items.APPLE, new ItemStack(Items.APPLE,5), new ItemStack[]{new ItemStack(Item.getItemFromBlock(Blocks.SAPLING))}));
		cc.registerRecipe(new AdvancedRecipe(DarkRoleplayCore.MODID, Items.ARROW, new ItemStack(Items.APPLE,5), new ItemStack[]{new ItemStack(Item.getItemFromBlock(Blocks.SAPLING))}));
		cc.registerRecipe(new AdvancedRecipe(DarkRoleplayCore.MODID, Items.APPLE, new ItemStack(Items.APPLE,5), new ItemStack[]{new ItemStack(Item.getItemFromBlock(Blocks.SAPLING))}));
		cc.registerRecipe(new AdvancedRecipe(DarkRoleplayCore.MODID, Items.ARROW, new ItemStack(Items.APPLE,5), new ItemStack[]{new ItemStack(Item.getItemFromBlock(Blocks.SAPLING))}));
		cc.registerRecipe(new AdvancedRecipe(DarkRoleplayCore.MODID, Items.APPLE, new ItemStack(Items.APPLE,5), new ItemStack[]{new ItemStack(Item.getItemFromBlock(Blocks.SAPLING))}));
		cc.registerRecipe(new AdvancedRecipe(DarkRoleplayCore.MODID, Items.ARROW, new ItemStack(Items.APPLE,5), new ItemStack[]{new ItemStack(Item.getItemFromBlock(Blocks.SAPLING))}));
		cc.registerRecipe(new AdvancedRecipe(DarkRoleplayCore.MODID, Items.APPLE, new ItemStack(Items.APPLE,5), new ItemStack[]{new ItemStack(Item.getItemFromBlock(Blocks.SAPLING))}));
		cc.registerRecipe(new AdvancedRecipe(DarkRoleplayCore.MODID, Items.ARROW, new ItemStack(Items.APPLE,5), new ItemStack[]{new ItemStack(Item.getItemFromBlock(Blocks.SAPLING))}));
		cc.registerRecipe(new AdvancedRecipe(DarkRoleplayCore.MODID, Items.APPLE, new ItemStack(Items.APPLE,5), new ItemStack[]{new ItemStack(Item.getItemFromBlock(Blocks.SAPLING))}));
		cc.registerRecipe(new AdvancedRecipe(DarkRoleplayCore.MODID, Items.ARROW, new ItemStack(Items.APPLE,5), new ItemStack[]{new ItemStack(Item.getItemFromBlock(Blocks.SAPLING))}));
		cc.registerRecipe(new AdvancedRecipe(DarkRoleplayCore.MODID, Items.APPLE, new ItemStack(Items.APPLE,5), new ItemStack[]{new ItemStack(Item.getItemFromBlock(Blocks.SAPLING))}));
		cc.registerRecipe(new AdvancedRecipe(DarkRoleplayCore.MODID, Items.ARROW, new ItemStack(Items.APPLE,5), new ItemStack[]{new ItemStack(Item.getItemFromBlock(Blocks.SAPLING))}));
		cc.registerRecipe(new AdvancedRecipe(DarkRoleplayCore.MODID, Items.APPLE, new ItemStack(Items.APPLE,5), new ItemStack[]{new ItemStack(Item.getItemFromBlock(Blocks.SAPLING))}));
		cc.registerRecipe(new AdvancedRecipe(DarkRoleplayCore.MODID, Items.ARROW, new ItemStack(Items.APPLE,5), new ItemStack[]{new ItemStack(Item.getItemFromBlock(Blocks.SAPLING))}));
		cc.registerRecipe(new AdvancedRecipe(DarkRoleplayCore.MODID, Items.APPLE, new ItemStack(Items.APPLE,5), new ItemStack[]{new ItemStack(Item.getItemFromBlock(Blocks.SAPLING))}));
		cc.registerRecipe(new AdvancedRecipe(DarkRoleplayCore.MODID, Items.ARROW, new ItemStack(Items.APPLE,5), new ItemStack[]{new ItemStack(Item.getItemFromBlock(Blocks.SAPLING))}));
		cc.registerRecipe(new AdvancedRecipe(DarkRoleplayCore.MODID, Items.APPLE, new ItemStack(Items.APPLE,5), new ItemStack[]{new ItemStack(Item.getItemFromBlock(Blocks.SAPLING))}));
		cc.registerRecipe(new AdvancedRecipe(DarkRoleplayCore.MODID, Items.ARROW, new ItemStack(Items.APPLE,5), new ItemStack[]{new ItemStack(Item.getItemFromBlock(Blocks.SAPLING))}));
		cc.registerRecipe(new AdvancedRecipe(DarkRoleplayCore.MODID, Items.APPLE, new ItemStack(Items.APPLE,5), new ItemStack[]{new ItemStack(Item.getItemFromBlock(Blocks.SAPLING))}));

	}

	public static void postInit(FMLPostInitializationEvent event) {}
}
