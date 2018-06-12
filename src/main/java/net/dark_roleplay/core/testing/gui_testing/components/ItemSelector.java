package net.dark_roleplay.core.testing.gui_testing.components;

import net.dark_roleplay.core.common.References;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import scala.util.Random;

public class ItemSelector extends Component{

	private static final ResourceLocation texture = new ResourceLocation(References.MODID, "textures/guis/gui_elements.png");
	
	private boolean isOpen = false;
	
	int selectorX = 0, selectorY = 0;
	
	public ItemSelector(int posX, int posY, int selectorPosX, int selectorPosY, NonNullList<ItemStack> inventory) {
		super(18, 18, 18, 18);
		this.posX = posX;
		this.posY = posY;
		this.selectorX = selectorPosX;
		this.selectorY = selectorPosY;
		Random rnd = new Random();
		rnd.nextFloat();
	}
	
	@Override
	public ResourceLocation getTexture() {
		return texture;
	}

	@Override
	public void render(int mouseX, int mouseY, float partialTicks) {
		// TODO Auto-generated method stub
		
	}

}
