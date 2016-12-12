package net.dark_roleplay.drpcore.common.capabilities.player.crafting;

import java.util.concurrent.Callable;

public class RecipeControllerFactory implements Callable<IRecipeController> {

	  @Override
	  public IRecipeController call() throws Exception {
	    return new RecipeControllerDefault();
	  }
}