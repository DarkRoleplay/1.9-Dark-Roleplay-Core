package net.dark_roleplay.drpcore.common.partys;

import java.util.HashMap;
import java.util.UUID;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class Partys {

	@SideOnly(Side.SERVER)
	static HashMap<String, UUID[]> partys = new HashMap<String, UUID[]>();
	
	@SideOnly(Side.SERVER)
	public static boolean createParty(String name, UUID player){
		if(partys.containsKey(name)) return false;
		UUID[] temp = new UUID[6];
		temp[0] = player;
		partys.put(name, temp);
		return true;
	}
	
	@SideOnly(Side.SERVER)
	public static boolean joinParty(String partyName, UUID player){
		if(!partys.containsKey(partyName)) return false;
		UUID[] tmp = partys.get(partyName);
		for(int i = 0; i < tmp.length; i++){
			if(tmp[i] == null){
				tmp[i] = player;
				return true;
			}
		}
		return false;
	}
	
	
	@SideOnly(Side.CLIENT)
	static String partyName;
	
	@SideOnly(Side.CLIENT)
	static String partyMember;
	static UUID[] partyMembers = new UUID[6];
	
}
