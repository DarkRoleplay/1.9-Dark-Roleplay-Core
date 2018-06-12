package net.dark_roleplay.core.api.old.modules.color;

import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;

public class ColorHandler implements IItemColor{

	@Override
	public int colorMultiplier(ItemStack stack, int tintIndex) {
		if(tintIndex == 0)
			return 0xFFFFFFFF;
		if(stack.getItem() instanceof IColored){
			IColored colored = (IColored) stack.getItem();
			return colored.getColor(stack);
		}
		return 0;
	}

}
