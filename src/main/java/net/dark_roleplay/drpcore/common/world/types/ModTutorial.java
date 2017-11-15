package net.dark_roleplay.drpcore.common.world.types;

import net.minecraft.world.WorldType;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModTutorial extends WorldType {

	public ModTutorial() {
		super("drp_mod_tutorial");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getTranslationKey() {
		return "generator.mod_turorials";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean canBeCreated() {
		return true;
	}

}
