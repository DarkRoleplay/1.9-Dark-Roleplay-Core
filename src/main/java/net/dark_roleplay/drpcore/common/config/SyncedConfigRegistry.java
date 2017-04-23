package net.dark_roleplay.drpcore.common.config;

import java.util.HashMap;
import java.util.Iterator;

import net.dark_roleplay.drpcore.common.handler.DRPCorePackets;
import net.dark_roleplay.drpcore.common.network.packets.config.Packet_SyncBoolean;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class SyncedConfigRegistry {

	//Determines whether this Registry is on a Server or on a Client
	private static Side side;
	//Determines if the Client has connected to a Server;
	private static boolean isOnServer = false;
	
	private static HashMap<String, Boolean> clientBooleans = new HashMap<String, Boolean>();
	private static HashMap<String, Boolean> serverBooleans = new HashMap<String, Boolean>();
 
	public static void setSide(Side side2){
		side = side2;
	}
	
	public static void establishServerConnection(){
		isOnServer = true;
	}
	
	public static void initBoolean(String key, boolean value){
		if(side == Side.CLIENT){
			clientBooleans.put(key, value);
		}else{
			serverBooleans.put(key, value);
		}
	}
	
	public static void addBooleanValue(String key, boolean value, boolean serverSide){
		if(serverSide){
			serverBooleans.put(key, value);
		}else{
			clientBooleans.put(key, value);
		}
	}
	
	public static boolean getBooleanValue(String key){
		System.out.println(side + ":" + isOnServer);
		if(side == Side.CLIENT && !isOnServer){
			if(clientBooleans.containsKey(key))
				return clientBooleans.get(key);
		}else{
			if(serverBooleans.containsKey(key))
				return serverBooleans.get(key);
		}
		return false;
	}
	
	public static void disconnectFromServer(){
		isOnServer = false;
		serverBooleans = new HashMap<String, Boolean>();
	}

	public static void sendConfigTo(EntityPlayer player){
		if(side == Side.SERVER){
			Iterator<String> bools = serverBooleans.keySet().iterator();
			while(bools.hasNext()){
				String key = bools.next();
				DRPCorePackets.sendTo(new Packet_SyncBoolean(key, serverBooleans.get(key)), (EntityPlayerMP) player);
			}
		}
	}
}
