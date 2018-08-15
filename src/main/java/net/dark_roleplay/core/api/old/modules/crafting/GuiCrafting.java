package net.dark_roleplay.core.api.old.modules.crafting;

import net.dark_roleplay.core.References;
import net.dark_roleplay.core.api.old.gui.DRPGuiScreen;
import net.dark_roleplay.core.api.old.modules.gui.IntegerWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;

public class GuiCrafting extends DRPGuiScreen{

	IntegerWrapper offsetTest = new IntegerWrapper(0, 0, 98);
	
	protected IRecipe selectedRecipe;
	
	public static final ResourceLocation BG = new ResourceLocation(References.MODID, "textures/guis/modular_gui/crafting_gui.png");

	public GuiCrafting(ResourceLocation bgTexture, int bgWidth, int bgHeight, int bgOffsetX, int bgOffsetY) {}
	
	private Panel_Craft panelCraft = null;
	
	private int recipeAreaWidth;
	
	@Override
	public void initGui(){
		this.elements.clear();
//		this.elements.add(panelCraft = new Panel_Craft(this, this.width/2 + 1, 50, this.width / 2 - 11, this.height - 60));

		this.recipeAreaWidth = this.width - 186;
		this.elements.add(panelCraft = new Panel_Craft(this, this.width - 174, 10, 164, this.height - 20));
		this.elements.add(new Panel_Recipes(this, 10, 50, recipeAreaWidth, this.height - 60));
		this.elements.add(new Panel_Categories(10, 10, recipeAreaWidth, 38));
	}
	
	@Override
	protected void drawBackground(int mouseX, int mouseY, float partialTicks) {		
		this.drawDefaultBackground();

        Minecraft.getMinecraft().getTextureManager().bindTexture(BG);
        Gui.drawScaledCustomSizeModalRect(4, 4, 0, 0, 6, 6, 6, 6, 64, 64);
        Gui.drawScaledCustomSizeModalRect(4, this.height - 10, 0, 58, 6, 6, 6, 6, 64, 64);
        Gui.drawScaledCustomSizeModalRect(this.width - 10, 4, 58, 0, 6, 6, 6, 6, 64, 64);
        Gui.drawScaledCustomSizeModalRect(this.width - 10, this.height - 10, 58, 58, 6, 6, 6, 6, 64, 64);
		this.drawTiled(4, 10, 6, this.height - 19, 0, 6, 6, 52, 64, 64);
		this.drawTiled(this.width - 10, 10, 6, this.height - 19, 58, 6, 6, 52, 64, 64);
		this.drawTiled(10, 4, this.width - 19, 6, 6, 0, 52, 6, 64, 64);
		this.drawTiled(10, this.height - 10, this.width - 19, 6, 6, 58, 52, 6, 64, 64);
		
		//Horiziontal Spacer
		this.drawTiled(9, 48, recipeAreaWidth + 1, 2, 6, 56, 50, 2, 64, 64);
		//Vertical Spacer
		this.drawTiled(recipeAreaWidth + 10, 9, 2, this.height - 18, 6, 6, 2, 50, 64, 64);
		
	}
	

	@Override
	protected void drawForeground(int mouseX, int mouseY, float partialTicks) {
		
	}

	public IRecipe getSelectedRecipe() {
		return selectedRecipe;
	}

	public void setSelectedRecipe(IRecipe selectedRecipe) {
		this.selectedRecipe = selectedRecipe;
		this.panelCraft.setSelectedRecipe(selectedRecipe);
	}
}
