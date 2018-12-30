package net.dark_roleplay.core.api.storage;

import javax.annotation.Nullable;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class DynamicStorageTileEntity extends TileEntity{

	protected ItemStackHandler inventoryMain = null;

	public DynamicStorageTileEntity() {
		this(9);
	}

	public DynamicStorageTileEntity(int size) {
		this.inventoryMain = new ItemStackHandler(size) {
			@Override
		    protected void onContentsChanged(int slot){
				DynamicStorageTileEntity.this.markDirty();
		    }
		};
	}

	@Override
    public void readFromNBT(NBTTagCompound compound){
        super.readFromNBT(compound);

        this.inventoryMain = new ItemStackHandler() {
			@Override
		    protected void onContentsChanged(int slot){
				DynamicStorageTileEntity.this.markDirty();
		    }
		};

        if(compound.hasKey("inventoryMain"))
        	this.inventoryMain.deserializeNBT((NBTTagCompound) compound.getTag("inventoryMain"));
    }

    @Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        NBTTagCompound comp = super.writeToNBT(compound);

        NBTTagCompound inventory = this.inventoryMain.serializeNBT();
        comp.setTag("inventoryMain", inventory);

        return comp;
    }

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return true;
    	return super.hasCapability(capability, facing);
	}

	@Override
	@Nullable
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing){
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return (T) this.inventoryMain;
    	return super.getCapability(capability, facing);
	}

	@Override
	public NBTTagCompound getUpdateTag(){
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public void handleUpdateTag(NBTTagCompound tag){
		this.readFromNBT(tag);;
	}
}
