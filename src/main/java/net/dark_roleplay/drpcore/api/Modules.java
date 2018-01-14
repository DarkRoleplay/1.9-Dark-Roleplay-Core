package net.dark_roleplay.drpcore.api;

import net.dark_roleplay.drpcore.modules.Module;
import net.dark_roleplay.drpcore.modules.crafting.Module_Crafting;
import net.dark_roleplay.drpcore.modules.hud.Module_Hud;
import net.dark_roleplay.drpcore.modules.premium.Module_Premium;
import net.dark_roleplay.drpcore.modules.skill.Module_Skill;
import net.dark_roleplay.drpcore.modules.update_check.Module_UpdateChecker;
import net.dark_roleplay.drpcore.modules.wood.Module_Wood;

public class Modules {
	
	public static final Module_Hud HUD = new Module_Hud("HUD");
	public static final Module CONFIGS = new Module("CONFIGS");
	public static final Module QUEST = new Module("QUEST");
	public static final Module CRAFTING = new Module("CRAFTING");
	public static final Module PARTY = new Module("PARTY", HUD);
	public static final Module_Skill SKILL = new Module_Skill("SKILL", HUD);
	public static final Module_UpdateChecker UPDATE_CHECKER = new Module_UpdateChecker("UPDATE_CHECKER", CONFIGS);
	public static final Module_Wood WOODS = new Module_Wood("WOODS");
	public static final Module_Premium PREMIUM = new Module_Premium("PREMIUM");
	public static final Module_Crafting CrAFTING2 = new Module_Crafting("CRAFTING2");
	
}
