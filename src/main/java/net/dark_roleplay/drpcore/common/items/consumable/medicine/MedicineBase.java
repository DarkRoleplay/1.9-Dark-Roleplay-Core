package net.dark_roleplay.drpcore.common.items.consumable.medicine;

import java.util.ArrayList;

import net.dark_roleplay.drpcore.api.items.DRPItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class MedicineBase extends DRPItem {

	/** ------ VARIABLES------ **/

	protected byte useDuration = 32;
	
	protected boolean isDrinkable = false;
	
	protected ArrayList <String> ingrediants = new ArrayList <String>();

	protected void setUseDuration(byte useDuration) {
		this.useDuration = useDuration;
	}

	protected void setDrinkable(boolean isDrinkable) {
		this.isDrinkable = isDrinkable;
	}

	/** --------- END ------- **/

	public MedicineBase(String textures) {
		super(textures);
	}

	// Sets the Time it takes to Consume the Item
	@Override
	public int getMaxItemUseDuration(ItemStack item) {
		return useDuration;
	}

	// Sets the useAction of the Item (e.g. eat,drink)
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return isDrinkable ? EnumAction.DRINK : EnumAction.EAT;
	}

	// Happens when the Player Right-klicks the Item
	@Override
	public ActionResult<ItemStack> onItemRightClick(World itemStackIn, EntityPlayer worldIn, EnumHand playerIn) {
		worldIn.setActiveHand(playerIn);
		return new ActionResult(EnumActionResult.SUCCESS, worldIn.getHeldItem(playerIn));
	}

	// Happens when the Player has passed the Consume time
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		EntityPlayer entityplayer = entityLiving instanceof EntityPlayer ? (EntityPlayer) entityLiving : null;

		if (entityplayer == null || !entityplayer.capabilities.isCreativeMode) {
			stack.func_190918_g(1);
		}

		if (!worldIn.isRemote) {
			for (PotionEffect potioneffect : PotionUtils.getEffectsFromStack(stack)) {
				if (potioneffect.getPotion().isInstant()) {
					potioneffect.getPotion().affectEntity(entityplayer, entityplayer, entityLiving,
							potioneffect.getAmplifier(), 1.0D);
				} else {
					entityLiving.addPotionEffect(new PotionEffect(potioneffect));
				}
			}
		}

		if (entityplayer != null) {
			entityplayer.addStat(StatList.getObjectUseStats(this));
		}

		if (entityplayer == null || !entityplayer.capabilities.isCreativeMode) {
			if (stack.func_190926_b()) {
				return new ItemStack(Items.GLASS_BOTTLE);
			}

			if (entityplayer != null) {
				entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
			}
		}

		return stack;
	}
	
	public void addEffect(){
		
	}
}
