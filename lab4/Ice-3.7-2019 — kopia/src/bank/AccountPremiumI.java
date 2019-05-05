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
	public DualCurrency getLoan(Currency currency, int amount, int time, Current current) throws ErrorLoan {
		return null;
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
