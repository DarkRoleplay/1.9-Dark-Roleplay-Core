package net.dark_roleplay.core.client.gui.crafting.recipe_selection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.dark_roleplay.core.References;
import net.dark_roleplay.core.api.old.crafting.Crafting_Util;
import net.dark_roleplay.core.api.old.crafting.simple_recipe.IRecipe;
import net.dark_roleplay.core.api.old.crafting.simple_recipe.RecipeCategory;
import net.dark_roleplay.core.api.old.crafting.simple_recipe.SimpleRecipe;
import net.dark_roleplay.core.api.old.gui.DRPGuiScreen;
import net.dark_roleplay.core.client.gui.crafting.recipe_crafting.RecipeCrafting_SimpleRecipe;
import net.dark_roleplay.core.client.keybindings.DRPCoreKeybindings;
import net.dark_roleplay.core.common.config.Debug;
import net.dark_roleplay.core.common.crafting.CraftingRegistry;
import net.dark_roleplay.core.common.handler.DRPCoreConfigs;
import net.dark_roleplay.core.common.util.toasts.ToastController;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;

public class RecipeSelection extends DRPGuiScreen{

	private static ResourceLocation bg = new ResourceLocation(References.MODID, "textures/guis/recipe_selection.png");
	
	private Block craftingStation;
	private List<RecipeCategory> categorys;
	
	private List<IRecipe> recipes;
	
	private int recipePage = 0;
	private int maxRecipePages = 0;
	private int categoryOffset = 0;
	private short selectedCategory = 0;
	
	private EntityPlayer player;
//	private IRecipeController rcCon;
	
	private Button_CategorySelect[] categoryButtons = new Button_CategorySelect[7]; 
	private Button_RecipeSelection[] recipeButtons = new Button_RecipeSelection[18];
	
	private Button_ChangePage prevPage;
	private Button_ChangePage nextPage;
	
	private Button_ChangeCategory prevCategory;
	private Button_ChangeCategory nextCategory;
	//private Button_ChangePage
	
	private Button_Hide hideUnknown;
	private Button_Hide hideLocked;
	
	private BlockPos pos;
	
	public RecipeSelection(Block block) {
		super(bg, 190, 133);
		player = Minecraft.getMinecraft().player;
//		rcCon = player.getCapability(DRPCoreCapabilities.DRPCORE_RECIPE_CONTROLLER, null);
		
		this.craftingStation = block;
		this.categorys = CraftingRegistry.getCategorysForBlocks(block);
	}

	@Override
	public void onGuiClosed(){
		Crafting_Util.exit();
    }
	
	@Override
	public void initGui(){
		super.initGui();
		short buttonID = 0;
		for(int i = 0; i < 7; i ++){
			categoryButtons[i] = new Button_CategorySelect(buttonID++, this.guiLeft + 18 + (i * 23), this.guiTop + 3);
			this.buttonList.add(categoryButtons[i]);
		}
		prevPage = new Button_ChangePage(buttonID++, this.guiLeft + 17, this.guiTop + 118 , false);
		nextPage = new Button_ChangePage(buttonID++, this.guiLeft + 163, this.guiTop + 118 , true);
		this.buttonList.add(prevPage);
		this.buttonList.add(nextPage);
		
		prevCategory = new Button_ChangeCategory(buttonID++, this.guiLeft + 2, this.guiTop + 2 , false);
		nextCategory = new Button_ChangeCategory(buttonID++, this.guiLeft + 178, this.guiTop +2 , true);
		this.buttonList.add(prevCategory);
		this.buttonList.add(nextCategory);
		
		for(int i = 0; i < 18; i++){
			recipeButtons[i] = new Button_RecipeSelection(buttonID++, this.guiLeft + 18 + (i % 6) * 26, this.guiTop + 40 + (i/6) * 26);
			this.buttonList.add(recipeButtons[i]);
		}	
		
		this.addButton(this.hideUnknown = new Button_Hide(buttonID++, this.guiLeft + 173, this.guiTop + 39, !DRPCoreConfigs.CRAFTING.HIDE_UNKNOWN_RECIPES));
		this.addButton(this.hideLocked = new Button_Hide(buttonID++, this.guiLeft + 173, this.guiTop + 49, !DRPCoreConfigs.CRAFTING.HIDE_LOCKED_RECIPES));

		if(this.categorys == null){
			ToastController.displayInfoToast("No recipes available", null);
			Minecraft.getMinecraft().displayGuiScreen(null);
			return;
		}
		
		updateRecipes();
	}
	
