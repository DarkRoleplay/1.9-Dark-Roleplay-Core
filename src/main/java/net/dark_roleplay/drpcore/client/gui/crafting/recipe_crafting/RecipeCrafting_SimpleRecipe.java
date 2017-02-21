package net.dark_roleplay.drpcore.client.gui.crafting.recipe_crafting;

import java.io.IOException;

import net.dark_roleplay.drpcore.api.gui.DRPGuiScreen;
import net.dark_roleplay.drpcore.client.ClientProxy;
import net.dark_roleplay.drpcore.client.gui.crafting.recipe_selection.Button_CategorySelect;
import net.dark_roleplay.drpcore.client.gui.crafting.recipe_selection.Button_ChangeCategory;
import net.dark_roleplay.drpcore.client.gui.crafting.recipe_selection.Button_ChangePage;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.dark_roleplay.drpcore.common.DarkRoleplayCore;
import net.dark_roleplay.drpcore.common.crafting.CraftingRegistry;
import net.dark_roleplay.drpcore.common.crafting.SimpleRecipe;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.common.handler.DRPCoreGuis;
import net.dark_roleplay.drpcore.common.handler.DRPCorePackets;
import net.dark_roleplay.drpcore.common.network.packets.crafting.Initialize_SimpleRecipe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class RecipeCrafting_SimpleRecipe extends DRPGuiScreen{

	private static ResourceLocation bg = new ResourceLocation(DRPCoreInfo.MODID, "textures/guis/recipe_crafting_simple.png");
	
	public InventoryPlayer inventory;
	
	private static SimpleRecipe recipe;
	
	private int currentIngredientOffset = 0;

	private Button_Craft craft;
	
	private BlockPos pos;
	
	public RecipeCrafting_SimpleRecipe(BlockPos pos) {
		super(bg, 178, 161);
		this.pos = pos;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initGui(){
		super.initGui();
		short buttonID = 0;
		this.craft = new Button_Craft(buttonID++,this.guiLeft + 150, this.guiTop + 9);
		this.buttonList.add(this.craft);
		GuiButton retBack = new GuiButton(5, this.guiLeft - 52, this.guiTop, 50, 12, "Return");
		this.buttonList.add(retBack);
	}
	
	@Override
	protected void drawForeground(int mouseX, int mouseY, float partialTicks) {
		inventory = Minecraft.getMinecraft().player.inventory;
		

		ItemStack s = recipe.getMainOutput()[0];
		
		/**-------- Output ---------**/
		this.itemRender.renderItemIntoGUI(s, this.guiLeft + 10, this.guiTop + 10);
		this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, s, this.guiLeft + 10, this.guiTop + 10, s.getCount() == 1 ? null : String.valueOf(s.getCount()));
		
		
		/**-------- Input ---------**/
		for(int i = 0; i < 9; i++){
			if(i + currentIngredientOffset >= recipe.getMainIngredients().length)
				break;
			s = recipe.getMainIngredients()[i + currentIngredientOffset];
			this.itemRender.renderItemIntoGUI(s, this.guiLeft + 9 + (i * 18), this.guiTop + 41);
			this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, s, this.guiLeft + 9 + (i * 18), this.guiTop + 41, s.getCount() == 1 ? null : String.valueOf(s.getCount()));
		}
		
		/**-------- Inventory ---------**/
		for(int i = 0; i < 9; i++){
			s = inventory.mainInventory.get(i);
			if(s != null){
				this.itemRender.renderItemIntoGUI(inventory.mainInventory.get(i), this.guiLeft + 9 + (i * 18), this.guiTop + 140);
				this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, s, this.guiLeft + 9 + (i * 18), this.guiTop + 140, s.getCount() == 1 ? null : String.valueOf(s.getCount()));
			}
		}
		
		for(int i = 9; i < 36; i++){
			s = inventory.mainInventory.get(i);
			if(s != null){
				this.itemRender.renderItemAndEffectIntoGUI(inventory.mainInventory.get(i), this.guiLeft + 9 + ((i % 9) * 18), this.guiTop + 83 + (((i - 9) /9) * 18));
				this.itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, s, this.guiLeft + 9 + ((i % 9) * 18), this.guiTop + 83 + (((i - 9) /9) * 18), s.getCount() == 1 ? null : String.valueOf(s.getCount()));
			}
		}
		
	}
	
	
	public static void setRecipe(SimpleRecipe recipe) {
		RecipeCrafting_SimpleRecipe.recipe = recipe;
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {

		switch (button.id) {
		case 0:
			DRPCorePackets.sendToServer(new Initialize_SimpleRecipe(this.recipe.getRegistryName()));
			break;
		case 5:
			ClientProxy.useRecipeData = true;
			Minecraft.getMinecraft().player.openGui(DarkRoleplayCore.instance, DRPCoreGuis.DRPCORE_GUI_CRAFTING_RECIPESELECTION, Minecraft.getMinecraft().world, this.pos.getX(), this.pos.getY(), this.pos.getZ());
			break;
		}
	}
}
