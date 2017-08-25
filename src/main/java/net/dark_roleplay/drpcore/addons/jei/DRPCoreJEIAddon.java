package net.dark_roleplay.drpcore.addons.jei;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IJeiRuntime;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.ingredients.IModIngredientRegistration;
import net.dark_roleplay.drpcore.api.crafting.simple_recipe.IRecipe;
import net.dark_roleplay.drpcore.api.crafting.simple_recipe.SimpleRecipe;
import net.dark_roleplay.drpcore.common.crafting.CraftingRegistry;
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
	    
	    Collection<IRecipe> recipes = CraftingRegistry.getRecipes();
	    Iterator<IRecipe> iterRec = recipes.iterator();

	    Set<DRPCoreJEIRecipe> jeiRecipes = new HashSet();
	   
	    while(iterRec.hasNext()){
	    	IRecipe rec = iterRec.next();
	    	
	    	if(rec instanceof SimpleRecipe){
	    		SimpleRecipe srec = (SimpleRecipe) rec;
		    	DRPCoreJEIRecipe jeiRec = new DRPCoreJEIRecipe(ItemStack.EMPTY, Arrays.asList(srec.getMainOutput()), Arrays.asList(srec.getMainIngredients()));
		    	jeiRecipes.add(jeiRec);
	    	}
	    }

	    registry.addRecipes(recipes, "drpcore_crafting");
	}

	@Override
	public void onRuntimeAvailable(IJeiRuntime jeiRuntime) {
		// TODO Auto-generated method stub
		
	}

}
