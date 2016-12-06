package net.drpcore.common.capabilities;

import java.util.concurrent.Callable;

public class RecipeControlerFactory implements Callable<IRecipeController> {

	  @Override
	  public IRecipeController call() throws Exception {
	    return new RecipeControllerDefault();
	  }
	}
