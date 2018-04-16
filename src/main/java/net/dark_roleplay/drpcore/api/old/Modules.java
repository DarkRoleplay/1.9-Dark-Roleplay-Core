package net.dark_roleplay.drpcore.api.old;

import net.dark_roleplay.drpcore.api.old.modules.Module;
import net.dark_roleplay.drpcore.api.old.modules.crafting.Module_Crafting;
import net.dark_roleplay.drpcore.api.old.modules.hud.Module_Hud;
import net.dark_roleplay.drpcore.api.old.modules.materials.Module_Material;
import net.dark_roleplay.drpcore.api.old.modules.work_in_progress.premium.Module_Premium;
import net.dark_roleplay.drpcore.api.old.modules.work_in_progress.update_check.Module_UpdateChecker;

public class Modules {
	
	public static final Module_Hud HUD = new Module_Hud("HUD");
	private static final Module CONFIGS = new Module("CONFIGS");
	private static final Module QUEST = new Module("QUEST");
	private static final Module CRAFTING = new Module("CRAFTING");
	private static final Module PARTY = new Module("PARTY", HUD);
	public static final Module_UpdateChecker UPDATE_CHECKER = new Module_UpdateChecker("UPDATE_CHECKER", CONFIGS);
	public static final Module_Material MATERIALS = new Module_Material("MATERIAL");
	private static final Module_Premium PREMIUM = new Module_Premium("PREMIUM");
	public static final Module_Crafting CRAFTING2 = new Module_Crafting("CRAFTING2");
	
}
