package net.dark_roleplay.core.testing.skills;

import java.util.HashSet;
import java.util.Set;

import net.dark_roleplay.core.testing.crafting.IIcon;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class SkillTree extends IForgeRegistryEntry.Impl<SkillTree>{

	private IIcon icon = null;
	
	private Set<Skill> skills = new HashSet<Skill>();
	
	/**
	 * Used to add Skills to a Skill Tree
	 * Frozen after world init.
	 * @param skill The Skill object to add to this SkillTree
	 */
	public void addSkill(Skill skill) {
		skills.add(skill);
	}
	
	/**
	 * Used to get the Icon of a Skill Tree
	 * @return the Icon used to symbolize this SkillTree
	 */
	public IIcon getIcon() {
		return this.icon;
	}
	
	/**
	 * Used to get all Skills within a Skill Tree
	 * @return the Skills within this SkilLTree
	 */
	public Set<Skill> getSkills(){
		return this.skills;
	}
}
