package net.dark_roleplay.drpcore.common.capabilities.player.crafting;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    	Map<String,Float> progressed = instance.getProgressedRecipes();
    	
    	tag.setInteger("lockedAmount", locked.size());
    	tag.setInteger("unlockedAmount", unlocked.size());
    	tag.setInteger("progressedAmount", progressed.size());
    	
    	for(int i = 0; i < locked.size(); i++){
        	tag.setString("locked" + String.valueOf(i), locked.get(i));
    	}

    	for(int i = 0; i < unlocked.size(); i++){
        	tag.setString("unlocked" + String.valueOf(i), unlocked.get(i));
    	}
    	
    	int i = 0;
    	for(String key : progressed.keySet()){
        	tag.setString("progressedKey" + String.valueOf(i), key);
        	tag.setFloat("progressedValue" + String.valueOf(i), progressed.get(key));	
    		i++;
    	}
    	
        return tag;
    }

	@Override
	public void readNBT(Capability<IRecipeController> capability, IRecipeController instance, EnumFacing side, NBTBase nbt) {
		NBTTagCompound tag = (NBTTagCompound) nbt;
		
		if(tag != null && tag.hasKey("lockedAmount") && tag.hasKey("unlockedAmount") && tag.hasKey("progressedAmount")){
			int lockedAmount = tag.getInteger("lockedAmount");
			int unlockedAmount = tag.getInteger("unlockedAmount");
			int progressedAmount = tag.getInteger("progressedAmount");
			
			
			for(int i = 0; i < lockedAmount; i++){
				System.out.println("Debug1");
	        	instance.lockRecipe(tag.getString("locked" + String.valueOf(i)));
	    	}
	
	    	for(int i = 0; i < unlockedAmount; i++){
	    		System.out.println("Debug2");
	    		instance.unlockRecipe(tag.getString("unlocked" + String.valueOf(i)));
	    	}
	    	
	    	for(int i = 0; i < progressedAmount; i++){
	    		System.out.println("Debug3");
	    		instance.progressRecipe(tag.getString("progressedKey" + String.valueOf(i)), tag.getFloat("progressedValue" + String.valueOf(i)));
	    	}
		}
	}
}