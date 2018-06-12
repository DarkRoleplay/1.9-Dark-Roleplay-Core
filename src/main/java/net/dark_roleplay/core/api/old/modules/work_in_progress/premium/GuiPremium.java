package net.dark_roleplay.core.api.old.modules.work_in_progress.premium;

import net.dark_roleplay.core.api.old.Modules;
import net.dark_roleplay.core.api.old.gui.DRPGuiScreen;
import net.dark_roleplay.core.api.old.modules.crafting.Panel_Categories;
import net.dark_roleplay.core.api.old.modules.crafting.Panel_Craft;
import net.dark_roleplay.core.api.old.modules.crafting.Panel_Recipes;

public class GuiPremium extends DRPGuiScreen{

	@Override
	public void initGui(){
		this.elements.clear();
		this.elements.add(new Panel_Addons((this.width / 2) - 200, (this.height/2) - 50, 200, 100, 0));
//		this.elements.add(panelCraft = new Panel_Craft(this, this.width/2 + 1, 50, this.width / 2 - 11, this.height - 60));

//		this.recipeAreaWidth = this.width - 186;
//		this.elements.add(panelCraft = new Panel_Craft(this, this.width - 174, 10, 164, this.height - 20));
//		this.elements.add(new Panel_Recipes(this, 10, 50, recipeAreaWidth, this.height - 60));
//		this.elements.add(new Panel_Categories(10, 10, recipeAreaWidth, 38));
	}
	
	@Override
	protected void drawForeground(int mouseX, int mouseY, float partialTicks) {
		// TODO Auto-generated method stub
	}

}
