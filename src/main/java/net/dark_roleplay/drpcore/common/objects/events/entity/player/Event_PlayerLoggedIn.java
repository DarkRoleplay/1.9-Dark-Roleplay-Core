package net.dark_roleplay.drpcore.common.objects.events.entity.player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.dark_roleplay.drpcore.common.capabilities.player.crafting.IRecipeController;
import net.dark_roleplay.drpcore.common.capabilities.player.skill.ISkillController;
import net.dark_roleplay.drpcore.common.config.SyncedConfigRegistry;
import net.dark_roleplay.drpcore.common.handler.DRPCoreCapabilities;
import net.dark_roleplay.drpcore.common.handler.DRPCorePackets;
import net.dark_roleplay.drpcore.common.network.packets.crafting.SyncPacket_PlayerRecipeState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Event_PlayerLoggedIn {

	public static List<EntityPlayerMP> premiums = new ArrayList<EntityPlayerMP>();
	
	@SubscribeEvent
	public void handleEvent(EntityJoinWorldEvent event) {
		
		if(event.getEntity() instanceof EntityPlayerMP) {
			EntityPlayerMP player = (EntityPlayerMP) event.getEntity();
//			IRecipeController rpC = player.getCapability(DRPCoreCapabilities.DRPCORE_RECIPE_CONTROLLER, null);
//			ISkillController slC = player.getCapability(DRPCoreCapabilities.DRPCORE_SKILL_CONTROLLER, null);
//			
//			for(String recipe : rpC.getUnlockedRecipes()){
//				DRPCorePackets.sendTo(new SyncPacket_PlayerRecipeState(recipe,0,0F), player);
//			}
//			for(String recipe : rpC.getLockedRecipes()){
//				DRPCorePackets.sendTo(new SyncPacket_PlayerRecipeState(recipe,1,0F), player);
//			}
//			Map<String,Float> progressed = rpC.getProgressedRecipes();
//			for(String recipe : progressed.keySet()){
//				DRPCorePackets.sendTo(new SyncPacket_PlayerRecipeState(recipe,2,progressed.get(recipe)), player);
//			}
			
//			for(SkillPointData data : slC.getSkillPoints()){
//				DRPCorePackets.sendTo(new SyncPacket_SkillPoint(data.getPoint().getRegistryName(), data.getAmount(), data.getLevel(), data.getXP()) , player);
//			}
//			
//			for(Skill skill : slC.getUnlockedSkills()){
//				DRPCorePackets.sendTo(new SyncPacket_Skill(skill.getRegistryName()), player);
//			}
			
			SyncedConfigRegistry.sendConfigTo((EntityPlayer) event.getEntity());
			
			
//			try {
//				InputStream input = new URL("http://dark-roleplay.bplaced.net/premium_check.php?uuid=" + player.getUniqueID().toString()).openStream();
//				byte[] value = new byte[4];
//				input.read(value, 0, 4);
//				
//				String s = new String(value);
//				if(s.equals("true")){
//					premiums.add(player);
//				}
//			} catch (MalformedURLException e) {
//				e.printStackTrace();
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
	}       
}
