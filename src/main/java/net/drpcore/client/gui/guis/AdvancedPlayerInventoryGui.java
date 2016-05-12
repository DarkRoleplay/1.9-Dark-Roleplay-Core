package net.drpcore.client.gui.guis;

import java.io.IOException;

import org.lwjgl.opengl.GL11;

import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.capabilities.entities.player.advancedInventory.IPlayerAdvancedInventory;
import net.drpcore.common.gui.buttons.craftCategoryButton;
import net.drpcore.common.gui.buttons.craftIngredientButton;
import net.drpcore.common.gui.buttons.rotateEntityRender;
import net.drpcore.common.gui.container.AdvancedPlayerInventoryContainer;
import net.drpcore.common.gui.inventories.AdvancedPlayerInventory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
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

	private float rotation = 0F;

	rotateEntityRender rotateLeft;
	rotateEntityRender rotateRigth;

	public AdvancedPlayerInventoryGui(EntityPlayer player, InventoryPlayer inventoryPlayer, AdvancedPlayerInventory inventoryCustom) {
		super(new AdvancedPlayerInventoryContainer(player, inventoryPlayer, inventoryCustom));
		this.inventory = inventoryCustom;
		this.player = player;
	}

	@Override
	public void initGui() {

		super.initGui();

		int buttonID = 0;

		int posX = ((this.width - this.xSize + this.tabButtonWidth) / 2);
		int posY = (this.height - this.ySize) / 2;

		rotateLeft = new rotateEntityRender(buttonID++, posX + 106, posY + 64, false); // 63
																						// 65
		rotateRigth = new rotateEntityRender(buttonID++, posX + 62, posY + 64, true); // 107
																						// 65

		buttonList.add(rotateLeft);
		buttonList.add(rotateRigth);
	}

	public void drawScreen(int mouseX, int mouseY, float partialTick) {

		super.drawScreen(mouseX, mouseY, partialTick);
		this.mouseX = (float) mouseX;
		this.mouseY = (float) mouseY;
	}

	protected void drawGuiContainerBackgroundLayer(float partialTick, int mouseX, int mouseY) {

		int posX = ((this.width - this.xSize + this.tabButtonWidth) / 2);
		int posY = (this.height - this.ySize) / 2;

		mc.renderEngine.bindTexture(BACKGROUND);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.drawTexturedModalRect(posX, posY, 0, 0, this.xSize, this.ySize);

		this.drawEntityOnScreen(posX + 86, posY + 70, 30, this.rotation, 0, this.player);
	}

	protected void drawGuiContainerForegroundLayer(int par1, int par2) {}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {

		switch (button.id) {
			// Rotate Left
			case 0:
				this.rotation -= 10;
				if(this.rotation < -180){
					this.rotation += 360;
				}
				break;
			// Rotate Right
			case 1:
				this.rotation += 10;
				if(this.rotation > 180){
					this.rotation -= 360;
				}
				break;
		}
	}

	public static void drawEntityOnScreen(int posX, int posY, int scale, float rotationYaw, float rotationPitch, EntityLivingBase ent) {

		GlStateManager.enableColorMaterial();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) posX, (float) posY, 50.0F);
		GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
		GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);

		float f = ent.renderYawOffset;
		float f1 = ent.rotationYaw;
		float f2 = ent.rotationPitch;
		float f3 = ent.prevRotationYawHead;
		float f4 = ent.rotationYawHead;

		GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F); // Rotation Y
		RenderHelper.enableStandardItemLighting();
		GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F); // Rotation Y
		GlStateManager.rotate(10F, 1.0F, 0.0F, 0.0F);

		ent.renderYawOffset = rotationYaw;
		ent.rotationYaw = rotationYaw;
		ent.rotationPitch = 0F;
		ent.rotationYawHead = ent.rotationYaw;
		ent.prevRotationYawHead = ent.rotationYaw;

		GlStateManager.translate(0.0F, 0.0F, 0.0F);
		RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
		rendermanager.setPlayerViewY(180.0F);
		rendermanager.setRenderShadow(false);
		rendermanager.doRenderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
		rendermanager.setRenderShadow(true);

		ent.renderYawOffset = f;
		ent.rotationYaw = f1;
		ent.rotationPitch = f2;
		ent.prevRotationYawHead = f3;
		ent.rotationYawHead = f4;

		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}
}
