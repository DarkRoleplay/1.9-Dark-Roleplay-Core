package net.drpcore.client.gui.guis;

import java.io.IOException;
import java.util.ArrayList;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.gui.buttons.craftCategoryButton;
import net.drpcore.common.gui.buttons.craftIngredientButton;
import net.drpcore.common.gui.buttons.craftLaunchCraftButton;
import net.drpcore.common.gui.buttons.craftPageButton;
import net.drpcore.common.network.PacketHandler;
import net.drpcore.common.network.packets.PacketCraft;
import net.drpcore.common.util.crafting.CraftingManager;
import net.drpcore.common.util.crafting.CraftingRecipe;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;


public class CraftingGui extends GuiContainer {

	public static final ResourceLocation BACKGROUND = new ResourceLocation(DarkRoleplayCore.MODID + ":textures/guis/GuiCrafting.png");

	CraftingManager cm = new CraftingManager();

	EntityPlayer player = null;

	private int xSize = 178;

	private int ySize = 204;

	private Block craftingStation = null;

	private ArrayList<String> CategoryList;

	private int currentCategoryID = 0;

	private String categoryName = null;

	private int selectedRecipe = 0;

	private int recipeCount = 0;

	private int currentPage = 1;

	private int maxPages = 1;

	private int maxIngredients = 0;

	private int currentIngredients = 0;

	private int maxAddIngredients = 0;

	private int currentAddIngredients = 0;

	private ArrayList<Integer> selectedIngredients = new ArrayList<Integer>();

	craftCategoryButton prevCategory;

	craftCategoryButton nextCategory;

	// Recipe Pages
	craftPageButton prevPage;

	craftPageButton nextPage;

	// IngredientButtons
	craftIngredientButton prevMIngr;

	craftIngredientButton nextMIngr;

	craftIngredientButton prevAIngr;

	craftIngredientButton nextAIngr;

	// Craft Button
	craftLaunchCraftButton launchCraft;

	long selectedAdditionalIngredients;

	public CraftingGui(Container container, EntityPlayer player, Block craftingStation) {
		super(container);
		this.player = player;
		this.craftingStation = craftingStation;
		this.CategoryList = CraftingManager.getCategoryList(craftingStation);
		if(this.CategoryList != null)
			this.categoryName = this.CategoryList.get(this.currentCategoryID);
		this.maxPages = CraftingManager.getRecipeAmount(craftingStation, this.categoryName);
		this.recipeCount = CraftingManager.getRecipeAmount(craftingStation, this.categoryName);
	}

