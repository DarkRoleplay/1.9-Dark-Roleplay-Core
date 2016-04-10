package net.drpcore.client.guis;

import org.lwjgl.opengl.GL11;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.entities.player.advancedInventoryCapabiliy.AdvancedPlayerStorage;
import net.drpcore.common.entities.player.advancedInventoryCapabiliy.IPlayerInventoryAdvanced;
import net.drpcore.common.gui.container.AdvancedPlayerInventoryContainer;
import net.drpcore.common.gui.inventories.AdvancedPlayerInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class AdvancedPlayerInventoryGui extends GuiContainer {

	// TODO Player Inventory Gui

	private float mouseX;
	private float mouseY;

	private int xSize = 198;
	private int ySize = 162;

	private int tabButtonWidth = 24;

	private static final ResourceLocation BACKGROUND = new ResourceLocation(DarkRoleplayCore.MODID + ":textures/guis/AdvancedInventory.png");

	private final AdvancedPlayerInventory inventory;

	private final EntityPlayer player;

	public AdvancedPlayerInventoryGui(EntityPlayer player, InventoryPlayer inventoryPlayer, AdvancedPlayerInventory inventoryCustom) {
		super(new AdvancedPlayerInventoryContainer(player, inventoryPlayer, inventoryCustom));
		this.inventory = inventoryCustom;
		this.player = player;
	}

	public void drawScreen(int mouseX, int mouseY, float partialTick) {
		super.drawScreen(mouseX, mouseY, partialTick);
		this.mouseX = (float) mouseX;
		this.mouseY = (float) mouseY;
	}

	protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY) {
		int posX = (this.width - this.xSize + this.tabButtonWidth) / 2;
		int posY = (this.height - this.ySize) / 2;
		
		mc.renderEngine.bindTexture(BACKGROUND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.drawTexturedModalRect(posX, posY, 0, 0, this.xSize, this.ySize);
		
		GuiInventory.drawEntityOnScreen(posX + 62, posY + 7, 30, 0, 0, this.player);
		
		/*
		IPlayerInventoryAdvanced playerData = player.getCapability(DarkRoleplayCore.DRPCORE_INV, null);

		AdvancedPlayerInventory customInventory;
		
		if(playerData != null){
			customInventory = playerData.getInventory();
		}
		else{
			return;
		}

		
		/*
		/Helm
		if(player.inventory.getStackInSlot(39) == null){
			this.drawTexturedModalRect(k + 65, l + 6, 217, 0, 16, 16);
		}
		// Chestplate
		if(player.inventory.getStackInSlot(38) == null){
			this.drawTexturedModalRect(k + 65, l + 24, 217, 16, 16, 16);
		}
		// Pants
		if(player.inventory.getStackInSlot(37) == null){
			this.drawTexturedModalRect(k + 65, l + 42, 217, 32, 16, 16);
		}
		// Shoes
		if(player.inventory.getStackInSlot(36) == null){
			this.drawTexturedModalRect(k + 65, l + 60, 217, 48, 16, 16);
		}
		if(customInventory != null){
			// Necklace
			if(customInventory.getStackInSlot(0) == null){
				this.drawTexturedModalRect(k + 137, l + 6, 233, 0, 16, 16);
			}
			// Ring1
			if(customInventory.getStackInSlot(1) == null){
				this.drawTexturedModalRect(k + 137, l + 24, 233, 16, 16, 16);
			}
			// Ring2
			if(customInventory.getStackInSlot(2) == null){
				this.drawTexturedModalRect(k + 137, l + 42, 233, 16, 16, 16);
			}
			// Belt
			if(customInventory.getStackInSlot(3) == null){
				this.drawTexturedModalRect(k + 137, l + 60, 233, 32, 16, 16);
			}
			// Purse
			if(customInventory.getStackInSlot(4) == null){
				this.drawTexturedModalRect(k + 137, l + 78, 233, 48, 16, 16);
				for(int i = 0; i < 3; i++){
					this.drawTexturedModalRect((k + 158) + (18 * i), l + 78, 217, 112, 16, 16);
				}
			}
			else{
				for(int i = 0; i < 3; i++){
					this.drawTexturedModalRect((k + 158) + (18 * i), l + 78, 233, 64, 16, 16);
				}
			}
			// Pouch
			if(customInventory.getStackInSlot(5) == null){
				this.drawTexturedModalRect(k + 65, l + 78, 217, 64, 16, 16);
				for(int a = 0; a < 3; a++){
					for(int b = 0; b < 5; b++){
						this.drawTexturedModalRect(k + 7 + (a * 18), l + 6 + (b * 18), 217, 112, 16, 16);
					}
				}
			}
			// Quiver
			if(customInventory.getStackInSlot(6) == null){
				this.drawTexturedModalRect(k + 101, l + 78, 233, 79, 16, 16);
				this.drawTexturedModalRect(k + 119, l + 78, 217, 112, 16, 16);
			}
			else{
				this.drawTexturedModalRect(k + 119, l + 78, 217, 96, 16, 16);
			}
		}
		int i1;
		GuiInventory.drawEntityOnScreen(k + 108, l + 70, 30, (float) (k + 51) - this.xSize_lo, (float) (l + 75 - 50) - this.ySize_lo, this.mc.thePlayer);
		*/
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2) {}
}
