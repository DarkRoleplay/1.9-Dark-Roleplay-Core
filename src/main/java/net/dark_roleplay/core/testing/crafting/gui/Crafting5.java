package net.dark_roleplay.core.testing.crafting.gui;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import net.dark_roleplay.core.common.References;
import net.dark_roleplay.core.testing.crafting.CraftingRegistry;
import net.dark_roleplay.core.testing.crafting.Recipe;
import net.dark_roleplay.core.testing.gui_testing.Gui_Test;
import net.dark_roleplay.core.testing.gui_testing.components.Component;
import net.dark_roleplay.core.testing.gui_testing.components.TextBox;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;

public class Crafting5 extends Gui_Test{
	
	public static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/guis/crafting/crafting_gui.png");
	
	private static Map<Block, List<Recipe>> globalRecipes = new HashMap<Block, List<Recipe>>();
	
	private List<Recipe> recipes = null;
	private Block craftingStation;
	private BlockPos stationPos;
	
	ModeButton modeButton;
	GuiButton pageLeft;
	GuiButton pageRight;
	GuiButton variantLeft;
	GuiButton variantRight;
	GuiButton craft;
	
	public Crafting5(BlockPos pos, Block craftingStation) {
		this.craftingStation = craftingStation;
		this.stationPos = pos;
		
		if(globalRecipes.containsKey(craftingStation)) {
			recipes = globalRecipes.get(craftingStation);
		}else {
			recipes = CraftingRegistry.getRecipesForStation(craftingStation);
			globalRecipes.put(craftingStation, recipes);
		}
		
//		globalRecipes = new HashMap<Block, List<Recipe>>();
		
		System.out.println(CraftingRegistry.recipes);
		for(Recipe recipe : recipes) {
			System.out.println(recipe.getRegistryName());
		}
	}
	
	public void initGui(){
		comps.clear();

		int i = 0;

		int posY = (this.height - 166) / 2;
		int posX = (this.width / 2) - 173;
		
		TextBox tb = new TextBox(this.fontRenderer, posX + 23, posY + 14) {
			@Override
			public void contentChanged() {
				if(this.getText().isEmpty()) {
					Crafting5.this.recipes = Crafting5.globalRecipes.get(Crafting5.this.craftingStation);
				}else {
					Crafting5.this.recipes = Crafting5.globalRecipes.get(Crafting5.this.craftingStation);
					Crafting5.this.recipes = Crafting5.this.recipes.parallelStream().filter(r -> r.getRegistryName().contains(this.getText())).collect(Collectors.toList());
				}
				System.out.println("New Recipes!");
				for(Recipe recipe : recipes) {
					System.out.println(recipe.getRegistryName());
				}
			}
		};
		tb.setWidth(108);
		comps.add(tb);
		
		this.addButton(new ModeButton(i++, posX + 135, posY + 12));
		this.addButton(new ArrowButton(i++, posX + 50, posY + 136, false));
		this.addButton(new ArrowButton(i++, posX + 110, posY + 136, true));
		

		posX += 175;
		this.addButton(new ArrowButton(i++, posX + 11, posY + 133, false));
		this.addButton(new ArrowButton(i++, posX + 50, posY + 133, true));
    }

	public void drawScreen(int mouseX, int mouseY, float partialTicks){
		int posY = (this.height - 166) / 2;
		int posX = (this.width / 2) - 173;
		this.drawDefaultBackground();
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		
		//Left Side
		this.drawTexturedModalRect(posX, posY, 0, 0, 172, 166);
		this.drawTexturedModalRect(posX + 9, posY + 12, 172, 97, 12, 12);

		for(int x = 0; x < 6; x++){
			for(int y = 0; y < 4; y++){
				this.drawTexturedModalRect(posX + 11 + (x * 25), posY + 31 + (y * 25), 172, 32, 25, 25);
			}
		}
		
		//Right Side
		posX += 175;
		this.drawTexturedModalRect(posX, posY, 0, 0, 172, 166);
		

		for(int x = 0; x < 6; x++)
			this.drawTexturedModalRect(posX + 11 + (x * 25), posY + 98, 172, 32, 25, 25);
		
		for(int x = 0; x < 6; x++)
			this.drawTexturedModalRect(posX + 11 + (x * 25), posY + 22, 172, 32, 25, 25);

		for(int x = 0; x < 6; x++)
			this.drawTexturedModalRect(posX + 11 + (x * 25), posY + 60, 172, 32, 25, 25);
		
		this.drawTexturedModalRect(posX + 24, posY + 129, 172, 32, 25, 25);
		this.drawTexturedModalRect(posX + 137, posY + 129, 196, 57, 24, 24);

		
		this.drawString(this.fontRenderer, I18n.format("Output"), posX + 11, posY +  11, 0xFFFFFFFF);
		this.drawString(this.fontRenderer, I18n.format("Ingredients"), posX + 11, posY +  49, 0xFFFFFFFF);
		this.drawString(this.fontRenderer, I18n.format("Tools"), posX + 11, posY +  87, 0xFFFFFFFF);

		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
    }
	
	protected void actionPerformed(GuiButton button) throws IOException{
		
    }
}
