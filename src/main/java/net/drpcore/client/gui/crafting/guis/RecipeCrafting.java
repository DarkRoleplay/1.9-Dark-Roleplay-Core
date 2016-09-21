package net.drpcore.client.gui.crafting.guis;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.drpcore.client.gui.ITimedGui;
import net.drpcore.client.gui.crafting.buttons.Back;
import net.drpcore.client.gui.crafting.buttons.InfoButton;
import net.drpcore.client.gui.crafting.buttons.PrevNext;
import net.drpcore.client.gui.crafting.buttons.StartCraft;
import net.drpcore.client.gui.crafting.buttons.changeIngredientType;
import net.drpcore.client.gui.crafting.buttons.incDecAmount;
import net.drpcore.client.gui.crafting.buttons.ingredientInfo;
import net.drpcore.client.gui.crafting.buttons.selectRecipe;
import net.drpcore.common.DarkRoleplayCore;
import net.drpcore.common.crafting.AdvancedRecipe;
import net.drpcore.common.crafting.CraftingController;
import net.drpcore.common.gui.GuiHandler;
import net.drpcore.common.network.PacketHandler;
import net.drpcore.common.network.packets.PacketCraft;
import net.drpcore.common.network.packets.guis.PacketOpenPurse;
import net.drpcore.common.util.crafting.CraftingManager;
import net.drpcore.common.util.localization.DPRCoreLocalization;
import net.minecraft.block.Block;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;

public class RecipeCrafting extends GuiContainer implements ITimedGui{
	
	public static final ResourceLocation BACKGROUND = new ResourceLocation(DarkRoleplayCore.MODID + ":textures/guis/RecipeCrafting.png");

	CraftingController cc = CraftingController.INSTANCE;

	//Info for back Button
	private BlockPos stationPos;
	
	//Recipe Info
	private Block craftingStation = null;
	
	private String categoryName = "";
	
	private AdvancedRecipe recipe = null;
	
	private int xSize = 178;

	private int ySize = 179;
	
	private EntityPlayer player;
	
	private ItemStack[] primaryList;
	
	private ItemStack[] secondaryList;
	
	private boolean[] useSecondary;
	
	private short tabPage = 1;
	
	private short craftAmount = 1;
	
	//SelectedTab
	private boolean isPrimaryTab = true;
	
	private changeIngredientType primaryType;
	private changeIngredientType secondaryType;
	
	private ingredientInfo[] ingredients = new ingredientInfo[14];
	private ingredientInfo output; 
	
	private byte ticks = 0;
	private int seconds = 0;
	
	private PrevNext prevTabPage;
	private PrevNext nextTabPage;
	
	private incDecAmount decOne;
	private incDecAmount decTwo;
	private incDecAmount decThree;
	
	private incDecAmount incOne;
	private incDecAmount incTwo;
	private incDecAmount incThree;

	private InfoButton info;
	
	private StartCraft start;
	
	private boolean isDone = false;
	private int remainingSeconds = 0;
	private boolean isTimerRunning = false;
	
	private short unmetConditions = 0;
	
	public RecipeCrafting(Container container, Block craftingStation, String category, AdvancedRecipe recipe, EntityPlayer player) {
		super(container);
		int buttonID = 0;
			
		this.craftingStation = craftingStation;
		this.categoryName = category;
		this.recipe = recipe;
		this.player = player;
		this.primaryList = recipe.getPrimaryIngredients();
		this.secondaryList = recipe.getSecondaryIngredients();
		if(secondaryList != null){
			useSecondary = new boolean[secondaryList.length];
			for(boolean bool : useSecondary){
				bool = false;
			}
		}
	}
	
