package net.drpcore.common.crafting;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;


public class AdvancedRecipe {

	// -------------------------------------------------- Variables and Objects --------------------------------------------------
	/**
	 * The ID of the Mod that adds this Recipe (For Configurations)
	 */
	String modname;

	/**
	 * Item displayed as a preview in recipe selection screen
	 */
	private ItemStack previewItem;

	private ItemStack defaultOutput;

	private ItemStack[] primaryIngredients;

	private boolean[] respectMetaPrimary;

	private ItemStack[] secondaryIngredients;

	private boolean[] respectMetaSecondary;

	// TODO Initialize Variable
	private boolean needToUnlock = false;

	// TODO Initialize Variable
	private float generateInWorldPercentage = 0F;

	// TODO Initialize Variable
	public int craftingTime = 0;

	//Automaticly set by registerRecipe Method
	private Block station;

	//Automaticly set by registerRecipe Method
	private String category;
	
	//Automaticly set by registerRecipe Method

	
	private int registeredID;

	// -------------------------------------------------- Constructors --------------------------------------------------
	/**
	 * Preview Item equals defaultOutput, secondaryIngredients are null
	 * 
	 * @param modname
	 *            The modname that provides this Recipe (Used for Configs)
	 * @param defaultOutput
	 *            The Default Output use for Recipe Preview and Output if not changed in getOuput()
	 * @param primaryIngredients
	 *            The needed Ingredients to craft this recipe
	 */
	public AdvancedRecipe(String modname, ItemStack defaultOutput, ItemStack[] primaryIngredients) {
		this(modname, defaultOutput, defaultOutput, primaryIngredients, null);
	}

	/**
	 * SecondaryIngredients are null
	 * 
	 * @param modname
	 *            The modname that provides this Recipe (Used for Configs)
	 * @param previewItem
	 *            This Item will be used as a preview in the Recipe Selection
	 * @param defaultOutput
	 *            The Default Output use for Recipe Preview and Output if not changed in getOuput()
	 * @param primaryIngredients
	 *            The needed Ingredients to craft this recipe
	 */
	public AdvancedRecipe(String modname, ItemStack previewItem, ItemStack defaultOutput, ItemStack[] primaryIngredients) {
		this(modname, previewItem, defaultOutput, primaryIngredients, null);
	}

	/**
	 * SecondaryIngredients are null
	 * 
	 * @param modname
	 *            The modname that provides this Recipe (Used for Configs)
	 * @param previewItem
	 *            This Item will be used as a preview in the Recipe Selection
	 * @param defaultOutput
	 *            The Default Output use for Recipe Preview and Output if not changed in getOuput()
	 * @param primaryIngredients
	 *            The needed Ingredients to craft this recipe
	 * @param secondaryIngredients
	 *            Like primaryIngredients but are not a must have (more in the getOutput method)
	 */
	public AdvancedRecipe(String modname, ItemStack previewItem, ItemStack defaultOutput, ItemStack[] primaryIngredients, ItemStack[] secondaryIngredients) {
		this.modname = modname;
		this.previewItem = previewItem;
		this.setDefaultOutput(defaultOutput);
		this.setPrimaryIngredients(primaryIngredients);
		this.respectMetaPrimary = new boolean[primaryIngredients.length];
		for(short i = 0; i < primaryIngredients.length; i++ )
			respectMetaPrimary[i] = true;
		this.secondaryIngredients = secondaryIngredients;
		if(secondaryIngredients != null) {
			this.respectMetaSecondary = new boolean[secondaryIngredients.length];
			for(short i = 0; i < secondaryIngredients.length; i++ )
				respectMetaSecondary[i] = true;
		}
	}

	// -------------------------------------------------- Getter --------------------------------------------------
	/**
	 * Provides the modname of the mod that provides this recipe
	 * 
	 * @return the providing modname
	 */
	public String getmodname() {

		return this.modname;
	}

	/**
	 * @return The Preview Item used for this Recipe
	 */
	public ItemStack getPreviewItem() {

		return this.previewItem;
	}

	/**
	 * @return the category
	 */
	public String getCategory() {

		return this.category;
	}

	/**
	 * @return the station
	 */
	public Block getStation() {

		return this.station;
	}

	public ItemStack getDefaultOutput() {

		return this.defaultOutput;
	}
	
	public ItemStack[] getPrimaryIngredients() {

		return this.primaryIngredients;
	}
	
	public ItemStack[] getSecondaryIngredients() {

		return this.secondaryIngredients;
	}
	
	public boolean respectPrimaryMeta(int ingredient){
		return this.respectMetaPrimary[ingredient];
	}
	
	public boolean respectSecondaryMeta(int ingredient){
		if(this.respectMetaSecondary != null)
		return this.respectMetaSecondary[ingredient];
		return false;
	}
	
	public int getRegisteredID() {
		
		return registeredID;
	}

	// -------------------------------------------------- Setter --------------------------------------------------
	/**
	 * Used to manipulate the modname
	 * 
	 * @param modname
	 *            a String Supposed to be Readyble modname
	 */
	public AdvancedRecipe setmodname(String modname) {

		this.modname = modname;
		return this;
	}

	/**
	 * Used to manipulate the Preview Item of this Recipe
	 * 
	 * @param previewItem
	 *            The new Preview Item
	 */
	public AdvancedRecipe setPreviewItem(ItemStack previewItem) {

		this.previewItem = previewItem;
		return this;
	}

