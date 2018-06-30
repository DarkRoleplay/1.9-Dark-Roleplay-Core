package net.dark_roleplay.core.api.old.items;

import net.dark_roleplay.library_old.items.DRPItem;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public class DRPFood extends DRPItem{

    private final int itemUseDuration;
    private final int foodAmount;
    private final float saturationAmount;
	
	public DRPFood(int amount, float saturation, String name, int stackSize, String... subNames){
		this(amount, saturation, name, null, stackSize, subNames);
	}
	
	public DRPFood(int foodAmount, float saturationAmount, String name, String itemFolder, int stackSize, String... subNames){
		super(name, itemFolder, stackSize, subNames);
        this.itemUseDuration = 32;
        this.foodAmount = foodAmount;
        this.saturationAmount = saturationAmount;

	}

	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving){
        if (entityLiving instanceof EntityPlayer){
            EntityPlayer entityplayer = (EntityPlayer)entityLiving;
            entityplayer.getFoodStats().addStats(this.foodAmount, this.saturationAmount);
            worldIn.playSound((EntityPlayer)null, entityplayer.posX, entityplayer.posY, entityplayer.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
            this.onFoodEaten(stack, worldIn, entityplayer);
            entityplayer.addStat(StatList.getObjectUseStats(this));
        }
        stack.shrink(1);
        return stack;
    }
	
	protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer player){
//		if (!worldIn.isRemote && this.potionId != null && worldIn.rand.nextFloat() < this.potionEffectProbability){
//			player.addPotionEffect(new PotionEffect(this.potionId));
//		}
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack){
        return 32;
    }

	@Override
    public EnumAction getItemUseAction(ItemStack stack){
        return EnumAction.EAT;
    }
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn){
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (playerIn.canEat(false)){
            playerIn.setActiveHand(handIn);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
        }
        else
        {
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
        }
    }
}
