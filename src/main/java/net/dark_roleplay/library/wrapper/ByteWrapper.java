package net.dark_roleplay.library.wrapper;

public class ByteWrapper {

	private byte value = (byte) 0;
	
	private byte min = Byte.MIN_VALUE;
	private byte max = Byte.MAX_VALUE;
	
	public ByteWrapper() {}
	
	public ByteWrapper(byte value) {
		this.value = value;
	}
	
	public ByteWrapper(byte value, byte min, byte max) {
		this.value = value;
		this.min = min;
		this.max = max;
	}
	
	/**
	 * Returns the value of this wrapper
	 * @return boolean
	 */
	public byte getValue() {
		return value;
	}

	/**
	 * Sets the value of this wrapper
	 * @param value
	 */
	public void setValue(byte value) {
		if(value > min && value < max) {
			this.value = value;
			this.markDirty();
		}
	}

	/**
	 * Returns the minimum value of this wrapper
	 * @return byte
	 */
	public byte getMin() {
		return min;
	}

	/**
	 * Sets the minimum value of this wrapper
	 * @param min
	 */
	public void setMin(byte min) {
		this.min = min;
	}

	/**
	 * Returns the maximum value of this wrapper
	 * @return byte
	 */
	public byte getMax() {
		return max;
	}

	/**
	 * Sets the maximum value of this wrapper
	 * @param max
	 */
	public void setMax(byte max) {
		this.max = max;
	}

	/**
	 * Overwrite this method to do an action if the value is changed
	 */
	public void markDirty() {}
}