	@Override
	protected void drawMiddleground(int mouseX, int mouseY, float partialTicks) {
		GL11.glDisable(GL11.GL_LIGHTING);
		this.mc.renderEngine.bindTexture(this.bgTexture);

		drawTexturedModalRect(this.guiLeft + 15 + 23 * this.selectedCategory, this.guiTop, 214, 33, 22, 26);
		GL11.glEnable(GL11.GL_LIGHTING);
		
		
		//Recipes and Co.
		for(int i = 0; i < 7; i++){
			if(i + this.categoryOffset < this.categorys.size())
				this.itemRender.renderItemIntoGUI(categorys.get(i + categoryOffset).getPreviewStack(), this.guiLeft + 18 + (23 * i), this.guiTop + 3);
		}
		
		for(int i = 0; i < 18; i++){
			if(this.recipes.size() > (i + (this.recipePage * 18))){
				IRecipe rec = this.recipes.get((i + (this.recipePage * 18)));
				this.itemRender.renderItemIntoGUI(rec.getDisplayItems()[0], this.guiLeft + 22 + (26 * (i%6)), this.guiTop + 44 + (26 * (i/6)));
			}
		}
	}
	
	@Override
	protected void drawForeground(int mouseX, int mouseY, float partialTicks) {
		//"Labels"
		String toPrint = this.fontRenderer.trimStringToWidth(I18n.format("crafting.category." + this.categorys.get(this.selectedCategory + this.categoryOffset).getCategoryName() + ".name"), 156);
		this.fontRenderer.drawString(toPrint, this.guiLeft + 95 - (this.fontRenderer.getStringWidth(toPrint)/2), this.guiTop + 29, 16777215, true);
		
		toPrint = this.fontRenderer.trimStringToWidth("Page " + String.valueOf(this.recipePage + 1) + "/" + String.valueOf(this.maxRecipePages), 136);
		this.fontRenderer.drawString(toPrint, this.guiLeft + 95 - (this.fontRenderer.getStringWidth(toPrint)/2), this.guiTop + 119, 16777215, true);

		for (int i = 0; i < 18; ++i){
			if (this.isMouseOverButton(recipeButtons[i], mouseX, mouseY)){
				int k = (18 * recipePage);
				if(recipes.size() > k + i){
					renderToolTip(recipes.get(i + k).getDisplayItems()[0] ,mouseX,mouseY);
					if(Debug.DEBUG_RECIPE_NAMES){
						this.fontRenderer.drawString(recipes.get(i + k).getRegistryString(), 5, this.height - 25, 16777215);
					}
				}
			}
		}
	}
	
	private boolean isMouseOverButton(Button_RecipeSelection btn, int mouseX, int mouseY){
        return this.isPointInRegion(btn.x, btn.y, btn.width, btn.height, mouseX, mouseY);
    }
	
