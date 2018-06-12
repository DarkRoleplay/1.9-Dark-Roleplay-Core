package net.dark_roleplay.library_old.images;

public class DataHelper {
	//TODOCUMENT
	
	/**
	 * Used to store a boolean array into an int array
	 * @param data the boolean array to convert
	 * @return
	 */
	public static int[] writeDataToInt(boolean[] data) {
		int[] newData = new int[(int) Math.ceil(data.length / 32D)];
		int current = 0;
		int total = 0;
		for(int i = 0; i < newData.length; i++) {
			for(int j = 0; j < 32; j++){
				current += data[total] ? 1 << (31 - j): 0;
				total++;
				if(total >= data.length)
					break;
			}
			newData[i] = current;
			current = 0;
		}
		
		return newData;
	}
	
	public static boolean[] readDataFromInt(int[] data) {
		boolean[] newData = new boolean[data.length * 32];
		int total = 0;
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < 32; j++){
				newData[total] = ((data[i] << j) & 0x80000000) == 0x80000000;
				total++;
			}
		}
		
		return newData;
	}
	
	public static boolean[] toBoolArray(byte[] data, int dataSize) {
		boolean[] newData = new boolean[data.length * dataSize];
		int total = 0;
		int mask = 1 << dataSize;
		for(int i = 0; i < data.length; i++) {
			for(int j = 0; j < dataSize; j++) {
				newData[total] = ((data[i] >> (dataSize - j - 1)) & 1) == 1;
				total ++;
			}
		}
		return newData;
	}
	
	public static byte[] toByteArray(boolean[] data, int dataSize) {
		byte[] newData = new byte[data.length / dataSize];
		int total = 0;
		int mask = 1;
		for(int i = 0; i < newData.length; i++) {
			byte temp = 0;
			for(int j = 0; j < dataSize; j++) {
				temp |= data[total] ? mask << (dataSize - j - 1) : 0;
				total ++;
				if(total >= data.length)
					continue;
			}
			newData[i] = temp;
			temp = 0;
		}
		return newData;
	}
	
}
