package net.dark_roleplay.core.api.old.crafting2.item_ingredient;

import com.google.gson.JsonObject;

import net.dark_roleplay.core.api.old.crafting2.IIngredient;
import net.dark_roleplay.core.api.old.crafting2.IIngredientFactory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class ItemIngredientFactory implements IIngredientFactory{

	@Override
	public IIngredient loadFromJson(JsonObject object) {
		if(object.has("name")) {
			int amount = JsonUtils.getInt(object, "amount", 1);
			int meta = JsonUtils.getInt(object, "meta", 0);
			
			 IForgeRegistry<Item> itemRegistry = GameRegistry.findRegistry(Item.class);
			 if(itemRegistry.containsKey(new ResourceLocation(object.get("name").getAsString()))) {
				 ItemStack stack = new ItemStack(itemRegistry.getValue(new ResourceLocation(object.get("name").getAsString())), amount, meta);
				 return new ItemIngredient(stack);
			 }
		}
		return null;
	}
}
