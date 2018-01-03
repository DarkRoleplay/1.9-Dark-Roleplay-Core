package net.dark_roleplay.drpcore.modules.crafting;

import net.dark_roleplay.drpcore.modules.gui.Panel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class Panel_Craft extends Panel{

	private GuiCrafting parent;
	private IRecipe selectedRecipe;
	
    private int posInvX = this.width / 2 - 81;
    private int posInvY = this.height - 74;
    private NonNullList<ItemStack> playerInventory;
    private RenderItem itemRenderer;
    private FontRenderer fontRenderer;
	
	public Panel_Craft(GuiCrafting parent, int posX, int posY, int width, int height) {
		super(posX, posY, width, height);
		this.bgColor = 0x88000000;
		this.parent = parent;
		this.playerInventory = Minecraft.getMinecraft().player.inventory.mainInventory;
		this.itemRenderer = Minecraft.getMinecraft().getRenderItem();
		this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
	}

	public void drawBackground(int mouseX, int mouseY, float partialTick){
		//TODO Draw Recipe Information
		//TODO Draw Player Inventory
		//TODO Craft Button
		//TODO Disable Inventory Slots
        Minecraft.getMinecraft().getTextureManager().bindTexture(parent.BG);
        
        this.drawTiled(posInvX, posInvY, 162, 72, 8, 34, 18, 18, 64, 64);
        this.drawString(this.fontRenderer, "Inventory", posInvX, posInvY - 10, 0xFFFFFFFF);
        for(int i = 0; i < 9; i ++){
        	ItemStack stack = playerInventory.get(i);
        	itemRenderer.renderItemIntoGUI(stack, posInvX + 1 + (18 * i), posInvY + 55);
        	itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, stack, posInvX + 1 + (18 * i), posInvY + 55, null);
        }
        
        for(int i = 0; i < 9; i ++){
        	for(int j = 0; j < 3; j++){
            	ItemStack stack = playerInventory.get(i + (j * 9) + 9);
            	itemRenderer.renderItemIntoGUI(stack, posInvX + 1 + (18 * i), posInvY + 1 + (18 * j));
            	itemRenderer.renderItemOverlayIntoGUI(this.fontRenderer, stack, posInvX + 1 + (18 * i), posInvY + 1 + (18 * j), null);
        	}
        }

	}

	public void setSelectedRecipe(IRecipe selectedRecipe) {
		this.selectedRecipe = selectedRecipe;
	}
}