	@Override
	public void initGui() {

		super.initGui();
		int buttonID = 0;
		int posX = (this.width - this.xSize) / 2;
		int posY = (this.height - this.ySize) / 2;
		prevCategory = new craftCategoryButton(buttonID++ , posX + 8, posY + 4, false);
		nextCategory = new craftCategoryButton(buttonID++ , posX + 160, posY + 4, true);
		prevPage = new craftPageButton(buttonID++ , posX + 10, posY + 22, false);
		nextPage = new craftPageButton(buttonID++ , posX + 10, posY + 112, true);
		prevMIngr = new craftIngredientButton(buttonID++ , posX + 54, posY + 32, false);
		nextMIngr = new craftIngredientButton(buttonID++ , posX + 156, posY + 32, true);
		prevAIngr = new craftIngredientButton(buttonID++ , posX + 54, posY + 59, false);
		nextAIngr = new craftIngredientButton(buttonID++ , posX + 156, posY + 59, true);
		launchCraft = new craftLaunchCraftButton(buttonID++ , posX + 142, posY + 96);
		buttonList.add(prevCategory);
		buttonList.add(nextCategory);
		buttonList.add(prevPage);
		buttonList.add(nextPage);
		buttonList.add(prevMIngr);
		buttonList.add(nextMIngr);
		buttonList.add(prevAIngr);
		buttonList.add(nextAIngr);
		buttonList.add(launchCraft);
		prevPage.enabled = false;
		if(this.recipeCount > 0)
			nextPage.enabled = true;
		else {
			nextPage.enabled = false;
		}
		CraftingRecipe recipe = CraftingManager.getRecipe(this.craftingStation, this.categoryName, this.selectedRecipe);
		if(recipe.getMainIngredients() != null)
			this.maxIngredients = recipe.getMainIngredients().length;
		if(recipe.getAdditionalIngredients() != null)
			this.maxAddIngredients = recipe.getAdditionalIngredients().length;
		updateIngredients();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

		mc.renderEngine.bindTexture(BACKGROUND);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
		if(this.selectedRecipe - 1 >= 0) {
			drawTexturedModalRect(k + 12, l + 33, 178, 164, 26, 26);
		}
		if(this.selectedRecipe + 1 < this.recipeCount) {
			drawTexturedModalRect(k + 12, l + 85, 178, 164, 26, 26);
		}
		drawList(partialTicks, k, l);
		drawCategory();
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {

		switch (button.id) {
			// Prev Category
			case 0:
				if(this.currentCategoryID - 1 >= 0)
					this.currentCategoryID -= 1;
				else
					this.currentCategoryID = this.CategoryList.size() - 1;
				if(this.CategoryList != null)
					this.categoryName = this.CategoryList.get(this.currentCategoryID);
				changeCategory();
				break;
			// Next Category
			case 1:
				if( ! (this.currentCategoryID + 1 >= this.CategoryList.size()))
					this.currentCategoryID += 1;
				else
					this.currentCategoryID = 0;
				if(this.CategoryList != null)
					this.categoryName = this.CategoryList.get(this.currentCategoryID);
				changeCategory();
				break;
			// Prev Recipe
			case 2:
				if(this.selectedRecipe - 1 >= 0) {
					this.selectedRecipe -= 1;
					this.nextPage.enabled = true;
				}
				if(this.selectedRecipe - 1 < 0)
					this.prevPage.enabled = false;
				updateIngredients();
				break;
			// Next Recipe
			case 3:
				if(this.selectedRecipe + 1 < this.recipeCount) {
					this.selectedRecipe += 1;
					this.prevPage.enabled = true;
				}
				if(this.selectedRecipe + 2 > this.recipeCount)
					this.nextPage.enabled = false;
				updateIngredients();
				break;
			// Prev Ingredients
			case 4:
				if(this.currentIngredients - 2 < 0) {
					this.prevMIngr.enabled = false;
				}
				if(this.currentIngredients - 1 >= 0) {
					this.currentIngredients -= 1;
					this.nextMIngr.enabled = true;
				}
				break;
			// Next Ingredients
			case 5:
				if(this.currentIngredients + 6 >= this.maxIngredients) {
					this.nextMIngr.enabled = false;
				}
				if(this.currentIngredients + 1 < this.maxIngredients) {
					this.currentIngredients += 1;
					this.prevMIngr.enabled = true;
				}
				break;
			case 8:
				PacketHandler.sendToServer(new PacketCraft(this.craftingStation, this.categoryName, this.selectedRecipe));
				break;
		}
	}

	private void updateIngredients() {

		CraftingRecipe recipe = CraftingManager.getRecipe(this.craftingStation, this.categoryName, this.selectedRecipe);
		this.currentIngredients = 0;
		this.prevMIngr.enabled = false;
		if(recipe.getMainIngredients() != null)
			this.maxIngredients = recipe.getMainIngredients().length;
		else
			this.maxIngredients = 0;
		if(5 <= this.maxIngredients) {
			this.nextMIngr.enabled = true;
		} else {
			this.nextMIngr.enabled = false;
		}
		this.currentAddIngredients = 0;
		this.prevAIngr.enabled = false;
		if(recipe.getAdditionalIngredients() != null)
			this.maxAddIngredients = recipe.getAdditionalIngredients().length;
		else
			this.maxAddIngredients = 0;
		if(5 <= this.maxAddIngredients) {
			this.nextAIngr.enabled = true;
		} else {
			this.nextAIngr.enabled = false;
		}
		this.selectedIngredients = new ArrayList<Integer>();
	}

	private void changeCategory() {

		this.currentIngredients = 0;
		this.currentAddIngredients = 0;
		this.currentPage = 0;
		this.selectedRecipe = 0;
		this.recipeCount = CraftingManager.getRecipeAmount(craftingStation, this.categoryName);
		prevPage.enabled = false;
		if(this.recipeCount > 0)
			nextPage.enabled = true;
		else {
			nextPage.enabled = false;
		}
		updateIngredients();
	}

	private void drawCategory() {

		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		if(categoryName != null)
			this.mc.fontRendererObj.drawString(categoryName, x + 88 - ((categoryName.length() / 2) * 5), y + 8, 0xffFFFFFF);
	}

	private void drawList(float partialTicks, int x, int y) {

		int x0 = 16 + x;
		int y0 = 37 + y;
		int x1 = 16 + x;
		int y1 = 63 + y;
		int x2 = 16 + x;
		int y2 = 89 + y;
		if(this.selectedRecipe - 1 >= 0) {
			CraftingRecipe recipe1 = CraftingManager.getRecipe(this.craftingStation, this.categoryName, this.selectedRecipe - 1);
			if(recipe1 != null)
				drawItemStack(recipe1.getOutput(null), x0, y0, 2);
		}
		CraftingRecipe recipe2 = CraftingManager.getRecipe(this.craftingStation, this.categoryName, this.selectedRecipe);
		if(recipe2 != null) {
			drawItemStack(recipe2.getOutput(null), x1, y1, 2);
			drawIngredients(recipe2, x, y);
		}
		if(this.selectedRecipe + 1 < this.recipeCount) {
			CraftingRecipe recipe3 = CraftingManager.getRecipe(this.craftingStation, this.categoryName, this.selectedRecipe + 1);
			if(recipe3 != null)
				drawItemStack(recipe3.getOutput(null), x2, y2, 2);
		}
	}

	private void drawIngredients(CraftingRecipe recipe, int x, int y) {

		ItemStack[] mIngr = recipe.getMainIngredients();
		ItemStack[] aIngr = recipe.getAdditionalIngredients();
		ItemStack Output = recipe.getOutput(null);
		// 66,33
		// 57 81
		drawItemStack(Output, x + 57, y + 81, 1);
		this.mc.fontRendererObj.drawString(Output.getDisplayName(), x + 75, y + 81, 0xffFFFFFF);
		for(int i = 0; i < 5; i++ ) {
			if(this.currentIngredients + i < mIngr.length)
				drawItemStack(mIngr[this.currentIngredients + i], x + 66 + (18 * i), y + 33, hasItemStack(mIngr[this.currentIngredients + i], recipe.shouldIgnoreMeta()));
		}
		if(aIngr != null) {
			for(int i = 0; i < 5; i++ ) {
				if(this.currentAddIngredients + i < aIngr.length)
					drawItemStack(aIngr[this.currentAddIngredients + i], x + 66 + (18 * i), y + 60, hasItemStack(aIngr[this.currentAddIngredients + i], recipe.shouldIgnoreMeta()));
			}
		}
	}

	private void drawItemStack(ItemStack stack, int x, int y, int hasNumber) {

		if(stack != null) {
			RenderHelper.enableGUIStandardItemLighting();
			Integer stackSize = stack.stackSize;
			String stackSizeString = stackSize.toString();
			GlStateManager.translate(0.0F, 0.0F, 32.0F);
			this.zLevel = 200.0F;
			this.itemRender.zLevel = 200.0F;
			FontRenderer font = null;
			font = stack.getItem().getFontRenderer(stack);
			if(font == null)
				font = fontRendererObj;
			this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
			if(hasNumber == 1)
				this.itemRender.renderItemOverlayIntoGUI(font, stack, x, y, stackSizeString);
			else if(hasNumber == 0)
				this.itemRender.renderItemOverlayIntoGUI(font, stack, x, y, TextFormatting.RED + stackSizeString);
			this.zLevel = 0.0F;
			this.itemRender.zLevel = 0.0F;
			RenderHelper.disableStandardItemLighting();
		}
	}

	private int hasItemStack(ItemStack stack, boolean ignoreMeta) {

		int i;
		InventoryPlayer mainInventory = player.inventory;
		int missingAmount = stack.stackSize;
		for(i = 0; i < mainInventory.getSizeInventory(); ++i) {
			if(mainInventory.getStackInSlot(i) != null && stack != null && mainInventory.getStackInSlot(i).getItem() == stack.getItem()) {
				if(ignoreMeta) {
					if(mainInventory.getStackInSlot(i).stackSize >= stack.stackSize) {
						return 1;
					} else if(mainInventory.getStackInSlot(i).stackSize <= missingAmount) {
						missingAmount -= mainInventory.getStackInSlot(i).stackSize;
					} else {
						missingAmount = 0;
					}
				} else if( ! ignoreMeta && mainInventory.getStackInSlot(i).getMetadata() == stack.getMetadata()) {
					if(mainInventory.getStackInSlot(i).stackSize >= stack.stackSize) {
						return 1;
					} else if(mainInventory.getStackInSlot(i).stackSize <= missingAmount) {
						missingAmount -= mainInventory.getStackInSlot(i).stackSize;
					} else {
						missingAmount = 0;
					}
				}
				if(missingAmount == 0)
					return 1;
			}
		}
		return 0;
	}
}
