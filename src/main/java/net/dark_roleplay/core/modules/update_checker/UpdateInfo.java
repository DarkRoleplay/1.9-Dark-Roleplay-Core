package net.dark_roleplay.core.modules.update_checker;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.common.ForgeVersion.CheckResult;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.versioning.ComparableVersion;

public class UpdateInfo {

	private String modid;
	private String modname;
	private String currentVersion;
	private String targetVersion;
	private URI updateURL;
	private List<String> versions;
	private List<String> changelog;
	
	public UpdateInfo(ModContainer mod, CheckResult result){
		this.modid = mod.getModId();
		this.modname = mod.getName();
		this.currentVersion = mod.getVersion();
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

	public String getModid() {
		return modid;
	}

	public String getModname() {
		return modname;
	}

	public String getCurrentVersion() {
		return currentVersion;
	}

	public String getTargetVersion() {
		return targetVersion;
	}

	public URI getUpdateURL() {
		return updateURL;
	}

	public List<String> getVersions() {
		return versions;
	}

	public List<String> getChangelog() {
		return changelog;
	}
	
	
}