	@Override
	public void initGui() {

		super.initGui();
		int buttonID = 0;
		int posX = (this.width - this.xSize) / 2;
		int posY = (this.height - this.ySize) / 2;
		

		this.primaryType = new changeIngredientType(buttonID ++ , posX + 7, posY + 24, true);
		this.secondaryType = new changeIngredientType(buttonID ++, posX + 7, posY + 42, false);

		buttonList.add(primaryType); //0
		buttonList.add(secondaryType); //1
		
		primaryType.enabled = false;
		if(secondaryList == null)
			secondaryType.enabled = false;
		
		for(int j = 0; j < 2; j ++){
			for(int i = 0; i < 7; i++){
				ingredients[(j * 7) + i] = new ingredientInfo(buttonID++, posX + 27 + (i * 18), posY + 25 + (j * 18),16,16);
				buttonList.add(ingredients[(j * 7) + i]); // 2 - 15
			}
		}
		
		output = new ingredientInfo(buttonID++,posX + 37,posY + 64,24,24);
		buttonList.add(output); //16
		
		this.decOne = new incDecAmount(buttonID++, posX + 26, posY + 63, false, (byte) 0);
		this.decTwo = new incDecAmount(buttonID++, posX + 26, posY + 71, false, (byte) 1);
		this.decThree = new incDecAmount(buttonID++, posX + 26, posY + 79, false, (byte) 2);
		this.incOne = new incDecAmount(buttonID++, posX + 63, posY + 63, true, (byte) 0);
		this.incTwo = new incDecAmount(buttonID++, posX + 63, posY + 71, true, (byte) 1);
		this.incThree = new incDecAmount(buttonID++, posX + 63, posY + 79, true, (byte) 2);
		
		buttonList.add(decOne);//17
		buttonList.add(decTwo);//18
		buttonList.add(decThree);//19
		buttonList.add(incOne);//20
		buttonList.add(incTwo);//21
		buttonList.add(incThree);//22
		
		this.prevTabPage = new PrevNext(buttonID++ , posX + 156, posY + 51, false, 8, 11, 196, 0, new ResourceLocation(DarkRoleplayCore.MODID + ":textures/guis/RecipeCrafting.png")); //23
		this.nextTabPage = new PrevNext(buttonID++ , posX + 165, posY + 51, true, 8, 11, 196, 0, new ResourceLocation(DarkRoleplayCore.MODID + ":textures/guis/RecipeCrafting.png"));//24
		
		buttonList.add(prevTabPage);//23
		buttonList.add(nextTabPage);//24
		
		this.start = new StartCraft(buttonID ++, posX + 130, posY + 66);

		buttonList.add(start);//25
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {

		mc.renderEngine.bindTexture(BACKGROUND);
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
		GL11.glDisable(GL11.GL_LIGHTING);
		RenderHelper.enableGUIStandardItemLighting();
		
		//Crafting Station
		if(craftingStation != null) {
			//Render Station and Station Name
			if(craftingStation == Blocks.AIR){
				//Free Crafting
				this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(Blocks.CRAFTING_TABLE), k + 27, l + 5);
				this.fontRendererObj.drawString(this.fontRendererObj.trimStringToWidth(I18n.translateToLocal(DPRCoreLocalization.freeCraftingStation), 145), k + 45, l + 9, 16777215, true);
			}else{
				this.itemRender.renderItemAndEffectIntoGUI(new ItemStack(craftingStation), k + 27, l + 5);
				//Stationed Crafting
				this.fontRendererObj.drawString(this.fontRendererObj.trimStringToWidth(craftingStation.getLocalizedName(), 145), k + 45, l + 9, 16777215, true);
			}
		}
		
		drawPrimary();
		drawSecondary();
		drawOutput();
		drawHovering(mouseX,mouseY);
		
		if(this.isPointInRegion2(k + 153, l + 63, 9, 9, mouseX, mouseY)){
			this.drawHoveringText(new ArrayList<String>(){{add(recipe.getUnmetConditionText(unmetConditions));}}, mouseX, mouseY, fontRendererObj);
		}
		
		if(recipe.craftingTime != 0){
			this.fontRendererObj.drawString(this.fontRendererObj.trimStringToWidth("Takes:", 53), k + 73, l + 63, 16777215, true);
			this.fontRendererObj.drawString(this.fontRendererObj.trimStringToWidth(String.valueOf(recipe.craftingTime * craftAmount) + "s", 53), k + 73, l + 72, 16777215, true);
		}
		mc.renderEngine.bindTexture(BACKGROUND);

		if(this.isTimerRunning){
			float percent = (recipe.craftingTime * craftAmount) / 20;
			int remainSecInv = recipe.craftingTime * craftAmount - this.remainingSeconds;
			int step = (int) (((float) remainSecInv) / percent);
			drawTexturedModalRect(k + 139, l + 64, (int) (1  + (step < 5 ? step : 4) * 13), 204, 12, 12);
			drawTexturedModalRect(k + 139, l + 75, (int) (1  + (step - 5 < 5  ? (step - 5 < 0 ? 6 : step - 5) : 4) * 13), 217, 12, 12);
			drawTexturedModalRect(k + 128, l + 75, (int) (1  + (step - 10 < 5 ? (step - 10 < 0 ? 6 : step - 10): 4) * 13), 243, 12, 12);
			drawTexturedModalRect(k + 128, l + 64, (int) (1  + (step - 15 < 5 ? (step - 15 < 0 ? 6 : step - 15) : 4) * 13), 230, 12, 12);
		}//73 63
		
		
		
		//Debuging
		this.fontRendererObj.drawString(String.valueOf(this.ticks), 50, 50, 16777215);
		this.fontRendererObj.drawString(String.valueOf(this.seconds), 50, 70, 16777215);
					
	}

