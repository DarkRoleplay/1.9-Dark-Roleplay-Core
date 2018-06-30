package net.dark_roleplay.core.client.events.player;

import java.util.List;

import net.dark_roleplay.core.api.old.items.weapons.IExtendedRange;
import net.dark_roleplay.core.common.References;
import net.dark_roleplay.core.common.handler.DRPCorePackets;
import net.dark_roleplay.core.common.objects.packets.weapons.Packet_ExtendedRangeAttack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.event.MouseEvent;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = References.MODID)
public class Event_Mouse {

	/**
	 * Extended Range Event
	 */
	@SubscribeEvent(priority = EventPriority.NORMAL, receiveCanceled = true)
	public static void onEvent(MouseEvent event) {
		if (event.getButton() == 0 && event.isButtonstate()) {

			Minecraft mc =  FMLClientHandler.instance().getClient();
			EntityPlayer player = mc.player;
			if (player != null) {

				ItemStack itemstack = player.getHeldItemMainhand();
				IExtendedRange ranged = null;
				if (itemstack != null) {
					if (itemstack.getItem() instanceof IExtendedRange) {
						ranged = (IExtendedRange) itemstack.getItem();
					}

					if (ranged != null) {

						float range = ranged.getRange();
						RayTraceResult res = getMouseOverExtended(range);
						
						if (res != null) {
							if (res.entityHit != null && res.entityHit.hurtResistantTime < 1) {
								if (res.entityHit != player) {
									DRPCorePackets.sendToServer(new Packet_ExtendedRangeAttack(res.entityHit.getEntityId()));
								}
							}
						}
					}
				}
			}
		}
	}

	public static RayTraceResult getMouseOverExtended(float dist) {
		Minecraft mc = FMLClientHandler.instance().getClient();
		Entity theRenderViewEntity = mc.getRenderViewEntity();
		AxisAlignedBB theViewBoundingBox = new AxisAlignedBB(theRenderViewEntity.posX - 0.5D, theRenderViewEntity.posY - 0.0D, theRenderViewEntity.posZ - 0.5D, theRenderViewEntity.posX + 0.5D, theRenderViewEntity.posY + 1.5D, theRenderViewEntity.posZ + 0.5D);
		RayTraceResult returnMOP = null;
		if (mc.world != null) {

			returnMOP = theRenderViewEntity.rayTrace(dist, 0);
			double calcdist = dist;
			Vec3d pos = theRenderViewEntity.getPositionEyes(0);
			if (returnMOP != null) {
				calcdist = returnMOP.hitVec.distanceTo(pos);
			}

			Vec3d lookvec = theRenderViewEntity.getLook(0);
			Vec3d var8 = pos.addVector(lookvec.x * dist, lookvec.y * dist, lookvec.z * dist);
			Entity pointedEntity = null;
			float var9 = 0.2F;
			
			
			List<Entity> list = mc.world.getEntitiesWithinAABBExcludingEntity(theRenderViewEntity,
					theViewBoundingBox.offset(lookvec.x * 4.2, lookvec.y * 4.2, lookvec.z * 4.2)
					.expand(var9, var9, var9));
			float offset = 4.4f;
			while(list.size() == 0 && offset < dist) {
				list = mc.world.getEntitiesWithinAABBExcludingEntity(theRenderViewEntity,
						theViewBoundingBox.offset(lookvec.x * offset, lookvec.y * offset, lookvec.z * offset)
						.expand(var9, var9, var9));
				offset += 0.1F;
			}
			double d = calcdist;


			for (Entity entity : list) {
				if (entity.canBeCollidedWith()) {
					float bordersize = entity.getCollisionBorderSize();
					AxisAlignedBB aabb = new AxisAlignedBB(entity.posX - entity.width / 2, entity.posY,
							entity.posZ - entity.width / 2, entity.posX + entity.width / 2, entity.posY + entity.height,
							entity.posZ + entity.width / 2);
					aabb.expand(bordersize, bordersize, bordersize);
					RayTraceResult mop0 = aabb.calculateIntercept(pos, var8);

					if (aabb.contains(pos)) {
						if (0.0D < d || d == 0.0D) {
							pointedEntity = entity;
							d = 0.0D;
						}
					} else if (mop0 != null) {
						double d1 = pos.distanceTo(mop0.hitVec);

						if (d1 < d || d == 0.0D) {
							pointedEntity = entity;
							d = d1;
						}
					}
				}
			}

			if (pointedEntity != null && (d < calcdist || returnMOP == null)) {
				returnMOP = new RayTraceResult(pointedEntity);
			}
		}
		return returnMOP;
	}

}
