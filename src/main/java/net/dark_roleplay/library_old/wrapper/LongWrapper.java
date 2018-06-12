package net.dark_roleplay.library_old.wrapper;

public class LongWrapper {

	private long value = (long) 0;
	
	private long min = Long.MIN_VALUE;
	private long max = Long.MAX_VALUE;
	
	public LongWrapper() {}
	
	public LongWrapper(long value) {
		this.value = value;
	}
	
	public LongWrapper(long value, long min, long max) {
		this.value = value;
		this.min = min;
		this.max = max;
	}
	
	/**
	 * Returns the value of this wrapper
	 * @return boolean
	 */
	public long getValue() {
		return value;
	}

	/**
	 * Sets the value of this wrapper
	 * @param value
	 */
	public void setValue(long value) {
		if(value > min && value < max) {
			this.value = value;
			this.markDirty();
		}
	}

	/**
	 * Returns the minimum value of this wrapper
	 * @return long
	 */
	public long getMin() {
		return min;
	}

	/**
	 * Sets the minimum value of this wrapper
	 * @param min
	 */
	public void setMin(long min) {
		this.min = min;
	}

	/**
	 * Returns the maximum value of this wrapper
	 * @return long
	 */
	public long getMax() {
		return max;
	}

	/**
	 * Sets the maximum value of this wrapper
	 * @param max
	 */
	public void setMax(long max) {
		this.max = max;
	}
	
	/**
	 * Overwrite this method to do an action if the value is changed
	 */
	public void markDirty() {}
}
