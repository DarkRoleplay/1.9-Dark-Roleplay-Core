package net.dark_roleplay.drpcore.common.containers;

import javax.annotation.Nullable;

import net.dark_roleplay.drpcore.client.gui.inventory.Slot_Armor;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class Container_ExtendedInventory extends Container{

	private final EntityPlayer player;
    private static final EntityEquipmentSlot[] VALID_EQUIPMENT_SLOTS = new EntityEquipmentSlot[] {EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET};
	
    public Container_ExtendedInventory(InventoryPlayer playerInventory, EntityPlayer player , IItemHandler extendedInv){
        this.player = player;
 
        int slot = 0;
        
        for(; slot < 9;slot++){
        	this.addSlotToContainer(new Slot(playerInventory, slot, 8 + (slot * 18), 141));
        }
        
        for(; slot < 36;slot++){
        	this.addSlotToContainer(new Slot(playerInventory, slot, 8 + ((slot % 9)  * 18), 65 + ((slot / 9) * 18)));
        }
        
        for(; slot < 40; slot++){
            final EntityEquipmentSlot entityequipmentslot = VALID_EQUIPMENT_SLOTS[(slot - 36)];
        	this.addSlotToContainer(new Slot_Armor(playerInventory, slot, 8, 9 + ((slot - 36) * (18)), entityequipmentslot, player));
        }
        
        this.addSlotToContainer(new Slot(playerInventory, slot++, 116, 63));
        
        for(; slot < 45; slot++){
        	final EntityEquipmentSlot entityequipmentslot = VALID_EQUIPMENT_SLOTS[(slot - 41)];
         	this.addSlotToContainer(new SlotItemHandler(extendedInv, slot, 80, 9 + ((slot - 41) * (18))));
        }
        
    }

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return true;
	}
	
}
