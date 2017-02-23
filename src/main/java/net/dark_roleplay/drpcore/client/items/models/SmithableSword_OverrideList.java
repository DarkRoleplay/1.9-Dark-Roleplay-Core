package net.dark_roleplay.drpcore.client.items.models;

import java.util.List;

import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.block.model.ItemOverrideList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class SmithableSword_OverrideList extends ItemOverrideList {

	public SmithableSword_OverrideList(List<ItemOverride> overridesIn) {
		super(overridesIn);
	}

	/**
	 * handleItemState() is used to create/select a suitable IBakedModel based
	 * on the itemstack information. Typically, this will extract NBT
	 * information from the itemstack and customise the model based on that.
	 * It's probably safest to return a new model or at least an immutable one,
	 * rather than modifying the originalModel passed in, in case the rendering
	 * is multithreaded (block rendering has this problem, for example).
	 * 
	 * @param originalModel
	 * @param stack
	 * @param world
	 * @param entity
	 * @return
	 */
	@Override
	public IBakedModel handleItemState(IBakedModel originalModel, ItemStack stack, World world, EntityLivingBase entity) {
		int numberOfChessPieces = 0;
		if (stack != null) {
			numberOfChessPieces = 1;
		}
		return new SmithableFinal(originalModel, numberOfChessPieces);
	}

}