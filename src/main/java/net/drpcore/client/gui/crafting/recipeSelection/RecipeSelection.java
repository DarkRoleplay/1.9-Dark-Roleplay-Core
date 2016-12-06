package net.drpcore.client.gui.crafting.recipeSelection;

import java.util.List;
import java.util.Map;

import net.drpcore.api.guis.guis.DRPGuiScreen;
import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.crafting.CraftingRegistry;
import net.drpcore.common.util.localization.DPRCoreLocalization;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;

public class RecipeSelection extends DRPGuiScreen {

	EntityPlayer player = null;
	
	private Block station = null;
	
	private BlockPos stationPos = null;
	
	private SelectRecipeButton[] selectRecipe = new SelectRecipeButton[18];
	
	private String[] categorys;
	
	private Map<String,List<String>> recipes;
	
	public RecipeSelection(BlockPos stationPos, Block station, EntityPlayer player) {
		super(new ResourceLocation(DarkRoleplayCore.MODID + ":textures/guis/RecipeSelection.png"),178,160);
		
		this.player = player;
		this.station = station;
		this.stationPos = stationPos;
		recipes = CraftingRegistry.INSTANCE.getRecipesForStation(station);
		
		int buttonID = 0;
		
		if(recipes != null)
			categorys = recipes.keySet().toArray(new String[recipes.keySet().size()]);
		
		for(int j = 0; j < 3; j ++){
			for(int i = 0; i < 6; i++){
				selectRecipe[(j * 6) + i] = new SelectRecipeButton(buttonID++, this.guiLeft + 12 + (i * 26), this.guiTop + 64 + (j * 26));
				buttonList.add(selectRecipe[(j * 6) + i]);
			}
		}
			for(int i = 0; i < 18; i++){
				if(recipes.get(categorys[0]) != null && CraftingRegistry.INSTANCE.getRecipe(recipes.get(categorys[0]).get(i)) != null)
					selectRecipe[i].setDisplayItem(CraftingRegistry.INSTANCE.getRecipe(recipes.get(categorys[0]).get(i)).getMainOutput()[0]);
			}
	}

	@Override
    protected void drawBackground(int mouseX, int mouseY, float partialTicks){
    	super.drawBackground(mouseX, mouseY, partialTicks);
    	
    	if(this.station != null) {
			//Render Station and Station Name
			if(this.station == Blocks.AIR){
				//Free Crafting
				this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Blocks.CRAFTING_TABLE), this.guiLeft + 5, this.guiTop + 5);
				this.fontRendererObj.drawString(this.fontRendererObj.trimStringToWidth(I18n.translateToLocal(DPRCoreLocalization.freeCraftingStation), 145), this.guiLeft + 25, this.guiTop + 9, 16777215, true);
			}else{
				this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(station), this.guiLeft + 5, this.guiTop + 5);
				//Stationed Crafting
				this.fontRendererObj.drawString(this.fontRendererObj.trimStringToWidth(station.getLocalizedName(), 145), this.guiLeft + 25, this.guiTop + 9, 16777215, true);
			}
		}
    	
    }
    
    @Override
	protected void drawMiddleground(int mouseX, int mouseY, float partialTicks){
		super.drawMiddleground(mouseX, mouseY, partialTicks);
		
		//Recipes
		//Recipes
		for(int a = 0; a < 3; a++ ) {
			for(int b = 0; b < 6; b++ ) {
				if(selectRecipe[a*6+b] != null)
					if(selectRecipe[a*6+b].getDisplayItem() != null)
						this.itemRender.renderItemAndEffectIntoGUI(selectRecipe[a*6+b].getDisplayItem(), this.guiLeft + 16 + (b * 26), this.guiTop + 68 + (a * 26));
			}
		}
		
    }
	
	@Override
	protected void drawForeground(int mouseX, int mouseY, float partialTicks) {
		// TODO Auto-generated method stub
		
	}

}
