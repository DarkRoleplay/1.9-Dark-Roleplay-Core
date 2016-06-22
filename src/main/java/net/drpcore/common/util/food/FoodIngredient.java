package net.drpcore.common.util.food;
public class FoodIngredient {

	private static EnumFoodCategory foodType;

	private int FeedAmount;

	private double SaturationAmount;

	private String name;

	/**
	 * @param foodType
	 *            The type of the ingredient, determites if the player can eat
	 *            it or not.
	 * @param name
	 *            The name that will be displayed in the lore of food and used
	 *            in code.
	 * @param FeedAmount
	 *            The amount of food this ingredient will add to a food.
	 * @param SaturationAmount
	 *            The amount of saturation this ingredient will add to food.
	 */
	public FoodIngredient(EnumFoodCategory foodType, String name, int FeedAmount, double SaturationAmount) {
		this.foodType = foodType;
		this.FeedAmount = FeedAmount;
		this.SaturationAmount = SaturationAmount;
		this.name = name;
	}

	/**
	 * @return EnumFoodCategory The type of the ingredient choose
	 */
	public EnumFoodCategory getFoodType() {

		return this.foodType;
	}

	public int getFeedAmount() {

		return this.FeedAmount;
	}

	public double getSaturationAmount() {

		return this.SaturationAmount;
	}

	public String getName() {

		return this.name;
	}
}
