package net.dark_roleplay.drpcore.client.gui.crafting.recipe_selection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.lwjgl.opengl.GL11;

import net.dark_roleplay.drpcore.api.gui.DRPGuiScreen;
import net.dark_roleplay.drpcore.client.ClientProxy;
import net.dark_roleplay.drpcore.client.gui.crafting.recipe_crafting.RecipeCrafting_SimpleRecipe;
import net.dark_roleplay.drpcore.client.keybindings.DRPCoreKeybindings;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.dark_roleplay.drpcore.common.DarkRoleplayCore;
import net.dark_roleplay.drpcore.common.crafting.CraftingRegistry;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.common.handler.DRPCoreConfigs;
import net.dark_roleplay.drpcore.common.handler.DRPCoreGuis;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
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
	private List<String> unlockedRecipes;
	
	private int recipePage = 0;
	private int maxRecipePages = 0;
	private boolean debug = false;
	private int categoryOffset = 0;
	private short selectedCategory = 0;
	
	private Button_CategorySelect[] categoryButtons = new Button_CategorySelect[7]; 
	private Button_RecipeSelection[] recipeButtons = new Button_RecipeSelection[18];
	
	private Button_ChangePage prevPage;
	private Button_ChangePage nextPage;
	
	private Button_ChangeCategory prevCategory;
	private Button_ChangeCategory nextCategory;
	//private Button_ChangePage
	
	private BlockPos pos;
	
	
	
	private boolean isInitialSetupDone = false;
	
	public RecipeSelection(BlockPos pos) {
		super(bg, 190, 133);
		this.pos = pos;
		Block b = Minecraft.getMinecraft().world.getBlockState(pos).getBlock();
		this.categorizedRecipes = CraftingRegistry.getRecipesForStation(b);
		if(categorizedRecipes == null){
			categorizedRecipes = CraftingRegistry.getRecipesForStation(Blocks.AIR);
		}
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
		
		for(int i = 0; i < 18; i++){
			recipeButtons[i] = new Button_RecipeSelection(buttonID++, this.guiLeft + 18 + (i % 6) * 26, this.guiTop + 40 + (i/6) * 26);
			this.buttonList.add(recipeButtons[i]);
		}	
		
		this.lockedRecipes = Minecraft.getMinecraft().player.getCapability(DRPCoreCapabilities.DRPCORE_RECIPE_CONTROLLER, null).getLockedRecipes();
		this.progressedRecipes = Minecraft.getMinecraft().player.getCapability(DRPCoreCapabilities.DRPCORE_RECIPE_CONTROLLER, null).getProgressedRecipes();
		this.unlockedRecipes = Minecraft.getMinecraft().player.getCapability(DRPCoreCapabilities.DRPCORE_RECIPE_CONTROLLER, null).getUnlockedRecipes();
		
		updateRecipes();
		
		if(ClientProxy.useRecipeData){
			ClientProxy.useRecipeData = false;
			this.recipePage = ClientProxy.recipePage;
			this.selectedCategory = ClientProxy.selectedCategory;
			this.categoryOffset = ClientProxy.categoryOffset;
			updateRecipes();
		}
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
				this.itemRender.renderItemIntoGUI(categoryPreviews.get(categorys.get(i + categoryOffset)), this.guiLeft + 18 + (23 * i), this.guiTop + 3);
		}
		
		if(this.recipes != null){
			for(int i = 0; i < 18; i++){
					if(this.recipes.size() > (i) + (this.recipePage * 18)){
						String recipe = this.recipes.get((i) + (this.recipePage * 18));
						
						if(this.lockedRecipes != null && this.lockedRecipes.contains(recipe)){
							this.mc.renderEngine.bindTexture(this.bgTexture);
							drawTexturedModalRect(this.guiLeft + 18 + (26 * (i%6)), this.guiTop + 40 + (26 * (i/6)), 27, 134, 24, 24);
						}else if(this.unlockRecipes.contains(recipe)){
							this.mc.renderEngine.bindTexture(this.bgTexture);
							drawTexturedModalRect(this.guiLeft + 18 + (26 * (i%6)), this.guiTop + 40 + (26 * (i/6)), 1, 134, 24, 24);
							if(progressedRecipes.containsKey(recipe))
								drawTexturedModalRect(this.guiLeft + 18 + (26 * i%6), this.guiTop + 59 + (26 * i/6), 1, 160, 2 + (int)(20 * progressedRecipes.get(recipe)), 4);
						}else{
							this.itemRender.renderItemIntoGUI(CraftingRegistry.getRecipe(this.recipes.get(i + (this.recipePage * 18))).getMainOutput()[0], this.guiLeft + 22 + (26 * (i%6)), this.guiTop + 44 + (26 * (i/6)));
						}
					}
			}
		}
	}
	
	@Override
	protected void drawForeground(int mouseX, int mouseY, float partialTicks) {
		//"Labels"
		String toPrint = this.fontRenderer.trimStringToWidth(I18n.translateToLocal("crafting.category." + this.categorys.get(this.selectedCategory + this.categoryOffset) + ".name"), 156);
		this.fontRenderer.drawString(toPrint, this.guiLeft + 95 - (this.fontRenderer.getStringWidth(toPrint)/2), this.guiTop + 29, 16777215, true);
		
		toPrint = this.fontRenderer.trimStringToWidth("Page " + String.valueOf(this.recipePage + 1) + "/" + String.valueOf(this.maxRecipePages + 1), 136);
		this.fontRenderer.drawString(toPrint, this.guiLeft + 95 - (this.fontRenderer.getStringWidth(toPrint)/2), this.guiTop + 119, 16777215, true);

		for (int i = 0; i < 18; ++i){
			if (this.isMouseOverButton(recipeButtons[i], mouseX, mouseY)){
				int k = (18 * recipePage);
				if(recipes.size() > k + i){
					renderToolTip(CraftingRegistry.getRecipe(recipes.get(i + k)).getMainOutput()[0] ,mouseX,mouseY);
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
				this.recipePage = 0;
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
			this.recipePage = 0;
			this.nextCategory.enabled = true;
			this.updateRecipes();
			break;
		case 10: //Next Category
			this.categoryOffset ++;
			this.recipePage = 0;
			this.prevCategory.enabled = true;
			this.updateRecipes();
			break;
		case 11:
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
		case 17:
		case 18:
		case 19:
		case 20:
		case 21:
		case 22:
		case 23:
		case 24:
		case 25:
		case 26:
		case 27:
		case 28:
			EntityPlayer player = Minecraft.getMinecraft().player;
			int recipeID = button.id - 11 + (this.recipePage * 18);
			if(recipeID < this.recipes.size() && !this.lockedRecipes.contains(this.recipes.get(recipeID)) && !this.unlockRecipes.contains(this.recipes.get(recipeID))){
				RecipeCrafting_SimpleRecipe.setRecipe(CraftingRegistry.getRecipe(this.recipes.get(recipeID)));
				ClientProxy.recipePage = this.recipePage;
				ClientProxy.selectedCategory = this.selectedCategory;
				ClientProxy.categoryOffset = this.categoryOffset;
				player.openGui(DarkRoleplayCore.instance, DRPCoreGuis.DRPCORE_GUI_CRAFTING_RECIPECRAFTING_SIMPLE, player.world, pos.getX(), pos.getY(), pos.getZ());
			}
			break;
		}
	}
	
	private void updateRecipes(){
		this.recipes = this.categorizedRecipes.get(this.categorys.get(this.categoryOffset + this.selectedCategory));
		this.unlockRecipes = new ArrayList<String>();
		
		Iterator<String> iter = recipes.iterator();
		
		while (iter.hasNext()) {
		    String recipe = iter.next();
			if(CraftingRegistry.requiresRecipeUnlock(recipe) && !this.unlockedRecipes.contains(recipe)){
				if(DRPCoreConfigs.HIDE_LOCKED_RECIPES){
					iter.remove();
				}
				unlockRecipes.add(recipe);
			}
		}
		
		if(DRPCoreConfigs.HIDE_LOCKED_RECIPES)
			this.maxRecipePages = (((this.recipes.size() - 1)) / 18);
		else
			this.maxRecipePages = (((this.recipes.size() - 1) + (this.unlockRecipes.size() - 1)) / 18);
		
		if(this.recipePage <= 0)
			this.prevPage.enabled = false;
		else 
			this.prevPage.enabled = true;
		
		if(this.recipePage >= this.maxRecipePages)
			this.nextPage.enabled = false;
		else 
			this.nextPage.enabled = true;
		
		if(this.categoryOffset <= 0)
			this.prevCategory.enabled = false;
		else 
			this.prevCategory.enabled = true;
		
		if(this.categoryOffset >= this.categorys.size() - 7)
			this.nextCategory.enabled = false;
		else 
			this.nextCategory.enabled = true;
	}
    
    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException{
        if (keyCode == 1 || DRPCoreKeybindings.openCrafting.isActiveAndMatches(keyCode) || this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode)){
            this.mc.player.closeScreen();
        }
    }
}
