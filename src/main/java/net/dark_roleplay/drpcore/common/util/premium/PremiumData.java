package net.dark_roleplay.drpcore.common.util.premium;

import java.util.ArrayList;
import java.util.List;

public class PremiumData {
	
	private int premiumPoints;
	
	private boolean[] gadgets;
	
	public PremiumData(int premiumPoints, long... gadgets){
		this.premiumPoints = premiumPoints;
		this.gadgets = new boolean[64 * gadgets.length];
		for(int i = 0; i < gadgets.length * 64; i++){
			this.gadgets[i] = (gadgets[i/64] & (1 << (i % 64))) != 0;
		}
	}

	public int getPremiumPoints() {
		return premiumPoints;
	}

	public boolean[] getGadgets() {
		return gadgets;
	}
	
	
}
