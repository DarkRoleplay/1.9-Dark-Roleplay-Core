package net.drpcore.common.util.food;
public enum EnumFoodCategory {
	MEAT(false, false, false, true),
	FISH(false, false, true, false),
	VEGGIE(true, true, false, false),
	FRUIT(true, true, false, false),
	EGG(false, true, false, false);

	boolean isVegan = false;

	boolean isVegetarian = false;

	boolean isMeat = false;

	boolean isFish = false;

	EnumFoodCategory(boolean isVegan, boolean isVegetarian, boolean isFish, boolean isMeat) {
		this.isVegan = isVegan;
		this.isVegetarian = isVegetarian;
		this.isMeat = isMeat;
		this.isFish = isFish;
	}
}
