package net.drpcore.common.util.currency;

import java.util.ArrayList;
import java.util.HashMap;

import net.drpcore.common.items.templates.CurrencyBase;
import net.minecraft.item.ItemStack;

public class CurrencyManager {

	HashMap<String,CurrencyBase[]> currencys = new HashMap<String,CurrencyBase[]>();
	
	public void addCurrencyType(String CurrencyName, CurrencyBase[] currencyItems){
		this.currencys.put(CurrencyName, currencyItems);
	}
	
	public ArrayList<ItemStack> exchange(String currencyName, int toPayValue, int payedValue){
		
		CurrencyBase[] unsortedPossibleExchange = currencys.get(currencyName);
		
		CurrencyBase[] sorted = new CurrencyBase[currencys.get(currencyName).length];
		
		ArrayList<Integer> sortedKeys = new ArrayList<Integer>();
		
		CurrencyBase currentHighestCurrency = null;
		
		int highestCurrency;
		
		while(sortedKeys.size() < currencys.get(currencyName).length){
			for(int i = 0; i < unsortedPossibleExchange.length; i ++){
			
				CurrencyBase currency = unsortedPossibleExchange[i];
	
				if(currentHighestCurrency != null){
					if(currency.getValue() > currentHighestCurrency.getValue()){
						
						currentHighestCurrency = currency;
						highestCurrency = i;
						
					}
				}else{
					
					currentHighestCurrency = currency;
					highestCurrency = i;
					
				}
			}
		}
		
		int remainingValue = toPayValue - payedValue;
				
		int[] currencyValues = new int[sorted.length];
		
		int i = 0;

		for(CurrencyBase currency: sorted){
			currencyValues[i] = currency.getValue();
			i++;
		}

		return null;
	}
}
