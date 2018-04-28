package net.dark_roleplay.drpcore.client.build_mode;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityBuildingViewer extends Entity{

	public EntityBuildingViewer(World world) {
		super(world);
	}

	public void rotate(float x, float y) {
		this.setRotation(this.rotationYaw + x, -360);
	}
	
	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {}

}
