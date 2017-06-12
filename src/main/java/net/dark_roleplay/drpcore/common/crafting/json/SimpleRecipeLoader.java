package net.dark_roleplay.drpcore.common.crafting.json;

import java.io.InputStreamReader;
import java.io.Reader;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class SimpleRecipeLoader {

	public static void loadRecipe(){
		//try(Reader reader = new InputStreamReader(, "UTF-8")){
            Gson gson = new GsonBuilder().create();
            SimpleRecipeJSON p = gson.fromJson("{"
            		+ "station:\"drpmedieval:spinning_wheel\","
            		+ "unlock:\"false\","
            		+ "ingredients:[{"
	            		+ "item:\"minecraft:apple\","
	            		+ "amount:5"
	            		+ "}]"
            		+ "}", SimpleRecipeJSON.class);
            System.out.println(p);
            for(ItemStackJSON stack : p.ingredients){
            	System.out.println(stack);
            }
       // }
	}
	
	private class SimpleRecipeJSON{
		private String station = "";
		private ItemStackJSON[] ingredients;
		private ItemStackJSON[] outputs;
		private boolean unlock = false;
		
		@Override
	    public String toString() {
	        return station + "  +   " + unlock;
	    }
	}
	
	private class ItemStackJSON{
		private String item = "";
		private int amount = 0;
		private int meta = 0;
		//private NBTTagCompound nbt;
		
		@Override
	    public String toString() {
	        return item + "  +   " + amount + "   +   " + meta;
	    }
	}
	
}
