package net.dark_roleplay.drpcore.common.items.consumable.medicine;

import java.util.ArrayList;

import net.dark_roleplay.drpcore.api.items.DRPItem;
import net.dark_roleplay.drpcore.api.items.weapons.IExtendedRange;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.common.structure.Structure;
import net.minecraft.block.state.BlockStateContainer;
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
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MedicineBase extends DRPItem implements IExtendedRange{
	public MedicineBase(String textures) {
		super(textures);
	}
	
//	@Override
//	public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand){
//		player.getCapability(DRPCoreCapabilities.DRPCORE_RECIPE_CONTROLLER, null).progressRecipe("RECIPE12", 0.36f);
//		//Structure str = new Structure();
//		//str.writeStructure("ThisIsATest", world, pos, pos.add(2,2,2));
//        return EnumActionResult.PASS;
//    }

	@Override
	public float getRange() {
		return 1f;
	}
	
}
