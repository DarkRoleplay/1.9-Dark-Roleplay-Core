package net.drpcore.client.gui.crafting.guis;

import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.drpcore.api.guis.guis.DRPGuiScreen;
import net.drpcore.client.gui.buttons.rotateEntityRender;
import net.drpcore.client.gui.crafting.buttons.PrevNext;
import net.drpcore.client.gui.crafting.buttons.ingredientInfo;
import net.drpcore.client.gui.crafting.buttons.selectRecipe;
import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.crafting.AdvancedRecipe;
import net.drpcore.common.crafting.CraftingController;
import net.drpcore.common.gui.GuiHandler;
import net.drpcore.common.util.localization.DPRCoreLocalization;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;


public class RecipeSelection extends DRPGuiScreen {

	CraftingController cc = CraftingController.INSTANCE;
	
	EntityPlayer player = null;
	
	private Block station = null;

	private BlockPos stationPos = null;

	private String[] availableCategorys;
	
	private ArrayList<ItemStack> categoryPreviews = new ArrayList<ItemStack>();
	
	private short currentCategory = 0;

	private AdvancedRecipe[] availableRecipes;
	
	private short currentPage = 1;

	private short maxPages = 5;

	private String categoryName = "Category Placeholder";

	private PrevNext prevPage;
	private PrevNext nextPage;
	
	private PrevNext prevCategory;
	private PrevNext nextCategory;
	
	private selectRecipe[] chooseRecipe = new selectRecipe[18];
	
	public RecipeSelection(Block station, EntityPlayer player) {
		super(new ResourceLocation(DarkRoleplayCore.MODID + ":textures/guis/RecipeSelection.png"),178,160);
		this.player = player;
		this.station = station;
		availableCategorys = cc.getCategorysForStation(station);
		for(String category : availableCategorys){
			 categoryPreviews.add(cc.getRecipesForCategory(station, category)[0].getPreviewItem());
		}
		categoryName = I18n.translateToLocal("crafting." + availableCategorys[currentCategory] + ".name");
		availableRecipes = cc.getRecipesForCategory(station, availableCategorys[currentCategory]);
		maxPages = (short) Math.ceil((double)availableRecipes.length/18);
		System.out.println(availableRecipes);
	}

    public void initGui(){

		super.initGui();
		int buttonID = 0;
		prevPage = new PrevNext(buttonID++ , this.guiLeft + 11, this.guiTop + 142, false, 10, 11, 178, 0, new ResourceLocation(DarkRoleplayCore.MODID + ":textures/guis/RecipeSelection.png"));
		
		nextPage = new PrevNext(buttonID++ , this.guiLeft + 157, this.guiTop + 142, true, 10, 11, 178, 0, new ResourceLocation(DarkRoleplayCore.MODID + ":textures/guis/RecipeSelection.png"));
		
		prevCategory = new PrevNext(buttonID++ , this.guiLeft + 9, this.guiTop + 23, false, 10, 26, 198, 0, new ResourceLocation(DarkRoleplayCore.MODID + ":textures/guis/RecipeSelection.png"));
		
		nextCategory = new PrevNext(buttonID++ , this.guiLeft + 159, this.guiTop + 23, true, 10, 26, 198, 0, new ResourceLocation(DarkRoleplayCore.MODID + ":textures/guis/RecipeSelection.png") );
		
		for(int j = 0; j < 3; j ++){
			for(int i = 0; i < 6; i++){
				chooseRecipe[(j * 6) + i] = new selectRecipe(buttonID++, this.guiLeft + 12 + (i * 26), this.guiTop + 64 + (j * 26));
			}
		}
		
		buttonList.add(prevPage);
		buttonList.add(nextPage);
		buttonList.add(prevCategory);
		buttonList.add(nextCategory);
		
		for(int j = 0; j < 3; j ++){
			for(int i = 0; i < 6; i++){
			buttonList.add(chooseRecipe[(j * 6) + i]);
			}
		}
    }

	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		super.drawScreen(mouseX, mouseY, partialTicks);
		GL11.glColor4f(1, 1, 1, 1);
		GL11.glDisable(GL11.GL_LIGHTING);
		//Fix for Item Lighting
		RenderHelper.enableGUIStandardItemLighting();
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
	
			case 2:
				if(this.currentCategory - 1 >= 0){
					this.currentCategory --;
				}else{
					this.currentCategory = (short) (availableCategorys.length - 1);
				}
				this.currentPage = 1;
				this.availableRecipes = cc.getRecipesForCategory(this.station, this.availableCategorys[currentCategory]);
				this.maxPages = (short) Math.ceil((double)availableRecipes.length/18);
				this.categoryName = I18n.translateToLocal("crafting." + availableCategorys[currentCategory] + ".name");
				break;
				
			case 3:
				if(this.currentCategory + 1 <= this.availableCategorys.length - 1){
					this.currentCategory ++;
				}else{
					this.currentCategory = 0;
				}
				this.currentPage = 1;
				this.availableRecipes = cc.getRecipesForCategory(this.station, this.availableCategorys[currentCategory]);
				this.maxPages = (short) Math.ceil((double)availableRecipes.length/18);
				this.categoryName = I18n.translateToLocal("crafting." + availableCategorys[currentCategory] + ".name");
				break;
				
