package bank;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Current;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;

import Banks.Account;
import Banks.AccountManager;
import Banks.AccountPrx;
import Banks.AccountType;
import Banks.ErrorConnectUser;
import Banks.ErrorNewUser;
import Banks.NewAccountDetails;

public class AccountManagerI implements AccountManager {
	private final int minIncomeForPremium = 1000;
	private ObjectAdapter adapter;
	private Communicator communicator;
	CurrencyTranslator currencyTranslator;
	
	public AccountManagerI(ObjectAdapter adapter, Communicator communicator, CurrencyTranslator currencyTranslator) {
		this.adapter = adapter;
		this.currencyTranslator = currencyTranslator;
		this.communicator = communicator;
	}
	
	@Override
	public AccountPrx createNewAccount(int monthIncome, Current current) throws ErrorNewUser{
		String name, surname, PESEL;
		System.out.println("AccountManagerI :: "  + monthIncome + "\t::  " + current.ctx.toString());
		Account accountOut;
		Identity identity;
		name = current.ctx.get("name");
		surname = current.ctx.get("surname");
		PESEL = current.ctx.get("PESEL");			
		if(name==null || surname==null || PESEL==null)
			throw new ErrorNewUser();
		
		AccountType accType = monthIncome>minIncomeForPremium ? AccountType.PREMIUM : AccountType.STANDARD;
		String key = name + PESEL;
		try {
			if(accType == AccountType.PREMIUM) {
				accountOut = new AccountPremiumI(name, surname, PESEL, key, currencyTranslator);
				identity = new Identity(PESEL, "premium");
			} 
			else if(accType == AccountType.STANDARD) {
				accountOut = new AccountI(name, surname, PESEL, key, currencyTranslator);
				identity = new Identity(PESEL, "standard");
			}
			else {
				throw new Error("Magic");
			}
			adapter.add(accountOut, identity);
		} catch (com.zeroc.Ice.AlreadyRegisteredException e) {
			System.out.println("User " + PESEL + " already registered");
			throw new ErrorNewUser();
		}
		return AccountPrx.checkedCast(adapter.createProxy(identity));
	}


	@Override
	public AccountPrx connectAccount(Current current) throws ErrorConnectUser {
		// TODO Auto-generated method stub
		return null;
	}
}
