package net.dark_roleplay.drpcore.api.items;

public class DRPEquip extends DRPItem{

	private DRPEquip_TYPE type;
	
	//TODO ADD DRPEQUI INVENTORY
	/**
	 * Used to define Storage Space for following types:
	 * TYPE_AMMO_STORAGE, TYPE_MONEY_STORAGE, TYPE_GENERAL_STORAGE
	 * unused for
	 * TYPE_BELT, TYPE_RING, TYPE_NECKLACE
	 * however will still be used when DRPEquip_Inv is opened with this Item
	 */
	private int storageSpace;
	
	public DRPEquip(String modelFolder, DRPEquip_TYPE type){
		super(modelFolder);
		this.type = type;
	}
	
	/** -------------------------------------------------- GETTERS -------------------------------------------------- **/
	
	public DRPEquip_TYPE getType() {return type;}

	public int getStorageSpace() {return storageSpace;}

	/** -------------------------------------------------- SETTERS -------------------------------------------------- **/
	
	public void setType(DRPEquip_TYPE type) {this.type = type;}

	public void setStorageSpace(int storageSpace) {this.storageSpace = storageSpace;}
	
	protected enum DRPEquip_TYPE{
		
		TYPE_AMMO_STORAGE,
		TYPE_MONEY_STORAGE,
		TYPE_GENERAL_STORAGE,
		TYPE_BELT,
		TYPE_RING,
		TYPE_NECKLACE;
		
		
		DRPEquip_TYPE(){
			
		}
	}
}
