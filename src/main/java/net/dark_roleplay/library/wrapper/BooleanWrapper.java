package net.dark_roleplay.library.wrapper;

public class BooleanWrapper {

	private boolean value = false;

	public BooleanWrapper() {}
	
	public BooleanWrapper(boolean value) {
		this.value = value;
	}
	
	/**
	 * Returns the value of this wrapper
	 * @return boolean
	 */
	public boolean getValue() {
		return value;
	}

	/**
	 * Sets the value of this wrapper
	 * @param value
	 */
	public void setValue(boolean value) {
		this.value = value;
		this.markDirty();
	}

	/**
	 * Overwrite this method to do an action if the value is changed
	 */
	public void markDirty() {}
	
}
