package bank;

import com.zeroc.Ice.Current;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;

import Banks.AccountManager;
import Banks.AccountType;
import Banks.ErrorNewUser;
import Banks.NewAccountDetails;

public class AccountManagerI implements AccountManager {
	private final int minIncomeForPremium = 1000;
	private ObjectAdapter adapter;
	CurrencyTranslator currencyTranslator;
	
	public AccountManagerI(ObjectAdapter adapter, CurrencyTranslator currencyTranslator) {
		this.adapter = adapter;
		this.currencyTranslator = currencyTranslator;
	}
	
	@Override
	public NewAccountDetails createNewAccount(int monthIncome, Current current) throws ErrorNewUser{
		String name, surname, PESEL;
		System.out.println("AccountManagerI :: "  + monthIncome + "\t::  " + current.ctx.toString());
		name = current.ctx.get("name");
		surname = current.ctx.get("surname");
		PESEL = current.ctx.get("PESEL");			
		if(name==null || surname==null || PESEL==null)
			throw new ErrorNewUser();
		
		AccountType accType = monthIncome>minIncomeForPremium ? AccountType.PREMIUM : AccountType.STANDARD;
		String key = name + PESEL;
		try {
			if(accType == AccountType.PREMIUM) {
				adapter.add(new AccountPremiumI(name, surname, PESEL, key, currencyTranslator), new Identity(PESEL, "premium"));
			} 
			else if(accType == AccountType.STANDARD) {
				adapter.add(new AccountI(name, surname, PESEL, key, currencyTranslator), new Identity(PESEL, "standard"));
			}
			else {
				throw new Error("Magic");
			}
		} catch (com.zeroc.Ice.AlreadyRegisteredException e) {
			System.out.println("User " + PESEL + " already registered");
			throw new ErrorNewUser();
		}
		return new NewAccountDetails(accType, key);
	}
}
