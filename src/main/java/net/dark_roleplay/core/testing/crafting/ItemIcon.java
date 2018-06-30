package net.dark_roleplay.core.testing.crafting;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.item.ItemStack;

public class ItemIcon implements IIcon{

	private ItemStack stack;
	
	private RenderItem renderer;
	
	public ItemIcon(ItemStack stack) {
		this(stack, Minecraft.getMinecraft().getRenderItem());
	}
	
	public ItemIcon(ItemStack stack, RenderItem renderer) {
		this.stack = stack;
		this.renderer = renderer;
	}
	
	@Override
	public void draw(int posX, int posY, int width, int height) {
		GlStateManager.pushMatrix();
		GlStateManager.scale(0.0625F * width, 0.0625F * height, 1F);
		renderer.renderItemIntoGUI(stack, posX, posY);
		GlStateManager.popMatrix();
	}
}