	private void drawHovering(int mouseX, int mouseY){
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		mc.renderEngine.bindTexture(BACKGROUND);
		
		ArrayList<ItemStack> usedSecondary = new ArrayList<ItemStack>();
		
		if(useSecondary != null)
		for(int i = this.useSecondary.length; i > 0; i --){
			if(this.useSecondary[i-1])
				usedSecondary.add(this.secondaryList[i-1]);
		}
		
		ItemStack[] usedSecondary2 = new ItemStack[usedSecondary.size()];
		
		this.unmetConditions = this.recipe.doConditionsMet(primaryList, usedSecondary.toArray(usedSecondary2),craftAmount, player, null);
		
		switch(unmetConditions){
			case 0:
				drawTexturedModalRect(k + 153, l + 63,178,63,9,9);
				break;
			case 1:
				drawTexturedModalRect(k + 153, l + 63,178,81,9,9);
				break;
			default:
				drawTexturedModalRect(k + 153, l + 63,178,72,9,9);
				break;
		}

		//Hovering Text and Stuff
		for (int i1 = 0; i1 < 14; ++i1){
			if(this.isPrimaryTab){
				if (this.isMouseOverButton(ingredients[i1], mouseX, mouseY)){
					if(primaryList.length > (i1 + (18 * (tabPage - 1)))){
						renderToolTip(primaryList[(i1) + (18 * (tabPage - 1))] ,mouseX,mouseY);
					}
				}
			}else{
				if (this.isMouseOverButton(ingredients[i1], mouseX, mouseY)){

					if(secondaryList.length > (i1 + (18 * (tabPage - 1)))){
						renderToolTip(secondaryList[(i1) + (18 * (tabPage - 1))] ,mouseX,mouseY);
					}
				}
			}	
		}
		if (this.isMouseOverButton(output, mouseX, mouseY)){
			renderToolTip(recipe.getDefaultOutput() ,mouseX,mouseY);
		}
    }
	
	private void drawPrimary(){
		if(this.isPrimaryTab){
			int k = (this.width - this.xSize) / 2;
			int l = (this.height - this.ySize) / 2;
			
			for(int a = 0; a < 2; a++ ) {
				for(int b = 0; b < 7; b++ ) {
					if(((tabPage - 1) * 14) + (a * 5) + b  < primaryList.length){
						if(primaryList[((tabPage - 1) * 14) + (a * 5) + b ] != null){
							ItemStack previewItem = primaryList[((tabPage - 1) * 14) + (a * 7) + b ].copy();
							previewItem.stackSize = previewItem.stackSize * craftAmount;
							drawItemStack(previewItem, k + 27 + (18 * b), l + 25 + (a * 18),  hasItemStack(previewItem, false),true);
						}
					}
				}
			}
		}
	}
	