	protected boolean isPointInRegion(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY){
        return pointX >= rectX - 1 && pointX < rectX + rectWidth + 1 && pointY >= rectY - 1 && pointY < rectY + rectHeight + 1;
    }
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {

		if(button.id == hideUnknown.id){
			DRPCoreConfigs.CRAFTING.HIDE_UNKNOWN_RECIPES = !DRPCoreConfigs.CRAFTING.HIDE_UNKNOWN_RECIPES;
			ConfigManager.sync(References.MODID, Config.Type.INSTANCE);
			this.hideUnknown.active = !DRPCoreConfigs.CRAFTING.HIDE_UNKNOWN_RECIPES;
			this.updateRecipes();
			this.updateButtons();
		}else if(button.id == hideLocked.id){
			DRPCoreConfigs.CRAFTING.HIDE_LOCKED_RECIPES = !DRPCoreConfigs.CRAFTING.HIDE_LOCKED_RECIPES;
			ConfigManager.sync(References.MODID, Config.Type.INSTANCE);
			this.hideLocked.active = !DRPCoreConfigs.CRAFTING.HIDE_LOCKED_RECIPES;
			this.updateRecipes();
			this.updateButtons();
		}else if(button.id == prevPage.id){
			this.recipePage--;
			this.updateButtons();
		}else if(button.id == nextPage.id){
			this.recipePage++;
			this.updateButtons();
		}else if(button.id == prevCategory.id){
			if(categoryOffset -1 >= 0){
				this.categoryOffset --;
				this.recipePage = 0;
				this.updateRecipes();
				this.updateButtons();
			}
		}else if(button.id == nextCategory.id){
			if((categoryOffset + this.selectedCategory) + 1 < categorys.size()){
				this.categoryOffset ++;
				this.recipePage = 0;
				this.prevCategory.enabled = true;
				this.updateRecipes();
				this.updateButtons();
			}
		}else if(button.id >= categoryButtons[0].id && button.id <= categoryButtons[6].id){
			if(button.id + categoryOffset < categorys.size()){
				this.selectedCategory = (short) (button.id);
				this.recipePage = 0;
				this.updateRecipes();
				this.updateButtons();
			}
		}else if(button.id >= this.recipeButtons[0].id && button.id <= this.recipeButtons[17].id){
			int recipeID = (button.id - recipeButtons[0].id) + (this.recipePage * 18);
			if(recipeID < this.recipes.size() && this.recipes.get(recipeID).canCraft(this.player)){ // && !rcCon.isLocked(this.recipes.get(recipeID).getRegistryString())
				
				if(this.recipes.get(recipeID) instanceof SimpleRecipe){
					Minecraft.getMinecraft().displayGuiScreen(new RecipeCrafting_SimpleRecipe((SimpleRecipe) this.recipes.get(recipeID), this));
				}
			}
		}
	}
	
	private void updateRecipes(){
		this.recipes = new ArrayList<IRecipe>();
		List<IRecipe> tmpRecipes = this.categorys.get(this.selectedCategory + this.categoryOffset).getRecipes();
	
		for(IRecipe rec : tmpRecipes){
			if(rec.requiresUnlock() && !rec.canCraft(this.player) && DRPCoreConfigs.CRAFTING.HIDE_UNKNOWN_RECIPES){
				continue;
			}
			if(DRPCoreConfigs.CRAFTING.HIDE_LOCKED_RECIPES){// && this.rcCon.isLocked(rec.getRegistryString())
				continue;
			}
			this.recipes.add(rec);
		}

		this.recipePage = 0;
		this.prevPage.enabled = false;
		this.maxRecipePages = (int) Math.ceil(this.recipes.size() / 18F);
		if(this.maxRecipePages == 1)
			this.nextPage.enabled = false;
	}
	
	private void updateButtons(){
		if(this.recipePage < this.maxRecipePages -1){
			this.nextPage.enabled = true;
		}else{
			this.nextPage.enabled = false;
		}
		if(this.recipePage > 0){
			this.prevPage.enabled = true;
		}else{
			this.prevPage.enabled = false;
		}
	}
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException{
        if (keyCode == 1 || DRPCoreKeybindings.GUI_CRAFTING.isActiveAndMatches(keyCode) || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode)){
            this.mc.player.closeScreen();
        }
    }
}
