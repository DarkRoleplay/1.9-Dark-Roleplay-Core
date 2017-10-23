package net.dark_roleplay.drpcore.api.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.FMLContainer;
import net.minecraftforge.fml.common.InjectedModContainer;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class DRPItem extends Item{

	public String itemName;
	protected String[] subNames;
	protected String itemFolder;
	
	public DRPItem(String name, int stackSize, String... subNames){
		this(name, null, stackSize, subNames);
	}
	
	public DRPItem(String name, String itemFolder, int stackSize, String... subNames){
		ModContainer mc = Loader.instance().activeModContainer();
        String modid = (mc == null) || ((mc instanceof InjectedModContainer) && (((InjectedModContainer)mc).wrappedContainer instanceof FMLContainer)) ? "minecraft" : mc.getModId().toLowerCase();
		
        this.setUnlocalizedName(name);
		this.setRegistryName(modid + ":" + name);
		
		this.setMaxStackSize(stackSize);
		if(subNames != null){
	        this.setHasSubtypes(true);
		}
		this.itemName = name;
		this.subNames = subNames!=null&&subNames.length>0?subNames:null;
		this.itemFolder = itemFolder;
	
		ItemApi.toRegister.add(this);
	}
	
	public String[] getSubNames(){
		return subNames;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> list){
		if(this.isInCreativeTab(tab)){
			if(getSubNames()!=null){
				for(int i=0;i<getSubNames().length;i++){
					list.add(new ItemStack(this,1,i));
				}
			}
			else{
				list.add(new ItemStack(this));
			}
		}
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack){
		if(getSubNames()!=null){
			String subName = stack.getItemDamage() < getSubNames().length ? "." + getSubNames()[stack.getItemDamage()] : "";
			return this.getUnlocalizedName() + subName;
		}
		return this.getUnlocalizedName();
	}

	public String getItemName() {
		return itemName;
	}

	public String getItemFolder() {
		return itemFolder;
	}
	
	

}