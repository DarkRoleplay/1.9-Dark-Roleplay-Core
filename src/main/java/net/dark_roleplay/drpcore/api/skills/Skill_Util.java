package net.dark_roleplay.drpcore.api.skills;

import net.dark_roleplay.drpcore.client.gui.crafting.recipe_selection.RecipeSelection;
import net.dark_roleplay.drpcore.client.gui.skills.Gui_SkillOverview;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;

public class Skill_Util {

	public static void openSkillOverview(){
		if(DRPCoreInfo.SIDE.isClient())
			Minecraft.getMinecraft().displayGuiScreen(new Gui_SkillOverview());
	}
	
	
}
