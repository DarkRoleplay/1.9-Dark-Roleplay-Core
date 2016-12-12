package net.dark_roleplay.drpcore.common.handler;

import net.dark_roleplay.drpcore.api.items.DRPItem;
import net.dark_roleplay.drpcore.common.DRPCoreInfo;
import net.dark_roleplay.drpcore.common.DarkRoleplayCore;
import net.dark_roleplay.drpcore.common.items.consumable.medicine.MedicineBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.stats.StatList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class DRPCoreItems {
	/**---------- EXAMPLE ITEM ---------**/
	public static MedicineBase MEDICINE_BASE = (MedicineBase) new MedicineBase("Medicine"){
		@Override
		public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
			/*EntityPlayerMP entityplayer = entityLiving instanceof EntityPlayerMP ? (EntityPlayerMP) entityLiving : null;

			entityPlayer.
			
			if (entityplayer == null || !entityplayer.capabilities.isCreativeMode) {
				stack.func_190918_g(1);
			}

			if (!worldIn.isRemote) {
				for (PotionEffect potioneffect : PotionUtils.getEffectsFromStack(stack)) {
					if (potioneffect.getPotion().isInstant()) {
						potioneffect.getPotion().affectEntity(entityplayer, entityplayer, entityLiving,
								potioneffect.getAmplifier(), 1.0D);
					} else {
						entityLiving.addPotionEffect(new PotionEffect(potioneffect));
					}
				}
			}

			if (entityplayer != null) {
				entityplayer.addStat(StatList.getObjectUseStats(this));
			}

			if (entityplayer == null || !entityplayer.capabilities.isCreativeMode) {
				if (stack.func_190926_b()) {
					return new ItemStack(Items.GLASS_BOTTLE);
				}

				if (entityplayer != null) {
					entityplayer.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
				}
			}
*/
			return stack;
		}
		
		
	}.setRegistryName("Medicine");
	
	
	/**---------- A ----------**/
	/**---------- B ----------**/
	/**---------- C ----------**/
	/**---------- D ----------**/
	/**---------- E ----------**/
	/**---------- F ----------**/
	/**---------- G ----------**/
	/**---------- H ----------**/
	/**---------- I ----------**/
	/**---------- J ----------**/
	/**---------- K ----------**/
	/**---------- L ----------**/
	/**---------- M ----------**/
	/**---------- N ----------**/
	/**---------- O ----------**/
	/**---------- P ----------**/
	/**---------- Q ----------**/
	/**---------- R ----------**/
	/**---------- S ----------**/
	/**---------- T ----------**/
	/**---------- U ----------**/
	/**---------- V ----------**/
	/**---------- W ----------**/
	/**---------- X ----------**/
	/**---------- Y ----------**/
	/**---------- Z ----------**/
	
	public static final void preInit(FMLPreInitializationEvent event) {
		/**---------- EXAMPLE ITEM ---------**/
		if(DRPCoreConfigs.ENABLE_DEBUG_ITEMS){
			//Register here all Debug Item
			registerItem(DRPCoreInfo.MODID, MEDICINE_BASE);
		}
	}
	
	public static final void init(FMLInitializationEvent event) {}

	public static final void postInit(FMLPostInitializationEvent event) {}

	public static final void registerItem(String modid,DRPItem item){
		GameRegistry.register(item);

		DarkRoleplayCore.proxy.registerItemMesh(modid, item);
	}
}
