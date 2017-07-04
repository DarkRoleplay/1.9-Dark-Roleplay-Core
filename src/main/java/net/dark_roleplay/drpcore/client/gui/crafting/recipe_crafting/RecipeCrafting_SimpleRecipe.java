package net.dark_roleplay.drpcore.client.gui.crafting.recipe_crafting;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;

import net.dark_roleplay.drpcore.api.gui.DRPGuiScreen;
import net.dark_roleplay.drpcore.api.gui.ITimedGui;
import net.dark_roleplay.drpcore.client.ClientProxy;
import net.dark_roleplay.drpcore.client.gui.crafting.recipe_selection.Button_CategorySelect;
import net.dark_roleplay.drpcore.client.gui.crafting.recipe_selection.Button_ChangeCategory;
import net.dark_roleplay.drpcore.client.gui.crafting.recipe_selection.Button_ChangePage;
import net.dark_roleplay.drpcore.client.keybindings.DRPCoreKeybindings;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.dark_roleplay.drpcore.common.DarkRoleplayCore;
import net.dark_roleplay.drpcore.common.config.SyncedConfigRegistry;
import net.dark_roleplay.drpcore.common.crafting.CraftingRegistry;
import net.dark_roleplay.drpcore.common.crafting.simple_recipe.SimpleRecipe;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.common.handler.DRPCoreGuis;
import net.dark_roleplay.drpcore.common.handler.DRPCorePackets;
import net.dark_roleplay.drpcore.common.network.packets.crafting.Packet_InitSimpleRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class RecipeCrafting_SimpleRecipe extends DRPGuiScreen implements ITimedGui {

	private static ResourceLocation bg = new ResourceLocation(DRPCoreInfo.MODID,
			"textures/guis/recipe_crafting_simple.png");

	public InventoryPlayer inventory;

	private static SimpleRecipe recipe;

	private int currentIngredientOffset = 0;

	private int multiplier = 1;

	private Button_Craft craft;
	private GuiButton retBack;

	private Button_ScrollIngredients ingScrollLeft;
	private Button_ScrollIngredients ingScrollRight;

	private Button_IncAmount incMultiplier;
	private Button_IncAmount decMultiplier;
	
	private BlockPos pos;

	private GhostSlot[] outputSlots = new GhostSlot[7];

	private GhostSlot[] ingredientSlots = new GhostSlot[9];

	private GhostSlot[] inventorySlots = new GhostSlot[36];

	private ItemStack[] outputs;
	private ItemStack[] inputs;
	
	private boolean enableMultiplier;
	
	public RecipeCrafting_SimpleRecipe(BlockPos pos) {
		super(bg, 178, 161);
		this.pos = pos;
	}

	@Override
	public void initGui() {
		super.initGui();
		short buttonID = 0;

		this.enableMultiplier = SyncedConfigRegistry.getBooleanValue("enable_craft_multiplicator");
		
		this.craft = new Button_Craft(buttonID++, this.guiLeft + 150, this.guiTop + 9);
		this.buttonList.add(this.craft);
		
		this.ingScrollLeft = new Button_ScrollIngredients(buttonID++, this.guiLeft + 8, this.guiTop + 60, false);
		this.buttonList.add(this.ingScrollLeft);
		this.ingScrollRight = new Button_ScrollIngredients(buttonID++, this.guiLeft + 163, this.guiTop + 60, true);
		this.buttonList.add(this.ingScrollRight);

		this.incMultiplier = new Button_IncAmount(buttonID++, this.guiLeft + 8, this.guiTop + 31, false);
		this.buttonList.add(this.incMultiplier);
		this.decMultiplier = new Button_IncAmount(buttonID++, this.guiLeft + 163, this.guiTop + 31, true);
		this.buttonList.add(this.decMultiplier);
		
		
		if(!this.enableMultiplier){
			this.incMultiplier.enabled = false;
			this.decMultiplier.enabled = false;
		}
		
		this.retBack = new GuiButton(buttonID++, this.guiLeft - 52, this.guiTop, 50, 12, "Return");
		this.buttonList.add(this.retBack);
		
		outputs = recipe.getMainOutput();
		inputs = recipe.getMainIngredients();
		NonNullList<ItemStack> inv = Minecraft.getMinecraft().player.inventory.mainInventory;

		outputSlots[0] = new GhostSlot(outputs[0], this.guiLeft + 10, this.guiTop + 10, 16, 16);
		for (int i = 1; i < 7; i++) {
			outputSlots[i] = new GhostSlot(outputs.length >= i + 1 ? outputs[i] : null, this.guiLeft + 30,
					this.guiTop + 10, 16, 16);
		}

		for (int i = 0; i < 9; i++) {
			ingredientSlots[i] = new GhostSlot(inputs.length >= i + 1 ? inputs[i] : null, this.guiLeft + 9 + (i * 18),
					this.guiTop + 41, 16, 16);
		}

		for (int i = 0; i < 9; i++) {
			inventorySlots[i] = new GhostSlot(inv.size() >= i + 1 ? inv.get(i) : null, this.guiLeft + 9 + (i * 18),
					this.guiTop + 140, 16, 16);
		}

		for (int i = 9; i < 36; i++) {
			inventorySlots[i] = new GhostSlot(inv.size() >= i + 1 ? inv.get(i) : null,
					this.guiLeft + 9 + ((i % 9) * 18), this.guiTop + 83 + (((i - 9) / 9) * 18), 16, 16);
		}
	}

	@Override
	protected void drawForeground(int mouseX, int mouseY, float partialTicks) {
		inventory = Minecraft.getMinecraft().player.inventory;
		
		FontRenderer font = Minecraft.getMinecraft().fontRenderer;
		
//		this.outputs = recipe.getMainOutput().clone();
//		this.inputs = recipe.getMainIngredients().clone();
		font.drawString("x" + this.multiplier, this.guiLeft + 80, this.guiTop + 31, 16777215);
		
		/** -------- Output --------- **/
		// Update Output Multiplier
		for (int i = 0; i < 7; i++) {
			ItemStack out = i >= outputs.length ? null : outputs[i].copy();
			if (out != null)
				out.grow(out.getCount() * (this.multiplier - 1));
			outputSlots[i].setStack(out);
		}

		for (GhostSlot slot : outputSlots) {
			if (slot.getStack() != null) {
				this.itemRender.renderItemIntoGUI(slot.getStack(), slot.getPosX(), slot.getPosY());
				this.itemRender.renderItemOverlayIntoGUI(this.fontRenderer, slot.getStack(), slot.getPosX(),
						slot.getPosY(),
						slot.getStack().getCount() == 1 ? null : String.valueOf(slot.getStack().getCount()));
			}
		}

		/** -------- Input --------- **/
		// Update Ingredient Offset
		for (int i = 0; i < 9; i++) {
			if (i + currentIngredientOffset < inputs.length) {
				ItemStack ing = i + currentIngredientOffset >= inputs.length ? null : inputs[i + currentIngredientOffset].copy();
				if (ing != null)
					ing.grow(ing.getCount() * (this.multiplier - 1));
				ingredientSlots[i].setStack(ing);
			}
		}

		for (GhostSlot slot : ingredientSlots) {
			if (slot.getStack() != null) {
				this.itemRender.renderItemIntoGUI(slot.getStack(), slot.getPosX(), slot.getPosY());
				this.itemRender.renderItemOverlayIntoGUI(this.fontRenderer, slot.getStack(), slot.getPosX(),
						slot.getPosY(),
						slot.getStack().getCount() == 1 ? null : String.valueOf(slot.getStack().getCount()));
			}
		}

		/** -------- Inventory --------- **/
		// Update Inventory
		for (int i = 0; i < 36; i++) {
			inventorySlots[i].setStack(inventory.mainInventory.get(i));
		}

		for (GhostSlot slot : inventorySlots) {
			if (slot.getStack() != null && !slot.getStack().isEmpty()) {
				this.itemRender.renderItemIntoGUI(slot.getStack(), slot.getPosX(), slot.getPosY());
				this.itemRender.renderItemOverlayIntoGUI(this.fontRenderer, slot.getStack(), slot.getPosX(),
						slot.getPosY(),
						slot.getStack().getCount() == 1 ? null : String.valueOf(slot.getStack().getCount()));
			}
		}

		/** -------- Hover Info --------- **/
		for (GhostSlot slot : outputSlots) {
			if (slot.isMouseOver(mouseX, mouseY) && slot.getStack() != null && !slot.getStack().isEmpty()) {
				this.renderToolTip(slot.getStack(), mouseX, mouseY);
			}
		}

		for (GhostSlot slot : ingredientSlots) {
			if (slot.isMouseOver(mouseX, mouseY) && slot.getStack() != null && !slot.getStack().isEmpty()) {
				this.renderToolTip(slot.getStack(), mouseX, mouseY);
			}
		}

		for (GhostSlot slot : inventorySlots) {
			if (slot.isMouseOver(mouseX, mouseY) && slot.getStack() != null && !slot.getStack().isEmpty()) {
				this.renderToolTip(slot.getStack(), mouseX, mouseY);
			}
		}

		if (craft.isMouseOver()) {
			this.drawHoveringText(new ArrayList<String>() {
				{
					add("Click to Craft");
					if(multiplier == 1)
					add("and Hold to craft mutliple");
				}
			}, mouseX, mouseY, (font == null ? fontRenderer : font));
		}
		
		if(!this.enableMultiplier && (incMultiplier.isMouseOver() || decMultiplier.isMouseOver())){
			this.drawHoveringText(new ArrayList<String>() {
				{
					add("This Feature is disabled!");
					add("You can enable it in the configs.");
					add("(Needs to be enabled on servers too)");
				}
			}, mouseX, mouseY, (font == null ? fontRenderer : font));
		}

	}

	public static void setRecipe(SimpleRecipe recipe) {
		RecipeCrafting_SimpleRecipe.recipe = recipe;
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {

		if (button.id == this.craft.id) {
			DRPCorePackets.sendToServer(new Packet_InitSimpleRecipe(this.recipe.getRegistryName(), this.multiplier));
			if(this.multiplier == 1)
				this.craftButtonHold = 1;
		} else if (button.id == this.ingScrollLeft.id) {
			if (this.currentIngredientOffset - 1 >= 0)
				this.currentIngredientOffset--;
		} else if (button.id == this.ingScrollRight.id) {
			if (this.currentIngredientOffset + 9 < this.recipe.getMainIngredients().length)
				this.currentIngredientOffset++;
		} else if (button.id == this.incMultiplier.id) {
			if(this.multiplier - 1 > 0)
				this.multiplier--;
		} else if (button.id == this.decMultiplier.id) {
			if(this.multiplier + 1 <= 10)
				this.multiplier++;
		}else if (button.id == this.retBack.id) {
			ClientProxy.useRecipeData = true;
			Minecraft.getMinecraft().player.openGui(DarkRoleplayCore.instance,
					DRPCoreGuis.DRPCORE_GUI_CRAFTING_RECIPESELECTION, Minecraft.getMinecraft().world, this.pos.getX(),
					this.pos.getY(), this.pos.getZ());
		}
	}

	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if (keyCode == 1 || DRPCoreKeybindings.openCrafting.isActiveAndMatches(keyCode)
				|| this.mc.gameSettings.keyBindInventory.isActiveAndMatches(keyCode)) {
			this.mc.player.closeScreen();
		}
	}

	private int craftButtonHold = 0;
	private int ticksTillCraft = 0;

	@Override
	public void tick() {
		if (craftButtonHold > 0 && craft.isMouseOver()) {
			this.craftButtonHold++;
			if (craftButtonHold >= 20) {
				ticksTillCraft--;
				if (ticksTillCraft <= 0) {
					ticksTillCraft = 3;
					DRPCorePackets.sendToServer(new Packet_InitSimpleRecipe(this.recipe.getRegistryName(), this.multiplier));
				}
			}
		} else if (this.craftButtonHold > 0) {
			this.craftButtonHold = 0;
		}
	}

	@Override
	protected void mouseReleased(int mouseX, int mouseY, int state) {
		if (this.selectedButton != null && state == 0) {
			this.selectedButton.mouseReleased(mouseX, mouseY);
			this.selectedButton = null;
		}
		this.craftButtonHold = 0;
	}

	private void refreshSlots() {
		ItemStack[] outputs = recipe.getMainOutput();
		ItemStack[] inputs = recipe.getMainIngredients();
		NonNullList<ItemStack> inv = Minecraft.getMinecraft().player.inventory.mainInventory;

		for (int i = 1; i < 7; i++) {
			outputSlots[i] = new GhostSlot(outputs.length >= i + 1 ? outputs[i] : null, this.guiLeft + 30,
					this.guiTop + 10, 16, 16);
		}

		for (int i = 0; i < 9; i++) {
			ingredientSlots[i] = new GhostSlot(inputs.length >= i + 1 ? inputs[i] : null, this.guiLeft + 9 + (i * 18),
					this.guiTop + 41, 16, 16);
		}

		for (int i = 0; i < 9; i++) {
			inventorySlots[i] = new GhostSlot(inputs.length >= i + 1 ? inputs[i] : null, this.guiLeft + 9 + (i * 18),
					this.guiTop + 41, 16, 16);
		}
	}
}
