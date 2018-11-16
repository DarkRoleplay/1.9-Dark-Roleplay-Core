package net.dark_roleplay.core.testing.expanded_inventory;

import java.io.IOException;

import net.dark_roleplay.core.References;
import net.dark_roleplay.core.api.old.crafting.Crafting_Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;

public class ExpandedInventoryGui extends GuiContainer{

    private static final ResourceLocation character = new ResourceLocation(References.MODID, "textures/guis/extended_inventory/character.png");
    private static final ResourceLocation inventory = new ResourceLocation(References.MODID, "textures/guis/extended_inventory/inventory.png");
    private static final ResourceLocation purse = new ResourceLocation(References.MODID, "textures/guis/extended_inventory/purse_base.png");

    private GuiButton openCrafting = null;

	public ExpandedInventoryGui(Container inventorySlots) {
		super(inventorySlots);
		this.guiLeft = 0;
		this.guiTop = 0;
//		this.setGuiSize(this.width, this.height);
	}


	@Override
	public void initGui(){
        this.xSize = 176;
        this.ySize = this.height;
        super.initGui();
		this.openCrafting = new GuiButton(0, this.guiLeft - 20, this.guiTop + 0, 18, 18, "C");
		this.buttonList.add(this.openCrafting);
	}


    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks){
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
    	int posX = (this.width - this.xSize)/2;
    	int posY = (this.height - this.ySize)/2;

//		ModularGui_Drawer.drawBackground(posX, posY, this.xSize, this.ySize);

        this.mc.getTextureManager().bindTexture(character);
    	drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop + 5, 0, 0, 86, 104, 86, 104, 104, 104);

    	this.mc.getTextureManager().bindTexture(inventory);
    	drawScaledCustomSizeModalRect(this.guiLeft, this.guiTop + 110, 0, 0, 176, 97, 176, 97, 176, 97);

    	this.mc.getTextureManager().bindTexture(purse);
    	drawScaledCustomSizeModalRect(this.guiLeft + 87, this.guiTop + 5, 0, 0, 68, 75, 68, 75, 68, 75);

//        for(Slot slot : this.inventorySlots.inventorySlots){
//        	this.drawTexturedModalRect((posX + slot.xPos) - 1, (posY + slot.yPos) - 1, 0, 238, 18, 18);
//        }
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		if(button == this.openCrafting) {
			Crafting_Util.openRecipeSelection(Blocks.AIR, Minecraft.getMinecraft().player.getPosition().add(0, -1, 0), 0f, -90f);
		}
    }
}
