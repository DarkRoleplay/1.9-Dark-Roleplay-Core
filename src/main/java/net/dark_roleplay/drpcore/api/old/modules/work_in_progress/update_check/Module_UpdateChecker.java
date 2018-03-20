package net.dark_roleplay.drpcore.api.old.modules.work_in_progress.update_check;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.dark_roleplay.drpcore.api.old.modules.Module;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.ForgeVersion.CheckResult;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

public class Module_UpdateChecker extends Module{

	protected static HashMap<ModContainer, CheckResult> results = new HashMap<ModContainer, CheckResult>();
	
	public Module_UpdateChecker(String name, Module... requiredModules) {
		super(name, requiredModules);
	}
	
	public void postInit(FMLPostInitializationEvent event){
		List<ModContainer> mods = Loader.instance().getActiveModList();
		for(ModContainer mod : mods){
			results.put(mod, ForgeVersion.getResult(mod));
		}
	}
}
