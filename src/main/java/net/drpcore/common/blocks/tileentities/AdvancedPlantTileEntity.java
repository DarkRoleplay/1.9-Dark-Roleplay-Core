package net.drpcore.common.blocks.tileentities;

import java.util.HashMap;
import java.util.Map;

import net.drpcore.api.blocks.AdvancedPlant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class AdvancedPlantTileEntity  extends TileEntity implements ITickable {

    private int counter = 0;
    //private BlockPos[][] crops2 = new BlockPos[11][11];
    private HashMap<BlockPos,Integer> crops = new HashMap<BlockPos,Integer>();
    private int lastDay = 0;

    //To Test just every second
    private int delayCounter = 100;
    
    public void setDay(int day){
    	this.lastDay = day;
    }
    
    public boolean addCrop(BlockPos pos){
    		if(!crops.containsKey(pos)){
    			crops.put(pos,0);
    			return true;
    		}
    	return false;
    }

    
    public boolean removeCrop(BlockPos pos){
    	if(crops.containsKey(pos)){
    		crops.remove(pos);
    		return true;
    	}
    	return false;
    }

    public HashMap<BlockPos,Integer> getCrops(){
    	return crops;
    }
    
    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate){
        return false;
    }
    
    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        
        for(int i = 0; i < compound.getInteger("reg_crops"); i++){
        	int[] intPos = compound.getIntArray(String.valueOf(i));
        	BlockPos pos = new BlockPos(intPos[0], intPos[1], intPos[2]);
        	crops.put(pos, intPos[3]);
        }
        this.lastDay = compound.getInteger("last_day");
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        
        int i = 0;
        for(Map.Entry<BlockPos, Integer> entry : crops.entrySet()) {
            BlockPos key = entry.getKey();
            int value = entry.getValue();
            
            int[] intPos = new int[]{key.getX(), key.getY(), key.getZ(), value};
            compound.setIntArray(String.valueOf(i), intPos);
            i++;
        }
        compound.setInteger("reg_crops", i);
        compound.setInteger("last_day", this.lastDay);
        return compound;
    }

	@Override
	public void update() {
		delayCounter--;
        if (delayCounter <= 0) {
        	int j = (int)(this.getWorld().getWorldTime() / 24000L % 2147483647L);
        	if(lastDay != j){
        		lastDay = j;
        		for(BlockPos pos : crops.keySet()){
        			crops.replace(pos, crops.get(pos) + 1);
        			IBlockState state = this.getWorld().getBlockState(pos);
        			if(state.getBlock() instanceof AdvancedPlant){
        				((AdvancedPlant) state.getBlock()).grow(this.getWorld(), crops.get(pos), pos, state);
        			}
        			//this.getWorld().setBlockState(pos.add(0,1,0),Blocks.GLOWSTONE.getDefaultState());
        		}
        	}
            delayCounter = 100;
        }
	}
}