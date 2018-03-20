package net.dark_roleplay.drpcore.api.old.modules.crafting;

import java.io.IOException;

import org.lwjgl.input.Mouse;

import net.dark_roleplay.drpcore.api.old.modules.gui.HorizontalPanel;
import net.dark_roleplay.drpcore.api.old.modules.gui.HorizontalScrollBar;
import net.dark_roleplay.drpcore.api.old.modules.gui.IntegerWrapper;
import net.dark_roleplay.drpcore.api.old.modules.gui.VerticalPanel;
import net.dark_roleplay.drpcore.api.old.modules.gui.VerticalScrollBar;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;

public class Panel_Recipes extends VerticalPanel{

	private GuiCrafting parent;
	
	public int currentStack = 0;
	private float timeTillSwitch = 70F;
	
	public Panel_Recipes(GuiCrafting parent, int posX, int posY, int width, int height) {
		super(posX, posY, width, height, 0);
		this.bgColor = 0x88000000;
		this.parent = parent;
		
		int recipeAmount = 200;
		
		int maxWidth = (this.width - 4) / 30;
		int row = 0, column = 0;
		for(int i = 0; i < recipeAmount; i++){
			if(column >= maxWidth){
				row++;
				column = 0;
			}
			this.addChild(new Recipe(parent, this, 30 * column + 2, 30 * row + 2, new SimpleRecipe(new ItemStack[]{
					new ItemStack(Blocks.PLANKS,1 ,0), new ItemStack(Blocks.PLANKS, 1, 1),
					new ItemStack(Blocks.PLANKS,1 ,2), new ItemStack(Blocks.PLANKS, 1, 3),
					new ItemStack(Blocks.PLANKS,1 ,4), new ItemStack(Blocks.PLANKS, 1, 5)
					}, null, null)));
			column++;
		}
		
		scrollAmount = new IntegerWrapper(0, 0, MathHelper.clamp((int)(Math.ceil(recipeAmount / (maxWidth + 0F)) * 30) - this.width + 35, 1, Integer.MAX_VALUE));
		scrollBar = new VerticalScrollBar(width - 7, 0, 7, height, scrollAmount);
		this.scrollMultiplier = -(((float) scrollAmount.getMax()) / ((float)height)) * 2;
	}
	
	@Override
	public void draw(int mouseX, int mouseY, float partialTick) {
		super.draw(mouseX, mouseY, partialTick);
		timeTillSwitch -= partialTick;
		if(timeTillSwitch <= 0){
			timeTillSwitch = 70;
			currentStack = 100 > (currentStack + 1) ? currentStack + 1 : 0;
		}
	}

}
