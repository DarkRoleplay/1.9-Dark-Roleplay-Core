package net.drpcore.client.gui.crafting.guis;

import java.io.IOException;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.drpcore.client.gui.buttons.rotateEntityRender;
import net.drpcore.client.gui.crafting.buttons.changePage;
import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.crafting.AdvancedRecipe;
import net.drpcore.common.crafting.CraftingController;
import net.drpcore.common.util.localization.DPRCoreLocalization;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;


public class RecipeSelection extends GuiScreen {

	public static final ResourceLocation BACKGROUND = new ResourceLocation(DarkRoleplayCore.MODID + ":textures/guis/RecipeSelection.png");

	private int xSize = 178;

	private int ySize = 160;

	private Block station = null;

	private BlockPos stationPos = null;

	private String[] availableCategorys;
	
	private short currentCategory = 0;

	private AdvancedRecipe[] availableRecipes;
	
	private short currentPage = 1;

	private short maxPages = 5;

	private String categoryName = "Category Placeholder";

	public byte currentTicks = 0;

	public int passedSeconds = 0;

	private changePage prevPage;
	private changePage nextPage;
	
	public RecipeSelection(Block station) {
		this.station = station;
		availableCategorys = CraftingController.getCategorysForStation(station);
		categoryName = I18n.translateToLocal("crafting." + availableCategorys[currentCategory] + ".name");
		availableRecipes = CraftingController.getRecipesForCategory(station, availableCategorys[currentCategory]);
		maxPages = (short) Math.ceil((double)availableRecipes.length/18);
		System.out.println(availableRecipes);
	}

    public void initGui(){

		super.initGui();
		int buttonID = 0;
		int posX = ((this.width - this.xSize) / 2);
		int posY = (this.height - this.ySize) / 2;
		prevPage = new changePage(buttonID++ , posX + 11, posY + 142, false);
		
		nextPage = new changePage(buttonID++ , posX + 157, posY + 142, true);
		
		buttonList.add(prevPage);
		buttonList.add(nextPage);
    }

	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		mc.renderEngine.bindTexture(BACKGROUND);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
		super.drawScreen(mouseX, mouseY, partialTicks);
		//GL11.glColor4f(1, 1, 1, 1);
		GL11.glDisable(GL11.GL_LIGHTING);
		//Fix for Item Lighting
		RenderHelper.enableGUIStandardItemLighting();
		
		
		//Crafting Station
		if(station != null) {
			//Render Station and Station Name
			if(station == Blocks.AIR){
				//Free Crafting
				this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Blocks.CRAFTING_TABLE), k + 5, l + 5);
				this.fontRendererObj.drawString(this.fontRendererObj.trimStringToWidth(I18n.translateToLocal(DPRCoreLocalization.freeCraftingStation), 145), k + 25, l + 9, 16777215, true);
			}else{
				//Stationed Crafting
				this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(station), k + 5, l + 5);
				this.fontRendererObj.drawString(this.fontRendererObj.trimStringToWidth(station.getLocalizedName(), 145), k + 25, l + 9, 16777215, true);
			}
		}
		
		
		//Category
		
		//Category Items
		
		
		//Category Name
		int stringWidth = this.fontRendererObj.getStringWidth(this.fontRendererObj.trimStringToWidth(categoryName, 161));
		this.fontRendererObj.drawString(this.fontRendererObj.trimStringToWidth(categoryName, 161), k + (xSize / 2) - (stringWidth / 2), l + 52, 16777215, true);
		
		
		//Recipes
		for(int a = 0; a < 3; a++ ) {
			for(int b = 0; b < 6; b++ ) {
				if(((currentPage - 1) * 18) + (a * 5) + b  < availableRecipes.length){
					if(availableRecipes[((currentPage - 1) * 18) + (a * 5) + b ] != null){
						ItemStack previewItem = new ItemStack(availableRecipes[((currentPage - 1) * 18) + (a * 5) + b ].getPreviewItem());
						this.itemRender.renderItemAndEffectIntoGUI(previewItem, k + 16 + (b * 26), l + 68 + (a * 26));

					}
				}
			}
		}
		
		//Page
		stringWidth = this.fontRendererObj.getStringWidth(this.fontRendererObj.trimStringToWidth("Page " + String.valueOf(currentPage) + "/" + maxPages, 136));
		this.fontRendererObj.drawString(this.fontRendererObj.trimStringToWidth("Page " + String.valueOf(currentPage) + "/" + maxPages, 136), k + (xSize / 2) - (stringWidth / 2), l + 142, 16777215, true);
		
		//Debuging
		this.fontRendererObj.drawString(String.valueOf(this.passedSeconds), 50, 50, 16777215);
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {

		switch (button.id) {
			//Prev Page
			case 0:
				if(this.currentPage - 1 >= 1){
					this.currentPage --;
				}else{
					this.currentPage = this.maxPages;
				}
				break;
			//Next Page
			case 1:
				if(this.currentPage + 1 <= this.maxPages){
					this.currentPage ++;
				}else{
					this.currentPage = 1;
				}
				break;
		}
	}

	@Override
	public boolean doesGuiPauseGame() {

		return true;
	}
	//@Override
	//	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
	//}
}
