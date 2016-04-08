package net.drpcore.common.items;

import net.drpcore.common.util.food.EnumFoodCategory;
import net.drpcore.common.util.food.FoodIngredient;
import net.drpcore.common.items.templates.FoodBase;
import net.drpcore.common.util.food.DRPCoreIngredients;

public class DebugFood1 extends FoodBase{

	public DebugFood1() {
		super(2, 1, 4, new FoodIngredient[]{DRPCoreIngredients.potato,DRPCoreIngredients.carrot,DRPCoreIngredients.egg}, EnumFoodCategory.MEAT);
		this.setUnlocalizedName("DebugFOod1");
	}
	
	

}
