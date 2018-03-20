package net.dark_roleplay.drpcore.server.permission;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

import com.mojang.authlib.GameProfile;

import net.dark_roleplay.drpcore.common.References;
import net.minecraftforge.server.permission.DefaultPermissionLevel;
import net.minecraftforge.server.permission.IPermissionHandler;
import net.minecraftforge.server.permission.context.IContext;

public class DRPPermissionHandler implements IPermissionHandler{

	private static final HashMap<String, String> DESCRIPTIONS = new HashMap<String, String>();
	private static final HashSet<String> NODES = new HashSet<String>(); 
	
	@Override
	public void registerNode(String node, DefaultPermissionLevel level, String desc) {
		NODES.add(node);
		if(desc != null && !(desc.isEmpty()))
			DESCRIPTIONS.put(node, desc);
	}

	@Override
	public Collection<String> getRegisteredNodes() {
		 return Collections.unmodifiableSet(NODES);
	}

	@Override
	public String getNodeDescription(String node) {
		String desc = DESCRIPTIONS.get(node);
		return desc == null ? "" : desc;
	}

	@Override
	public boolean hasPermission(GameProfile profile, String node, IContext context) {
		String uuid = profile.getId().toString();
		
		if(new File(References.FOLDER_PERMISSIONS_USERS.getPath() + "/" + uuid + ".json").exists()) {
			return true;
		}
		
		return false;
	}
}