	/**
	 * @param station
	 *            the station to set
	 */
	public AdvancedRecipe setStation(Block station) {

		this.station = station;
		return this;
	}

	/**
	 * @param category
	 *            the category to set
	 */
	public AdvancedRecipe setCategory(String category) {

		this.category = category;
		return this;}

	public AdvancedRecipe setDefaultOutput(ItemStack defaultOutput) {

		this.defaultOutput = defaultOutput;
		return this;
	}

	public AdvancedRecipe setPrimaryIngredients(ItemStack[] primaryIngredients) {

		this.primaryIngredients = primaryIngredients;
		return this;
	}
	
	public AdvancedRecipe setRegisteredID(int registeredID) {
		
		this.registeredID = registeredID;
		return this;
	}
	
	public AdvancedRecipe setCraftingTime(int craftingTime){
		this.craftingTime = craftingTime;
		return this;
	}
	
	// -------------------------------------------------- Utility --------------------------------------------------
	/**
	 * Used to inform the Player about the unmet Condition so he can fix it up
	 * 
	 * @param ID
	 *            of the Information (Default obtained by doConditionsMet(...)
	 * @return A String that should contain all the Information
	 */
	public String getUnmetConditionText(short ID) {

		switch (ID) {
			case 0:
				return "Looks like all conditions are met!";
			case 1:
				return "You are missing a few Ingredients!";
			default:
				return "Missing Condition! Ask mod Author for better information!";
		}
	}


	public boolean doesHaveIngredients(EntityPlayer player, ItemStack[] input , int multiplier) {

		// Clone Inventory to prevent ItemStacks count twice
		ItemStack[] inv = new ItemStack[player.inventory.mainInventory.length];
		for(int i = 0; i < player.inventory.mainInventory.length; i++){
			if(player.inventory.mainInventory[i] != null)
				inv[i] = player.inventory.mainInventory[i].copy();
		}
		// Current Stack to check if exists
		short currentStack = 0;
		if(input != null)
		for(ItemStack stack : input) {
			int remainingAmount = stack.stackSize * multiplier;
			boolean hasFoundMatch = true;
			while(hasFoundMatch && remainingAmount > 0) {
				hasFoundMatch = false;
				// Current Slot being checked for Item
				short i;
				if((i = getSlotContainingItem(inv, stack, respectMetaPrimary[currentStack])) > - 1) {
					if(inv[i].stackSize > remainingAmount) {
						inv[i].stackSize -= remainingAmount;
						remainingAmount = 0;
					} else if(inv[i].stackSize == remainingAmount) {
						inv[i] = null;
						remainingAmount = 0;
					} else if(inv[i].stackSize < remainingAmount) {
						remainingAmount -= inv[i].stackSize;
						inv[i] = null;
					}
					hasFoundMatch = true;
				}
			}
			if(remainingAmount > 0)
				return false;
			currentStack++ ;
		}
		return true;
	}
	
	/**
	 * Method for finding slot containing Item
	 * 
	 * @param Inventory
	 *            ItemStack[] that contains the Inventory that should be searched
	 * @param stack
	 *            The Searched ItemStack
	 * @param respectMeta
	 *            Should metadata be respected?
	 * @return ID of slot found matching ItemStack/Item
	 */
	private short getSlotContainingItem(ItemStack[] Inventory, ItemStack stack, boolean respectMeta) {

		short i = - 1;
		short found = - 1;
		for(ItemStack tested : Inventory) {
			i++ ;
			if(tested != null && tested.getItem() == stack.getItem()) {
				if(respectMeta)
					if(tested.getMetadata() == stack.getMetadata()) {
						found = i;
						return found;
					}
				found = i;
				return found;
			}
		}
		return found;
	}

	/**
	 * Returns a list of outputs for the given ingredients
	 * 
	 * @param primaryIngr
	 *            A List of ItemStacks that will be used on crafting
	 * @param secondaryIngr
	 *            A List of ItemStacks that will be used on crafting (This one might be null when
	 *            the users doesn't uses any secondary íngredients)
	 * @return A List of ItemStacks the Player will recieve
	 */
	public ItemStack[] getOutput(ItemStack[] primaryIngr, ItemStack[] secondaryIngr) {

		return new ItemStack[] {getDefaultOutput()};
	}

	/**
	 * Used to check if all Conditions are met.
	 * Can be customized returned Variable will be used in getUnmetConditionText()
	 * 
	 * @param primaryInput
	 *            The ItemStacks that will be used as Main Ingredients for crafting
	 * @param secondaryInput
	 *            The ItemStacks that will be used as Secondary Ingredients for crafting
	 * @param player
	 *            The Player that is supposed to craft this
	 * @param stationPos
	 *            The Position of the Crafting Station (if the item is crafted without a station
	 *            this will be null!) WorldObj can be obtained trough player
	 * @return ID of unmet Criteria for getUnmetConditionText()
	 *         Must return 0 = all Conditions are met
	 *         all other are unimportant and can be customized!
	 */
	public short doConditionsMet(ItemStack[] primaryInput, ItemStack[] secondaryInput,int multiplier, EntityPlayer player, BlockPos stationPos) {

		if( ! doesHaveIngredients(player,primaryInput , multiplier)) {
			return 1;
		}else if(! doesHaveIngredients(player,secondaryInput,multiplier)){
			return 1;
		}
		return 0;
	}
}