			default:				
				break;
		}
		
		if(button.id > 3 && button.id < 22){
			if(((currentPage - 1) * 18) + button.id - 4  < availableRecipes.length){
				GuiHandler.setRecipe(this.categoryName, availableRecipes[((currentPage - 1) * 18) + button.id - 4]);
				player.openGui(DarkRoleplayCore.instance, GuiHandler.GUI_CRAFTING_RECIPECRAFTING, player.worldObj, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
				GuiHandler.setRecipe(null, null);
			}}
	}

	@Override
	public boolean doesGuiPauseGame() {

		return true;
	}

	private boolean isMouseOverButton(selectRecipe btn, int mouseX, int mouseY)
    {
        return this.isPointInRegion(btn.xPosition, btn.yPosition, btn.width, btn.height, mouseX, mouseY);
    }
	
	protected boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY){
        return pointX >= rectX - 1 && pointX < rectX + rectWidth + 1 && pointY >= rectY - 1 && pointY < rectY + rectHeight + 1;
    }

	@Override
	protected void drawMiddleground(int mouseX, int mouseY, float partialTicks){
		super.drawMiddleground(mouseX, mouseY, partialTicks);
		//Crafting Station
		if(station != null) {
			//Render Station and Station Name
			if(station == Blocks.AIR){
				//Free Crafting
				this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Blocks.CRAFTING_TABLE), this.guiLeft + 5, this.guiTop + 5);
				this.fontRendererObj.drawString(this.fontRendererObj.trimStringToWidth(I18n.translateToLocal(DPRCoreLocalization.freeCraftingStation), 145), this.guiLeft + 25, this.guiTop + 9, 16777215, true);
			}else{
				this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(station), this.guiLeft + 5, this.guiTop + 5);
				//Stationed Crafting
				this.fontRendererObj.drawString(this.fontRendererObj.trimStringToWidth(station.getLocalizedName(), 145), this.guiLeft + 25, this.guiTop + 9, 16777215, true);
			}
		}
				
				
		//Category
			//Category Items
			//C-2 >= 0 ? C-2 : C - 1 > 0 ? M - 1 : M - 2 > 0 ? M - 2 : 0 
			this.itemRender.renderItemAndEffectIntoGUI(categoryPreviews.get(currentCategory -2 >= 0 ? currentCategory -2 : currentCategory -2 == -2  ? categoryPreviews.size() - 2 : categoryPreviews.size() - 1), this.guiLeft + 25, this.guiTop + 28);
			this.itemRender.renderItemAndEffectIntoGUI(categoryPreviews.get(currentCategory -1 >= 0 ? currentCategory -1 : categoryPreviews.size() - 1), this.guiLeft + 52, this.guiTop + 28);
			this.itemRender.renderItemAndEffectIntoGUI(categoryPreviews.get(currentCategory), this.guiLeft + 81, this.guiTop + 28);
			this.itemRender.renderItemAndEffectIntoGUI(categoryPreviews.get(currentCategory +1 <= categoryPreviews.size() - 1 ? currentCategory + 1 : 0), this.guiLeft + 110, this.guiTop + 28);
			this.itemRender.renderItemAndEffectIntoGUI(categoryPreviews.get(currentCategory +2 <= categoryPreviews.size() - 1 ? currentCategory + 2  : (currentCategory +1 == categoryPreviews.size() - 1 ? 0 : 0)), this.guiLeft + 137, this.guiTop + 28);		
			//Category Name
			int stringWidth = this.fontRendererObj.getStringWidth(this.fontRendererObj.trimStringToWidth(categoryName, 161));
			this.fontRendererObj.drawString(this.fontRendererObj.trimStringToWidth(categoryName, 161), this.guiLeft + (this.bgWidth / 2) - (stringWidth / 2), this.guiTop + 52, 16777215, true);
				
		//Recipes
			//Recipes
			for(int a = 0; a < 3; a++ ) {
				for(int b = 0; b < 6; b++ ) {
					if(((currentPage - 1) * 18) + (a * 5) + b  < availableRecipes.length){
						if(availableRecipes[((currentPage - 1) * 18) + (a * 5) + b ] != null){
							ItemStack previewItem = availableRecipes[((currentPage - 1) * 18) + (a * 5) + b ].getPreviewItem();
							this.itemRender.renderItemAndEffectIntoGUI(previewItem, this.guiLeft + 16 + (b * 26), this.guiTop + 68 + (a * 26));

						}
					}
				}
			}
			//Page Info
			stringWidth = this.fontRendererObj.getStringWidth(this.fontRendererObj.trimStringToWidth("Page " + String.valueOf(currentPage) + "/" + maxPages, 136));
			this.fontRendererObj.drawString(this.fontRendererObj.trimStringToWidth("Page " + String.valueOf(currentPage) + "/" + maxPages, 136), this.guiLeft + (this.bgWidth / 2) - (stringWidth / 2), this.guiTop + 142, 16777215, true);
		
	}
	
	@Override
	protected void drawForeground(int mouseX, int mouseY, float partialTicks) {
		//Hovering Text and Stuff
		for (int i1 = 0; i1 < 18; ++i1){
			if (this.isMouseOverButton(chooseRecipe[i1], mouseX, mouseY)){
				if(availableRecipes.length > (i1 + (18 * (currentPage - 1)))){
					renderToolTip(availableRecipes[(i1) + (18 * (currentPage - 1))].getPreviewItem() ,mouseX,mouseY);
				}
			}
		}
	}
	
}
