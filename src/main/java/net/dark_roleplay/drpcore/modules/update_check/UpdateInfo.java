package net.dark_roleplay.drpcore.modules.update_check;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.ForgeVersion.CheckResult;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.versioning.ComparableVersion;

public class UpdateInfo {

	private String modid;
	private String modname;
	private String currentVersion;
	private String targetVersion;
	private URI updateURL;
	private List<String> versions;
	private List<String> changelog;
	
	public UpdateInfo(Mod mod, CheckResult result){
		this.modid = mod.modid();
		this.modname = mod.name();
		this.currentVersion = mod.version();
		this.targetVersion = result.target.toString();
		this.versions = new ArrayList<String>();
		this.changelog = new ArrayList<String>();
		for(ComparableVersion key : result.changes.keySet()){
			String value = result.changes.get(key);
			versions.add(key.toString());
			changelog.add(value);
		}
		
		try {
			this.updateURL = new URI(result.url);
		} catch (URISyntaxException e) {
			this.updateURL = null;
		}
	}
	
}
