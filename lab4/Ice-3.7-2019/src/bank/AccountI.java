package bank;

import com.zeroc.Ice.Current;

import Banks.Account;
import Banks.AccountPremium;
import Banks.AccountStandard;
import Banks.AccountType;
import Banks.Currency;
import Banks.ErrorLoan;
import Banks.DualCurrency;
import Banks.localCurrency;

public class AccountI implements Account, localCurrency {
	@SuppressWarnings("unused")
	private String name, surname, PESEL, key; 
	CurrencyTranslator currencyTranslator;
	

	public AccountI(String name, String surname, String PESEL, String key, CurrencyTranslator currencyTranslator) {
		this.name = name;
		this.surname = surname;
		this.PESEL = PESEL;
		this.currencyTranslator = currencyTranslator;
		this.key = key;
	}

	@Override
	public DualCurrency getLoan(Currency currency, int amount, int time, Current current) throws ErrorLoan {
		throw new ErrorLoan();
	}

	@Override
	public int getStatus(Current current) {
		// TODO Auto-generated method stub
		return 0;
	}

}
