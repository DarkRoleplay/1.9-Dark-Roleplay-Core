package net.dark_roleplay.drpcore.api.skills;

import net.dark_roleplay.drpcore.api.gui.advanced.Gui_Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class SkillPoint extends IForgeRegistryEntry.Impl<SkillPoint>{

	private ResourceLocation registryName;
	private String unlocalizedName;
	
	private Gui_Icon icon;
	
	public SkillPoint(ResourceLocation registryName, ResourceLocation icon){
		this(registryName, registryName.getResourcePath(), icon);
	}
	
	public SkillPoint(ResourceLocation registryName, String unlocalizedName, ResourceLocation icon){
		this.setRegistryName(registryName);
		this.unlocalizedName = "skill.point." + unlocalizedName + ".name";
		this.icon = new Gui_Icon(icon);
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	public Gui_Icon getIcon() {
		return icon;
	}
	
	public boolean canLevel(int currentLevel){
		return false;
	}
	
	public float requiredXP(int currentLevel){
		return currentLevel * 100F;
	}
}
