package net.dark_roleplay.core.api.old.util.sitting;

import java.util.List;

import net.dark_roleplay.core.common.objects.entities.util.sitting.Sittable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class SittingUtil {

	public static boolean sitOnBlock(World world, double x, double y, double z, EntityPlayer player, double par6){
		if (!checkForExistingEntity(world, x, y, z, player) && !world.isRemote){
			Sittable nemb = new Sittable(world, x, y, z, par6);
			world.spawnEntity(nemb);
			player.startRiding(nemb);
		}
		
		return true;
	}

	public static boolean sitOnBlockWithRotationOffset(World world, double x, double y, double z, EntityPlayer player, double par6, int metadata, double offset){
		if (!checkForExistingEntity(world, x, y, z, player)){
			Sittable nemb = new Sittable(world, x, y, z, par6, metadata, offset);
			world.spawnEntity(nemb);
			player.startRiding(nemb);
		}
		return true;
	}

	public static boolean checkForExistingEntity(World world, double x, double y, double z, EntityPlayer player){
		List<Sittable> listEMB = world.getEntitiesWithinAABB(Sittable.class, new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D).expand(1D, 1D, 1D));
		for (Sittable mount : listEMB){
			if (mount.blockPosX == x && mount.blockPosY == y && mount.blockPosZ == z){
				if (!mount.isBeingRidden()){
					player.startRiding(mount);
				}
				return true;
			}
		}
		return false;
	}
	
	public static boolean isSomeoneSitting(World world, double x, double y, double z){
		List<Sittable> listEMB = world.getEntitiesWithinAABB(Sittable.class, new AxisAlignedBB(x, y, z, x + 1.0D, y + 1.0D, z + 1.0D).expand(1D, 1D, 1D));
		for (Sittable mount : listEMB){
			if (mount.blockPosX == x && mount.blockPosY == y && mount.blockPosZ == z){
				return mount.isBeingRidden();
			}
		}
		return false;
	}
}
