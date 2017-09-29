package net.dark_roleplay.drpcore.api.skills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.dark_roleplay.drpcore.api.gui.advanced.Gui_Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class Skill extends IForgeRegistryEntry.Impl<Skill>{

	private String unlocalizedName;
	private String unlocalizedDesc;
	
	private Gui_Icon icon;
	
	private Map<SkillTree, SkillTreeData> skillTrees;
	
	public Skill(ResourceLocation registryName, ResourceLocation icon){
		this.setRegistryName(registryName);
		this.unlocalizedName = "skill." + registryName.getResourcePath() + ".name";
		this.unlocalizedDesc = "skill." + registryName.getResourcePath() + ".desc";
		this.icon = new Gui_Icon(icon);
		this.skillTrees = new HashMap<SkillTree, SkillTreeData>();
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}
	
	public String getUnlocalizedDesc() {
		return unlocalizedDesc;
	}

	public Gui_Icon getIcon() {
		return icon;
	}
	
	public List<SkillTreeData> getSkillTrees(){
		return new ArrayList<SkillTreeData>(this.skillTrees.values());
	}
	
	public SkillTreeData getDataForTree(SkillTree tree){
		return this.skillTrees.get(tree);
	}
	
	public boolean canLevel(int currentLevel){
		return false;
	}
	
	public float requiredXP(int currentLevel){
		return currentLevel * 100F;
	}
	
	public void addToSkillTree(SkillTree tree, int posX, int posY){
		this.skillTrees.put(tree, new SkillTreeData(tree, posX, posY));
	}
}
