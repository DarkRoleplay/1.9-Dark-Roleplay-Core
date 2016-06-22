package net.drpcore.client.gui.guis;

import org.lwjgl.opengl.GL11;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.gui.container.Container_Purse;
import net.drpcore.common.gui.inventories.PurseInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;


public class PurseGui extends GuiContainer {

	private float xSize_lo;

	private float ySize_lo;

	private static final ResourceLocation iconLocation = new ResourceLocation(DarkRoleplayCore.MODID, "textures/guis/Purse.png");

	private final PurseInventory inventory;

	public PurseGui(Container_Purse containerItem) {
		super(containerItem);
		this.inventory = containerItem.inventory;
	}

	/**
	 * Draws the screen and all the components in it.
	 */
	public void drawScreen(int par1, int par2, float par3) {

		super.drawScreen(par1, par2, par3);
		this.xSize_lo = (float) par1;
		this.ySize_lo = (float) par2;
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2) {}

	/**
	 * Draw the background layer for the GuiContainer (everything behind the
	 * items)
	 */
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(iconLocation);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		int x = 9 * (this.inventory.INV_SIZE - 1);
		for(int i = 0; i < this.inventory.INV_SIZE; i++ ) {
			this.drawTexturedModalRect(k + 79 - x + 18 * i, l + 7, 176, 0, 18, 18);
		}
	}
}
