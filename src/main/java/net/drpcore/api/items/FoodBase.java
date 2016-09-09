package net.drpcore.api.items;

import java.util.ArrayList;
import java.util.List;

import net.drpcore.common.util.food.EnumFoodCategory;
import net.drpcore.common.util.food.FoodIngredient;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


public class FoodBase extends Item {

	private int baseFeedAmount;

	private float baseSaturation;

	private boolean alwaysEdible;

	private EnumFoodCategory foodType;

	public int itemUseDuration;

	private int potionId;

	private int potionDuration;

	private int potionAmplifier;

	private float potionEffectProbability;

	private static final String __OBFID = "CL_00000036";

	private static FoodIngredient[] INGREDIENTS;

	public FoodBase(int feedAmount, float saturation, int eatTime, FoodIngredient[] ingreadients, EnumFoodCategory foodType) {
		this.baseFeedAmount = feedAmount;
		this.baseSaturation = saturation;
		this.itemUseDuration = eatTime * 20;
		this.foodType = foodType;
		this.setHasSubtypes(true);
		this.INGREDIENTS = ingreadients;
	}

	public ItemStack[] getPossibleIngredients() {

		ItemStack[] Ingredients = new ItemStack[] {};
		return Ingredients;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn, EnumHand hand) {

		if(playerIn.canEat(this.alwaysEdible)) {
			playerIn.setActiveHand(hand);
			// playerIn.setItemInUse(itemStackIn,
			// this.getMaxItemUseDuration(itemStackIn));
		}
		return new ActionResult(EnumActionResult.SUCCESS, itemStackIn);
	}

	public FoodBase setPotionEffect(int id, int duration, int amplifier, float probability) {

		this.potionId = id;
		this.potionDuration = duration;
		this.potionAmplifier = amplifier;
		this.potionEffectProbability = probability;
		return this;
	}

	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {

		if(entityLiving instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityLiving;
			int meta = stack.getMetadata();
			boolean poisioned;
			if((poisioned = isPoisioned(meta)))
				meta-- ;
			ArrayList<Integer> addToList = getContainedIngrediens(meta);
			int feedAmount = this.baseFeedAmount;
			float saturationAmount = this.baseSaturation;
			for(int i = 0; i < addToList.size(); i++ ) {
				if(addToList.get(i) == 1) {
					feedAmount += INGREDIENTS[i].getFeedAmount();
					saturationAmount += INGREDIENTS[i].getSaturationAmount();
				}
			}
			--stack.stackSize;
			if(poisioned) {
				player.addPotionEffect(new PotionEffect(Potion.getPotionById(19), 1200, 1));
			}
			player.getFoodStats().addStats(feedAmount, saturationAmount);
			worldIn.playSound((EntityPlayer) null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
			this.onFoodEaten(stack, worldIn, player);
			player.addStat(StatList.getObjectUseStats(this));
		}
		return stack;
	}

	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player) {

		if( ! worldIn.isRemote && this.potionId > 0 && worldIn.rand.nextFloat() < this.potionEffectProbability) {
			player.addPotionEffect(new PotionEffect(Potion.getPotionById(this.potionId), this.potionDuration * 20, this.potionAmplifier));
		}
	}

	public FoodBase setAlwaysEdible() {

		this.alwaysEdible = true;
		return this;
	}

	@Override
	public EnumAction getItemUseAction(ItemStack stack) {

		return EnumAction.EAT;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {

		return this.itemUseDuration;
	}

	public boolean isPoisioned(int meta) {

		if(meta % 2 == 1)
			return true;
		else
			return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {

		int meta = stack.getItemDamage();
		if(isPoisioned(meta)) {
			meta-- ;
			tooltip.add(TextFormatting.DARK_GREEN + "Poisoned");
		}
		if(meta > 0)
			tooltip.add(TextFormatting.WHITE + "Ingredients:");
		if(stack.getItemDamage() == 0)
			;
		tooltip.add(TextFormatting.DARK_GRAY + "none");
		ArrayList<Integer> addToList = getContainedIngrediens(meta);
		for(int i = 0; i < addToList.size(); i++ ) {
			if(addToList.get(i) == 1) {
				tooltip.add(TextFormatting.AQUA + this.INGREDIENTS[i].getName());
			}
		}
	}

	public ArrayList<Integer> getContainedIngrediens(int meta) {

		ArrayList<Integer> containing = new ArrayList<Integer>();
		if(meta >= Math.pow(2, this.INGREDIENTS.length + 1)) {
			meta = (int) (Math.pow(2, this.INGREDIENTS.length + 1) - 1);
		}
		meta /= 2;
		while(meta != 0) {
			containing.add(meta % 2);
			meta /= 2;
		}
		return containing;
	}
}
