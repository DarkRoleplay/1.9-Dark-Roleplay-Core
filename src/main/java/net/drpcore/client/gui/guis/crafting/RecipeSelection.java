package net.drpcore.client.gui.guis.crafting;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import net.drpcore.common.DarkRoleplayCore;
import net.minecraft.block.Block;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;


public class RecipeSelection extends GuiScreen {

	public static final ResourceLocation BACKGROUND = new ResourceLocation(DarkRoleplayCore.MODID + ":textures/guis/RecipeSelection.png");

	private int xSize = 178;

	private int ySize = 160;

	private Block station = null;

	private BlockPos stationPos = null;

	private short currentCategory = 0;

	private short currentPage = 1;

	private short maxPages = 5;

	private String categoryName = "Category Placeholder";

	public byte currentTicks = 0;

	public int passedSeconds = 0;

	public RecipeSelection(Block station) {
		this.station = station;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {

		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		mc.renderEngine.bindTexture(BACKGROUND);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
		//GL11.glColor4f(1, 1, 1, 1);
		GL11.glDisable(GL11.GL_LIGHTING);
		//Fix for Item Lighting
		RenderHelper.enableGUIStandardItemLighting();
		if(station != null) {
			//Render Station and Station Name
			this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(station), k + 5, l + 5);
			this.fontRendererObj.drawString(this.fontRendererObj.trimStringToWidth(station.getLocalizedName(), 145), k + 25, l + 9, 16777215, true);
		}
		//Category
		int stringWidth = this.fontRendererObj.getStringWidth(this.fontRendererObj.trimStringToWidth(categoryName, 161));
		this.fontRendererObj.drawString(this.fontRendererObj.trimStringToWidth(categoryName, 161), k + (xSize / 2) - (stringWidth / 2), l + 52, 16777215, true);
		this.fontRendererObj.drawString(String.valueOf(this.passedSeconds), 50, 50, 16777215);
		//Recipes
		for(int a = 0; a < 6; a++ ) {
			for(int b = 0; b < 3; b++ ) {
				this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Blocks.BEACON), k + 16 + (a * 26), l + 68 + (b * 26));
			}
		}
	}

	@Override
	public boolean doesGuiPauseGame() {

		return true;
	}
	//@Override
	//	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
	//}
}
