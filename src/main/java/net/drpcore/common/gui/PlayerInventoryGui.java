package net.drpcore.common.gui;

import org.lwjgl.opengl.GL11;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.entities.player.ExtendedPlayer;
import net.drpcore.common.entities.player.capabilites.AdditionalPlayerStorage;
import net.drpcore.common.entities.player.capabilites.IPlayerCapability;
import net.drpcore.common.gui.container.PlayerInventoryContainer;
import net.drpcore.common.gui.inventories.PlayerInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class PlayerInventoryGui extends GuiContainer{

	//TODO Player Inventory Gui
	
	private float xSize_lo;
	private float ySize_lo;

	private int xSize = 216;
	private int ySize = 180;
	
	private static final ResourceLocation BACKGROUND = new ResourceLocation(DarkRoleplayCore.MODID+ ":textures/guis/GuiInventory.png");
	
	private final PlayerInventory inventory;
	
	private final EntityPlayer player;
	
	public PlayerInventoryGui(EntityPlayer player, InventoryPlayer inventoryPlayer, PlayerInventory inventoryCustom){
		super(new PlayerInventoryContainer(player, inventoryPlayer, inventoryCustom));
		this.inventory = inventoryCustom;
		// if you need the player for something later on, store it in a local variable here as well
		this.player = player;
	}
	
	public void drawScreen(int par1, int par2, float par3){
		super.drawScreen(par1, par2, par3);
		this.xSize_lo = (float)par1;
		this.ySize_lo = (float)par2;
	}
	
	protected void drawGuiContainerForegroundLayer(int par1, int par2){}
	
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3){

		IPlayerCapability playerData = player.getCapability(DarkRoleplayCore.getDrpcoreInv(),null);
		
		PlayerInventory customInventory;
		
		if(playerData != null){
			customInventory = playerData.getInventory();
		}else{
			return;
		}
		
		mc.renderEngine.bindTexture(BACKGROUND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		//Helm
		if(player.inventory.getStackInSlot(39) == null){
			this.drawTexturedModalRect(k+65,l+6,217,0,16,16);
		}
		//Chestplate
		if(player.inventory.getStackInSlot(38) == null){
			this.drawTexturedModalRect(k+65,l+24,217,16,16,16);
		}
		//Pants
		if(player.inventory.getStackInSlot(37) == null){
			this.drawTexturedModalRect(k+65,l+42,217,32,16,16);
		}
		//Shoes
		if(player.inventory.getStackInSlot(36) == null){
			this.drawTexturedModalRect(k+65,l+60,217,48,16,16);
		}
		
		//Necklace
		if(customInventory.getStackInSlot(0) == null){
			this.drawTexturedModalRect(k+137, l+6, 233, 0, 16,16);
		}
		//Ring1
		if(customInventory.getStackInSlot(1) == null){
			this.drawTexturedModalRect(k+137, l+24, 233, 16, 16,16);
		}
		//Ring2
		if(customInventory.getStackInSlot(2) == null){
			this.drawTexturedModalRect(k+137, l+42, 233, 16, 16,16);
		}
		//Belt
		if(customInventory.getStackInSlot(3) == null){
			this.drawTexturedModalRect(k+137, l+60, 233, 32, 16,16);
		}
		//Purse
		if(customInventory.getStackInSlot(4) == null){
			this.drawTexturedModalRect(k+137, l+78, 233, 48, 16,16);
			for(int i = 0; i<3; i++){
				this.drawTexturedModalRect((k + 158) + (18 * i) , l+78,217,112,16,16);
			}
		}else{
			for(int i = 0; i<3; i++){
				this.drawTexturedModalRect((k + 158) + (18 * i) , l+78,233,64,16,16);
			}
		}
		//Pouch
		if(customInventory.getStackInSlot(5) == null){
			this.drawTexturedModalRect(k+65, l+78, 217, 64, 16,16);
			for(int a = 0; a < 3; a++){
				for(int b = 0; b < 5; b ++){
					this.drawTexturedModalRect(k+7 + (a * 18), l+6+ (b*18), 217,112, 16, 16);
				}
			}
		}
		//Quiver
		if(customInventory.getStackInSlot(6) == null){
			this.drawTexturedModalRect(k +101,l+78,233,79,16,16);
			this.drawTexturedModalRect(k + 119,l+78,217,112,16,16);
		}else{
			this.drawTexturedModalRect(k + 119,l+78,217,96,16,16);
		}
		
		int i1;
		GuiInventory.drawEntityOnScreen(k + 108, l + 70, 30, (float)(k + 51) - this.xSize_lo, (float)(l + 75 - 50) - this.ySize_lo, this.mc.thePlayer);
	}
}
