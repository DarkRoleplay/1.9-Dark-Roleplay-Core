package net.dark_roleplay.core.modules;

import net.dark_roleplay.core.api.old.modules.Module;
import net.dark_roleplay.core.api.old.modules.crafting.Module_Crafting;
import net.dark_roleplay.core.api.old.modules.work_in_progress.premium.Module_Premium;
import net.dark_roleplay.core.modules.hud.Module_Hud;
import net.dark_roleplay.core.modules.update_checker.Module_UpdateChecker;

public class Modules {
	
	public static final Module_Hud HUD = new Module_Hud("HUD");
	private static final Module CONFIGS = new Module("CONFIGS");
	private static final Module QUEST = new Module("QUEST");
	private static final Module CRAFTING = new Module("CRAFTING");
	private static final Module PARTY = new Module("PARTY", HUD);
	public static final Module_UpdateChecker UPDATE_CHECKER = new Module_UpdateChecker("UPDATE_CHECKER", CONFIGS);
	private static final Module_Premium PREMIUM = new Module_Premium("PREMIUM");
	public static final Module_Crafting CRAFTING2 = new Module_Crafting("CRAFTING2");
	
}
