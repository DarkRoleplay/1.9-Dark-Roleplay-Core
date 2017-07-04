package net.dark_roleplay.drpcore.api.items;

public class DRPLockable extends DRPItem{

	public DRPLockable(String name, int stackSize, String... subNames){
		this(name, null, stackSize, subNames);
	}
		
	public DRPLockable(String name, String itemFolder, int stackSize, String[] subNames) {
		super(name, itemFolder, stackSize, subNames);
	}
	

	
	private enum LockableType{
		LOCK,
		KEY;
	}

}
