package net.dark_roleplay.core.modules.update_checker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import net.dark_roleplay.core.api.old.modules.Module;
import net.dark_roleplay.core.modules.Modules;
import net.minecraftforge.common.ForgeVersion;
import net.minecraftforge.common.ForgeVersion.CheckResult;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

public class Module_UpdateChecker extends Module{

	public static final List<UpdateInfo> mods = new ArrayList<UpdateInfo>();
	
	public Module_UpdateChecker(String name, Module... requiredModules) {
		super(name, requiredModules);
	}
	
	public void postInit(FMLPostInitializationEvent event){
		List<ModContainer> mods = Loader.instance().getActiveModList();
		for(ModContainer mod : mods){
			CheckResult res = ForgeVersion.getResult(mod);
			if(res.status == ForgeVersion.Status.OUTDATED) {
				this.mods.add(new UpdateInfo(mod, res));
			}
		}
	}
	
}
