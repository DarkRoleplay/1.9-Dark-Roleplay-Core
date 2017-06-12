package net.dark_roleplay.drpcore.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import net.dark_roleplay.drpcore.common.crafting.CraftingRegistry;
import net.dark_roleplay.drpcore.common.crafting.SimpleRecipe;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

//@JEIPlugin
public class DRPCoreJEIAddon implements IModPlugin{

	public static final String DRPCoreCategory = "drpcore.general";
	
	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {}

	@Override
	public void registerIngredients(IModIngredientRegistration registry) {}

	@Override
	public void register(IModRegistry registry) {
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();
	    IGuiHelper guiHelper = jeiHelpers.getGuiHelper();
		
		registry.addRecipeCategories(new DRPCoreCategory(guiHelper));
	    registry.addRecipeHandlers(new DRPCoreHandler());
	    registry.addRecipeCategoryCraftingItem(new ItemStack(Blocks.CRAFTING_TABLE), DRPCoreCategory);
	    
	    List<Block> stations = CraftingRegistry.getRecipeStations();
	    
	    List<DRPCoreJEIRecipe> result = new ArrayList<DRPCoreJEIRecipe>();
	    
	    for(Block station : stations){
	    	Map<String,List<String>> categorys = CraftingRegistry.getRecipesForStation(station);
	    	Iterator iter = categorys.keySet().iterator();
	    	while (iter.hasNext()) {
	    	    List<String> recipes = categorys.get(iter.next());
		    	
	    	    for(String recipeName : recipes){
	    	    	SimpleRecipe recipe = CraftingRegistry.getRecipe(recipeName);
	    	    	ItemStack[] outs = recipe.getMainOutput();
	    	    	ItemStack[] ins = recipe.getMainIngredients();

	    	    	ItemStack stationStack = station != Blocks.AIR ? new ItemStack(station) : new ItemStack(Items.APPLE);
	    	    	
	    	    	result.add(new DRPCoreJEIRecipe(stationStack, Arrays.asList(outs), Arrays.asList(ins)));
	    	    }
	    	}
	    }
	    registry.addRecipes(result);
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		// TODO Auto-generated method stub
		
	}

}
