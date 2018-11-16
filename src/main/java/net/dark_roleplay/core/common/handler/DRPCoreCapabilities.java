package net.dark_roleplay.core.common.handler;

import net.dark_roleplay.core.testing.expanded_inventory.ExpandedInventoryHandler;
import net.dark_roleplay.core.testing.expanded_inventory.IExpandedInventory;
import net.dark_roleplay.core.testing.skills.SkillHandler;
import net.dark_roleplay.core.testing.skills.SkillStorage;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.items.IItemHandlerModifiable;

public class DRPCoreCapabilities {


	/**
	 * TODO Move to Interface
	 */
	@CapabilityInject(SkillHandler.class)
	public static final Capability<SkillHandler> SKILL_HANDLER = null;

	@CapabilityInject(IExpandedInventory.class)
	public static final Capability<IExpandedInventory> EXPANDED_INVENTORY = null;


	public static void preInit(){
	}

	public static final void init(FMLInitializationEvent event) {
		CapabilityManager.INSTANCE.register(SkillHandler.class, new SkillStorage(), SkillHandler::new);
		CapabilityManager.INSTANCE.register(IExpandedInventory.class, new Capability.IStorage<IExpandedInventory>()
        {
            @Override
            public NBTBase writeNBT(Capability<IExpandedInventory> capability, IExpandedInventory instance, EnumFacing side)
            {
                NBTTagList nbtTagList = new NBTTagList();
                int size = instance.getSlots();
                for (int i = 0; i < size; i++)
                {
                    ItemStack stack = instance.getStackInSlot(i);
                    if (!stack.isEmpty())
                    {
                        NBTTagCompound itemTag = new NBTTagCompound();
                        itemTag.setInteger("Slot", i);
                        stack.writeToNBT(itemTag);
                        nbtTagList.appendTag(itemTag);
                    }
                }
                return nbtTagList;
            }

            @Override
            public void readNBT(Capability<IExpandedInventory> capability, IExpandedInventory instance, EnumFacing side, NBTBase base)
            {
                if (!(instance instanceof IItemHandlerModifiable))
                    throw new RuntimeException("IItemHandler instance does not implement IItemHandlerModifiable");
                IItemHandlerModifiable itemHandlerModifiable = (IItemHandlerModifiable) instance;
                NBTTagList tagList = (NBTTagList) base;
                for (int i = 0; i < tagList.tagCount(); i++)
                {
                    NBTTagCompound itemTags = tagList.getCompoundTagAt(i);
                    int j = itemTags.getInteger("Slot");

                    if (j >= 0 && j < instance.getSlots())
                    {
                        itemHandlerModifiable.setStackInSlot(j, new ItemStack(itemTags));
                    }
                }
            }
        }, ExpandedInventoryHandler::new);
	}
}
