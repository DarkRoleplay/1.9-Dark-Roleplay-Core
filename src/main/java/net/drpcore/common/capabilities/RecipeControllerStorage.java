package net.drpcore.common.capabilities;

import java.util.List;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

public class RecipeControllerStorage  implements IStorage<IRecipeController>{
	
    @Override
    public NBTTagCompound writeNBT(Capability<IRecipeController> capability, IRecipeController instance, EnumFacing side) {
    	NBTTagCompound tag = new NBTTagCompound();
    	
    	List<String> locked = instance.getLockedRecipes();
    	List<String> unlocked = instance.getUnlockedRecipes();
    	
    	tag.setInteger("lockedAmount", locked.size());
    	tag.setInteger("unlockedAmount", unlocked.size());
    	
    	for(int i = 0; i < locked.size(); i++){
        	tag.setString("locked" + String.valueOf(i), locked.get(i));
    	}

    	for(int i = 0; i < unlocked.size(); i++){
        	tag.setString("unlocked" + String.valueOf(i), unlocked.get(i));
    	}
    	
        return tag;
    }

	@Override
	public void readNBT(Capability<IRecipeController> capability, IRecipeController instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound tag = (NBTTagCompound) nbt;
		
		if(tag != null && tag.hasKey("lockedAmount") && tag.hasKey("unlockedAmount")){
			int lockedAmount = tag.getInteger("lockedAmount");
			int unlockedAmount = tag.getInteger("unlockedAmount");
			
			for(int i = 0; i < lockedAmount; i++){
	        	instance.lockRecipe(tag.getString("locked" + String.valueOf(i)));
	    	}
	
	    	for(int i = 0; i < unlockedAmount; i++){
	    		instance.unlockRecipe(tag.getString("unlocked" + String.valueOf(i)));
	    	}
		}
	}
}