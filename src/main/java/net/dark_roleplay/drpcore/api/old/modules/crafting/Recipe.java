package net.dark_roleplay.drpcore.api.old.modules.crafting;

import java.awt.Color;
import java.util.List;

import net.dark_roleplay.drpcore.api.old.modules.gui.IGuiElement;
import net.dark_roleplay.drpcore.common.References;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class Recipe extends IGuiElement.IMPL{
	
	private static final ResourceLocation BG = new ResourceLocation(References.MODID, "textures/guis/modular_gui/crafting_gui.png");
	
	private GuiCrafting grandParent;
	private Panel_Recipes parent;
	
	protected ItemStack[] previewStacks;
	private IRecipe recipe;
	
	public Recipe(GuiCrafting grandParent, Panel_Recipes parent, int posX, int posY, IRecipe recipe){
		this.setPos(posX, posY);
		this.setSize(28, 28);
		this.grandParent = grandParent;
		this.recipe = recipe;
		this.parent = parent;
		
		previewStacks = new ItemStack[recipe.getPreviewItems().size()];
		for(int i = 0; i < recipe.getPreviewItems().size(); i++){
			previewStacks[i] = recipe.getPreviewItems().get(i);
		}
	}

	@Override
	public void draw(int mouseX, int mouseY, float partialTicks) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(BG);
		this.drawScaledCustomSizeModalRect(this.posX, this.posY, 8, 6, 28, 28, 28, 28, 64, 64);
		RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
		if(this.isHovered(mouseX, mouseY)){
			this.drawGradientRect(this.posX + 1, this.posY + 1, this.posX + 27, this.posY + 27, 0x66FFFFFF, 0x66FFFFFF);
			this.drawScaledCustomSizeModalRect(this.posX + 16, this.posY + 1, hoversOverStar(mouseX, mouseY) ? 47 : 36, 6, 11, 11, 11, 11, 64, 64);
			itemRenderer.renderItemIntoGUI(previewStacks[parent.currentStack % previewStacks.length], this.posX + 2, this.posY + 10);
		}else{
			itemRenderer.renderItemIntoGUI(previewStacks[parent.currentStack % previewStacks.length], this.posX + 6, this.posY + 6);
		}
		
	}
	
	private boolean hoversOverStar(int mouseX, int mouseY){
		return mouseX >= 16 && mouseY >= 1 & mouseX <= 27 && mouseY <= 12;
	}
	
	@Override
	public boolean mouseClicked(int mouseX, int mouseY, int mouseButton){
		if(hoversOverStar(mouseX, mouseY)){
			System.out.println("Favorize");
		}else{
			grandParent.setSelectedRecipe(this.recipe);
		}
		return true;
	}

}
