package net.dark_roleplay.library.wrapper;

public class IntegerWrapper {

	private int value = (int) 0;
	
	private int min = Integer.MIN_VALUE;
	private int max = Integer.MAX_VALUE;
	
	public IntegerWrapper() {}
	
	public IntegerWrapper(int value) {
		this.value = value;
	}
	
	public IntegerWrapper(int value, int min, int max) {
		this.value = value;
		this.min = min;
		this.max = max;
	}
	
	/**
	 * Returns the value of this wrapper
	 * @return boolean
	 */
	public int getValue() {
		return value;
	}

	/**
	 * Sets the value of this wrapper
	 * @param value
	 */
	public void setValue(int value) {
		if(value > min && value < max) {
			this.value = value;
			this.markDirty();
		}
	}

	/**
	 * Returns the minimum value of this wrapper
	 * @return int
	 */
	public int getMin() {
		return min;
	}

	/**
	 * Sets the minimum value of this wrapper
	 * @param min
	 */
	public void setMin(int min) {
		this.min = min;
	}

	/**
	 * Returns the maximum value of this wrapper
	 * @return int
	 */
	public int getMax() {
		return max;
	}

	/**
	 * Sets the maximum value of this wrapper
	 * @param max
	 */
	public void setMax(int max) {
		this.max = max;
	}
	
	/**
	 * Overwrite this method to do an action if the value is changed
	 */
	public void markDirty() {}
}
