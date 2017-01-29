package net.dark_roleplay.drpcore.client.gui.crafting.recipe_selection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import net.dark_roleplay.drpcore.api.gui.DRPGuiScreen;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.dark_roleplay.drpcore.common.crafting.CraftingRegistry;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.common.handler.DRPCoreConfigs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.translation.I18n;

public class RecipeSelection extends DRPGuiScreen{

	private static ResourceLocation bg = new ResourceLocation(DRPCoreInfo.MODID, "textures/guis/recipe_selection.png");
	
	private Map<String,List<String>> categorizedRecipes;
	private List<String> recipes;
	private List<String> unlockRecipes;
	private List<String> categorys = new ArrayList<String>();
	private Map<String, ItemStack> categoryPreviews = new HashMap<String, ItemStack>();

	private List<String> lockedRecipes;
	private Map<String,Float> progressedRecipes;

	
	private int recipePage = 0;
	private int maxRecipePages = 0;
	private int debug = 0;
	private int categoryOffset = 0;
	private short selectedCategory = 0;
	
	private Button_CategorySelect[] categoryButtons = new Button_CategorySelect[7]; 
	
	private Button_ChangePage prevPage;
	private Button_ChangePage nextPage;
	
	private Button_ChangeCategory prevCategory;
	private Button_ChangeCategory nextCategory;
	//private Button_ChangePage
	
	
	private boolean isInitialSetupDone = false;
	
	public RecipeSelection() {
		super(bg, 190, 133);
		categorizedRecipes = CraftingRegistry.getRecipesForStation(Blocks.AIR);
		if(categorizedRecipes != null){
			categorys.addAll(categorizedRecipes.keySet());
		}
		for(String category : categorys){
			categoryPreviews.put(category, CraftingRegistry.getRecipe(categorizedRecipes.get(category).get(0)).getMainOutput()[0]);
		}	
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
		
		updateRecipes();
		
		this.lockedRecipes = Minecraft.getMinecraft().thePlayer.getCapability(DRPCoreCapabilities.DRPCORE_RECIPE_CONTROLLER, null).getLockedRecipes();
		this.progressedRecipes = Minecraft.getMinecraft().thePlayer.getCapability(DRPCoreCapabilities.DRPCORE_RECIPE_CONTROLLER, null).getProgressedRecipes();
		
		System.out.println(this.lockedRecipes);
	}
	
	@Override
	protected void drawMiddleground(int mouseX, int mouseY, float partialTicks) {
		this.mc.renderEngine.bindTexture(this.bgTexture);

		drawTexturedModalRect(this.guiLeft + 15 + 23 * this.selectedCategory, this.guiTop, 214, 33, 22, 26);
	}
	
	@Override
	protected void drawForeground(int mouseX, int mouseY, float partialTicks) {
		
		for(int i = 0; i < 7; i++){
			if(i + this.categoryOffset < this.categorys.size())
				this.itemRender.renderItemIntoGUI(categoryPreviews.get(categorys.get(i + categoryOffset)), this.guiLeft + 18 + (23 * i), this.guiTop + 3);
		}
		
		if(this.recipes != null){
			for(int i = 0; i < 6; i++){
				for(int j = 0; j < 3; j++){
					if(this.recipes.size() > (i + (6 * j)) + (this.recipePage * 18)){
						String recipe = this.recipes.get((i + (j * 6)) + (this.recipePage * 18));
						
						if(this.lockedRecipes != null && this.lockedRecipes.contains(recipe)){
							this.mc.renderEngine.bindTexture(this.bgTexture);
							drawTexturedModalRect(this.guiLeft + 18 + (26 * i), this.guiTop + 40 + (26 * j), 27, 134, 24, 24);
						}else if(this.unlockRecipes.contains(recipe)){
							this.mc.renderEngine.bindTexture(this.bgTexture);
							drawTexturedModalRect(this.guiLeft + 18 + (26 * i), this.guiTop + 40 + (26 * j), 1, 134, 24, 24);
						//TODO REMOVE
						//if(this.progressedRecipes != null && this.progressedRecipes.keySet().contains(this.recipes.get((i + (j * 6)) + (this.recipePage * 18)))){
						}else{
							this.itemRender.renderItemIntoGUI(CraftingRegistry.getRecipe(this.recipes.get((i + (j * 6)) + (this.recipePage * 18))).getMainOutput()[0], this.guiLeft + 22 + (26 * i), this.guiTop + 44 + (26 * j));
						}
					}
				}
			}
		}
		
		
		//"Labels"
		String toPrint = this.fontRendererObj.trimStringToWidth(I18n.translateToLocal("crafting.category." + this.categorys.get(this.selectedCategory + this.categoryOffset)) + ".name", 156);
		this.fontRendererObj.drawString(toPrint, this.guiLeft + 95 - (this.fontRendererObj.getStringWidth(toPrint)/2), this.guiTop + 29, 16777215, true);
		
		toPrint = this.fontRendererObj.trimStringToWidth("Page " + String.valueOf(this.recipePage + 1) + "/" + String.valueOf(this.maxRecipePages + 1), 136);
		this.fontRendererObj.drawString(toPrint, this.guiLeft + 95 - (this.fontRendererObj.getStringWidth(toPrint)/2), this.guiTop + 119, 16777215, true);

	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {

		switch (button.id) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
		case 5:
		case 6:
			if(button.id + categoryOffset < categorys.size()){
				this.selectedCategory =(short) (button.id);
				this.updateRecipes();
			}
			break;
		case 7: //Prev Page
			this.recipePage--;
			this.nextPage.enabled = true;
			this.updateRecipes();
			break;
		case 8: //Next Page
			this.recipePage++;
			this.prevPage.enabled = true;
			this.updateRecipes();
			break;
		case 9: //Prev Category
			this.categoryOffset --;
			this.nextCategory.enabled = true;
			this.updateRecipes();
			break;
		case 10: //Next Category
			this.categoryOffset ++;
			this.prevCategory.enabled = true;
			this.updateRecipes();
			break;
		}
	}
	
	private void updateRecipes(){
		this.recipes = this.categorizedRecipes.get(this.categorys.get(this.categoryOffset + this.selectedCategory));
		this.unlockRecipes = new ArrayList<String>();
		
		Iterator<String> iter = recipes.iterator();
		
		while (iter.hasNext()) {
		    String recipe = iter.next();
			if(CraftingRegistry.requiresRecipeUnlock(recipe)){
				if(DRPCoreConfigs.HIDE_LOCKED_RECIPES){
					iter.remove();
				}
				unlockRecipes.add(recipe);
			}
		}
		
		if(DRPCoreConfigs.HIDE_LOCKED_RECIPES)
			this.maxRecipePages = ((this.recipes.size() - 1)) / 18;
		else
			this.maxRecipePages = ((this.recipes.size() - 1) + (this.unlockRecipes.size() - 1)) / 18;
		
		if(this.recipePage <= 0)
			this.prevPage.enabled = false;
		if(this.recipePage >= this.maxRecipePages)
			this.nextPage.enabled = false;
		if(this.categoryOffset <= 0)
			this.prevCategory.enabled = false;
		if(this.categoryOffset >= this.categorys.size() - 7)
			this.nextCategory.enabled = false;
	}

}
