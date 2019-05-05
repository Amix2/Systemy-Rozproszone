package bank;

import com.zeroc.Ice.Current;

import Banks.Account;
import Banks.AccountPremium;
import Banks.AccountType;
import Banks.Currency;
import Banks.ErrorLoan;
import Banks.DualCurrency;
import Banks.localCurrency;

public class AccountPremiumI extends AccountI implements AccountPremium, localCurrency {
	

	public AccountPremiumI(String name, String surname, String PESEL, String key, CurrencyTranslator currencyTranslator) {
		super(name, surname, PESEL, key, currencyTranslator);

	}

	@Override
	public DualCurrency getLoan(Currency currency, int amountGlobal, int time, Current current) throws ErrorLoan {
		if(!current.ctx.get("key").equals(this.key) || !current.ctx.get("PESEL").equals(this.PESEL)) {
			System.out.println(String.format("loan wrong key %s:%s or PESEL %s:%s", current.ctx.get("key"), this.key, current.ctx.get("PESEL"), this.PESEL));
			throw new ErrorLoan();
		}
		if(!currencyTranslator.currList.contains(currency)) {
			System.out.println("Wrong currency " + currency);
			throw new ErrorLoan();
		}
		int amountLocal = currencyTranslator.exchange(currency, localCurrency.value, amountGlobal);
		
		return new DualCurrency(currency, amountGlobal, amountLocal);
	}

}
