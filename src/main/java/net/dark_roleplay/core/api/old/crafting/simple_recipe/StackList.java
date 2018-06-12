package net.dark_roleplay.core.api.old.crafting.simple_recipe;

import java.util.List;

import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;

public class StackList {

	ItemStack[] items;
	
	public StackList(ItemStack... items){
		this.items = items;
	}
	
	public ItemStack[] asArr(){
		return this.items;
	}
	
	public List<ItemStack> asList(){
		return Arrays.asList(items);
	}
	
}