	private void drawSecondary(){
		if(secondaryList != null){
			if(!this.isPrimaryTab){
				int k = (this.width - this.xSize) / 2;
				int l = (this.height - this.ySize) / 2;
				
				for(int a = 0; a < 2; a++ ) {
					for(int b = 0; b < 7; b++ ) {
						if(((tabPage - 1) * 18) + (a * 5) + b  < secondaryList.length){
							if(secondaryList[((tabPage - 1) * 18) + (a * 5) + b ] != null){
								ItemStack previewItem = secondaryList[((tabPage - 1) * 14) + (a * 7) + b ].copy();
								previewItem.stackSize = previewItem.stackSize * craftAmount;
								
								drawItemStack(previewItem, k + 27 + (18 * b), l + 25 + (a * 18), hasItemStack(previewItem, false),useSecondary[((tabPage - 1) * 18) + (a * 5) + b]);
							}
						}
					}
				}
			}
		}
	}
	
	private void drawOutput(){
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		ItemStack output = recipe.getDefaultOutput().copy();
		output.stackSize = output.stackSize * craftAmount;
		drawItemStack(output, k +  41, l + 68 , 1,true);
	}
	
	
	private int hasItemStack(ItemStack stack, boolean ignoreMeta) {

		int i;
		InventoryPlayer mainInventory = player.inventory;
		int missingAmount = stack.stackSize;
		for(i = 0; i < mainInventory.getSizeInventory(); ++i) {
			if(mainInventory.getStackInSlot(i) != null && stack != null && mainInventory.getStackInSlot(i).getItem() == stack.getItem()) {
				if(ignoreMeta) {
					if(mainInventory.getStackInSlot(i).stackSize >= stack.stackSize) {
						return 1;
					} else if(mainInventory.getStackInSlot(i).stackSize <= missingAmount) {
						missingAmount -= mainInventory.getStackInSlot(i).stackSize;
					} else {
						missingAmount = 0;
					}
				} else if( ! ignoreMeta && mainInventory.getStackInSlot(i).getMetadata() == stack.getMetadata()) {
					if(mainInventory.getStackInSlot(i).stackSize >= stack.stackSize) {
						return 1;
					} else if(mainInventory.getStackInSlot(i).stackSize <= missingAmount) {
						missingAmount -= mainInventory.getStackInSlot(i).stackSize;
					} else {
						missingAmount = 0;
					}
				}
				if(missingAmount == 0)
					return 1;
			}
		}
		return 0;
	}
	
