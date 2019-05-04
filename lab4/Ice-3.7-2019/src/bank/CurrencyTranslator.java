package bank;

import java.util.HashMap;
import java.util.Map;

import Banks.Currency;
import Banks.localCurrency;

public class CurrencyTranslator implements localCurrency{
	private Map<Currency, Integer> currencyMap = new HashMap<>();
	// 1 * local_curr = x * key_curr 
	
	int v= 100;
	public CurrencyTranslator() {
		for(Currency c : Currency.values()) {
			currencyMap.put(c, v);
			v+=150;
		}
	}
	
	public void print() {
		System.out.println(currencyMap.toString());
	}
	
	public int exchange(Currency from, Currency to, int ammount) {
			int ammountInLocal = localCurrency.value==from ? ammount : ammount * currencyMap.get(from);
			return localCurrency.value==to ? ammountInLocal : ammountInLocal / currencyMap.get(to);
			
	}
}
