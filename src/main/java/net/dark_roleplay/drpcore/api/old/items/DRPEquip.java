package net.dark_roleplay.drpcore.api.old.items;

public class DRPEquip extends DRPItem{

	private TYPE type;
	
	//TODO ADD DRPEQUI INVENTORY
	/**
	 * Used to define Storage Space for following types:
	 * TYPE_AMMO_STORAGE, TYPE_MONEY_STORAGE, TYPE_GENERAL_STORAGE
	 * unused for
	 * TYPE_BELT, TYPE_RING, TYPE_NECKLACE
	 * however will still be used when DRPEquip_Inv is opened with this Item
	 */
	private int storageSpace;
	
	public DRPEquip(String name, TYPE type){
		this(name, null, type);
	}
	
	public DRPEquip(String name, String modelFolder, TYPE type){
		super(name, modelFolder, 1);
		this.type = type;
	}
	
	/** -------------------------------------------------- GETTERS -------------------------------------------------- **/
	
	public TYPE getType() {return type;}

	public int getStorageSpace() {return storageSpace;}

	/** -------------------------------------------------- SETTERS -------------------------------------------------- **/
	
	public void setType(TYPE type) {this.type = type;}

	public void setStorageSpace(int storageSpace) {this.storageSpace = storageSpace;}
	
	public enum TYPE{
		
		TYPE_AMMO_STORAGE,
		TYPE_MONEY_STORAGE,
		TYPE_GENERAL_STORAGE,
		TYPE_BELT,
		TYPE_RING,
		TYPE_NECKLACE;

	}
}