	private void drawItemStack(ItemStack stack, int x, int y, int hasNumber, boolean isSelected) {

		if(stack != null) {
			RenderHelper.enableGUIStandardItemLighting();
			Integer stackSize = stack.stackSize;
			String stackSizeString = stackSize.toString();
			GlStateManager.translate(0.0F, 0.0F, 32.0F);
			this.zLevel = 200.0F;
			this.itemRender.zLevel = 200.0F;
			FontRenderer font = null;
			font = stack.getItem().getFontRenderer(stack);
			if(font == null)
				font = fontRendererObj;
			this.itemRender.renderItemAndEffectIntoGUI(stack, x, y);
			if(hasNumber == 1)
				this.itemRender.renderItemOverlayIntoGUI(font, stack, x, y, isSelected ? TextFormatting.WHITE + stackSizeString : TextFormatting.YELLOW + stackSizeString);
			else if(hasNumber == 0)
				this.itemRender.renderItemOverlayIntoGUI(font, stack, x, y, TextFormatting.RED + stackSizeString);
			this.zLevel = 0.0F;
			this.itemRender.zLevel = 0.0F;
			RenderHelper.disableStandardItemLighting();
		}
	}
	
	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		switch (button.id) {
			case 0:
				this.isPrimaryTab = true;
				primaryType.enabled = false;
				primaryType.toggle();
				secondaryType.enabled = true;
				secondaryType.toggle();
				tabPage = 1;
				break;
			case 1:
				this.isPrimaryTab = false;
				primaryType.enabled = true;
				primaryType.toggle();
				secondaryType.enabled = false;
				secondaryType.toggle();
				tabPage = 1;
				break;
			case 2:
				
				break;
			case 3:
				
				break;
			case 17:
				this.craftAmount -= 1;
				break;
			case 18:
				this.craftAmount -= 5;
				break;
			case 19:
				this.craftAmount -= 10;
				break;
			case 20:
				this.craftAmount += 1;
				break;
			case 21:
				this.craftAmount += 5;
				break;
			case 22:
				this.craftAmount += 10;
				break;
			case 23:
				if((this.tabPage - 1) >= 1){
					this.tabPage -= 1;
				}else{
					if(this.isPrimaryTab){
						this.tabPage = (short) Math.ceil((double) this.primaryList.length / 14d);
					}else{
						this.tabPage = (short) Math.ceil((double)this.secondaryList.length / 14d);
					}
				}
				break;
			case 24:
				if((this.isPrimaryTab && this.tabPage + 1 <= (short) Math.ceil(this.primaryList.length / 14 ) ||(!this.isPrimaryTab && this.tabPage + 1 <= (short) Math.ceil(this.secondaryList.length / 14)))){
					this.tabPage ++;
				}else{
						this.tabPage = 1;
				}
				break;
			case 25:
				if(recipe.craftingTime != 0 && !isDone && !isTimerRunning){
					this.remainingSeconds = recipe.craftingTime * craftAmount;
					this.isTimerRunning = true;
					start.enabled = false;
					decOne.enabled = false;
					decTwo.enabled = false;
					decThree.enabled = false;
					incOne.enabled = false;
					incTwo.enabled = false;
					incThree.enabled = false;
				}else if(recipe.craftingTime == 0 || (recipe.craftingTime != 0 && isDone)){
					this.isTimerRunning = false;
					this.isDone = false;
					decOne.enabled = true;
					decTwo.enabled = true;
					decThree.enabled = true;
					incOne.enabled = true;
					incTwo.enabled = true;
					incThree.enabled = true;
					
					ItemStack[] primary = new ItemStack[primaryList.length];
					for(int i = 0; i < primaryList.length; i ++){
						primary[i] = primaryList[i];
					}
					ItemStack[] secondary = null;
					if(secondaryList != null){
						secondary = new ItemStack[secondaryList.length];
						for(int i = 0; i < secondaryList.length; i ++){
							if(useSecondary[i]){
								secondary[i] = secondaryList[i];
							}
						}
					}
					
					if(recipe.doConditionsMet(primary, secondary, this.craftAmount, player, stationPos) != 0){
						return;
					}
					
					PacketHandler.sendToServer(new PacketCraft(this.recipe, primary, secondary, stationPos, this.craftAmount));
					
				}
				break;
		}
		if(button.id >= 2 && button.id <= 15){
			if(!this.isPrimaryTab){
				int ingredientID = button.id - 2;
				if(((tabPage - 1) * 16) + ingredientID < useSecondary.length)
					useSecondary[((tabPage - 1) * 16) + ingredientID] = useSecondary[((tabPage - 1) * 16) + ingredientID] ? false : true;
			}
		}
		if(button.id >= 16 && button.id <= 21){
			if(this.craftAmount < 1)
				this.craftAmount = 1;
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {

		return true;
	}

	private boolean isMouseOverButton(ingredientInfo btn, int mouseX, int mouseY)
    {
        return this.isPointInRegion2(btn.xPosition, btn.yPosition, btn.width, btn.height, mouseX, mouseY);
    }
	
	protected boolean isPointInRegion2(int rectX, int rectY, int rectWidth, int rectHeight, int pointX, int pointY)
    {
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
        return pointX >= rectX - 1 && pointX < rectX + rectWidth + 1 && pointY >= rectY - 1 && pointY < rectY + rectHeight + 1;
    }

	@Override
	public void increaseTimer(int time) {
		this.ticks++;
		if(this.ticks >= 20){
			if(this.remainingSeconds > 0 && this.isTimerRunning){
				this.remainingSeconds --;
			}else if(this.remainingSeconds == 0 && this.isTimerRunning){
				this.isDone = true;
				start.enabled = true;
			}
			this.ticks = 0;
		}
	}
}