package net.dark_roleplay.drpcore.api.skills;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;

public class Skill {

	private ItemStack displayItem;
	private String registryName;
	private String unlocalizedName = "";
	
	/** Unlocalized Description **/
	private String unlocalizedDescription = "";
	
	private SkillRequirements requirements = new SkillRequirements();
	
	private List<Skill> parents = new ArrayList<Skill>();
	
	private int posX, posY;
	
	public Skill(String registryName, ItemStack displayItem, int posX, int posY){
		this(registryName, null, displayItem, posX, posY);
	}
	
	public Skill(String registryName, String unlocalizedDescription, ItemStack displayItem, int posX, int posY){
		this.registryName = registryName;
		this.unlocalizedName = registryName;
		this.unlocalizedDescription = unlocalizedDescription;
		this.displayItem = displayItem;
		this.posX = posX;
		this.posY = posY;
	}
	
	public Skill addRequiredSkillPoint(SkillPoint skillPoint){
		this.requirements.addRequirement(skillPoint, (byte) 1);
		return this;
	}
	
	public Skill addRequiredSkillPoints(SkillPoint skillPoint, byte amount){
		this.requirements.addRequirement(skillPoint, amount);
		return this;
	}
	
	public Skill setUnlocalizedDesc(String unlocalizedDesc) {
		this.unlocalizedDescription = unlocalizedDesc;
		return this;
	}
	
	public SkillRequirements getRequirements(){
		return this.requirements;
	}
	
	public Skill addParent(Skill item){
		parents.add(item);
		return this;
	}

	public ItemStack getDisplayTexture() {
		return displayItem;
	}

	public String getRegistryName() {
		return registryName;
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}
	
	public String getUnlocalizedDesc() {
		return unlocalizedDescription;
	}

	public int getPosX() {
		return posX;
	}

	public int getPosY() {
		return posY;
	}

	public List<Skill> getParents() {
		return parents;
	}
	
	public void unlock(EntityPlayer player){
	}
}
