package net.dark_roleplay.drpcore.modules.skill;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Skill extends  IForgeRegistryEntry.Impl<Skill>{

	public Skill(String registryName){
		this.setRegistryName(registryName);
	}
	
	public void onUnlocked(EntityPlayer player){
		
	}
	
public void onLocked(EntityPlayer player){
		
	}
	
	public boolean doesTick(){
		return false;
	}
	
	public static enum TYPE{
		ACTIVE,
		PASSIVE
	}
	
	@Override
	public String toString(){
		return this.getRegistryName().toString();
	}
}
