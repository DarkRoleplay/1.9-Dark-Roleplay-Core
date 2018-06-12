package net.dark_roleplay.core.testing;

import java.util.Random;

import net.dark_roleplay.core.api.old.modules.color.IColored;
import net.dark_roleplay.library_old.items.DRPItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Test_PaintItem extends DRPItem implements IColored{

	public Test_PaintItem(String name, int stackSize, String... subNames) {
		super(name, stackSize, subNames);
	}

	@Override
	public int getColor(ItemStack stack) {
		Random rnd = new Random();
		return rnd.nextInt(0xFFFFFF);
	}

	@Override
	public void setColor(ItemStack stack, int color) {
		// TODO Auto-generated method stub
		
	}

}
