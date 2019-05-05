package bank;

import java.util.HashMap;
import java.util.Map;

import Banks.Currency;
import Banks.localCurrency;

public class CurrencyTranslator implements localCurrency{
	private Map<Currency, Integer> currencyMap = new HashMap<>();
	// 1 * local_curr = x * key_curr 
	
	int v= 89;
	public CurrencyTranslator() {
		for(Currency c : Currency.values()) {
			currencyMap.put(c, v);
			v+=48;
		}
	}
	
	public void print() {
		System.out.println(currencyMap.toString());
	}
	
	public int exchange(Currency from, Currency to, int ammount) {
		int amountInLocal = localCurrency.value==from ? ammount : ammount * currencyMap.get(from);
		int amountOut = localCurrency.value==to ? amountInLocal : amountInLocal / currencyMap.get(to);
		System.out.println(String.format("exchange\tfrom %s, to %s amount %d, in local: %d, in out %d", from.toString(), to.toString(), ammount, amountInLocal, amountOut));
		return amountOut;
			
	}
}
