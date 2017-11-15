package net.dark_roleplay.drpcore.testing;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class Testing_Entity extends Entity{

	public Testing_Entity(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {}

}
