package net.dark_roleplay.drpcore.api.skills;

import net.dark_roleplay.drpcore.api.gui.advanced.Gui_Icon;
import net.dark_roleplay.drpcore.common.DRPCoreReferences;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.registries.IForgeRegistryEntry;

public class SkillPoint extends IForgeRegistryEntry.Impl<SkillPoint>{

	private ResourceLocation registryName;
	private String unlocalizedName;
	private ResourceLocation iconRes;
	
	@SideOnly(Side.CLIENT)
	private Gui_Icon icon;
	
	public SkillPoint(ResourceLocation registryName, ResourceLocation icon){
		this(registryName, registryName.getResourcePath(), icon);
	}
	
	public SkillPoint(ResourceLocation registryName, String unlocalizedName, ResourceLocation icon){
		this.setRegistryName(registryName);
		this.unlocalizedName = "skill.point." + unlocalizedName + ".name";
		this.iconRes = icon;
	}

	public String getUnlocalizedName() {
		return unlocalizedName;
	}

	@SideOnly(Side.CLIENT)
	public Gui_Icon getIcon() {
		if(icon == null)
			this.icon = new Gui_Icon(iconRes);
		return icon;
	}
	
	public boolean canLevel(int currentLevel){
		return false;
	}
	
	public float requiredXP(int currentLevel){
		return currentLevel * 100F;
	}
}
