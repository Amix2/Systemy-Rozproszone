package bank;

import com.zeroc.Ice.Current;

import Banks.Account;
import Banks.AccountPremium;
import Banks.AccountType;
import Banks.Currency;
import Banks.ErrorLoan;
import Banks.DualCurrency;
import Banks.localCurrency;

public class AccountPremiumI implements AccountPremium, localCurrency {
	@SuppressWarnings("unused")
	private String name, surname, PESEL, key; 
	CurrencyTranslator currencyTranslator;
	

	public AccountPremiumI(String name, String surname, String PESEL, String key, CurrencyTranslator currencyTranslator) {
		this.name = name;
		this.surname = surname;
		this.PESEL = PESEL;
		this.currencyTranslator = currencyTranslator;
		this.key = key;
	}

	@Override
	public DualCurrency getLoan(Currency currency, int amountGlobal, int time, Current current) throws ErrorLoan {
		if(!current.ctx.get("key").equals(this.key)) {
			System.out.println("wrong key " + current.ctx.get("key") + " : " + this.key);
			throw new ErrorLoan();
		}
		int amountLocal = currencyTranslator.exchange(currency, localCurrency.value, amountGlobal);
		
		return new DualCurrency(currency, amountGlobal, amountLocal);
	}

	@Override
	public int getStatus(Current current) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public String getKey(Current current) {
		return key;
	}

}
