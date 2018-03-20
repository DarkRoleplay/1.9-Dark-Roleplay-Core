package net.dark_roleplay.library.wrapper;

public class ShortWrapper {

	private short value = (short) 0;
	
	private short min = Short.MIN_VALUE;
	private short max = Short.MAX_VALUE;
	
	public ShortWrapper() {}
	
	public ShortWrapper(short value) {
		this.value = value;
	}
	
	public ShortWrapper(short value, short min, short max) {
		this.value = value;
		this.min = min;
		this.max = max;
	}
	
	/**
	 * Returns the value of this wrapper
	 * @return boolean
	 */
	public short getValue() {
		return value;
	}

	/**
	 * Sets the value of this wrapper
	 * @param value
	 */
	public void setValue(short value) {
		if(value > min && value < max) {
			this.value = value;
			this.markDirty();
		}
	}

	/**
	 * Returns the minimum value of this wrapper
	 * @return short
	 */
	public short getMin() {
		return min;
	}

	/**
	 * Sets the minimum value of this wrapper
	 * @param min
	 */
	public void setMin(short min) {
		this.min = min;
	}

	/**
	 * Returns the maximum value of this wrapper
	 * @return short
	 */
	public short getMax() {
		return max;
	}

	/**
	 * Sets the maximum value of this wrapper
	 * @param max
	 */
	public void setMax(short max) {
		this.max = max;
	}
	
	/**
	 * Overwrite this method to do an action if the value is changed
	 */
	public void markDirty() {}
}
