package net.drpcore.common.items.templates;

public enum EnumDRPEquipType {

	NECKLACE(false),
	RING(false),
	RING_LEFT(false),
	RING_RIGTH(false),
	BELT(false),
	BACKPACK(true),
	PURSE(true),
	QUIVER(true);

	boolean hasStorage = false;

	EnumDRPEquipType(boolean hasStorage) {
		this.hasStorage = hasStorage;
	}
	
}
