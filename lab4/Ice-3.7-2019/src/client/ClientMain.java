package client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.LocalException;
import com.zeroc.Ice.ObjectPrx;
import com.zeroc.Ice.Util;

import Banks.AccountManagerPrx;
import Banks.AccountPremium;
import Banks.AccountPrx;
import Banks.AccountType;
import Banks.Currency;
import Banks.DualCurrency;
import Banks.ErrorLoan;
import Banks.ErrorNewUser;
import Banks.NewAccountDetails;

public class ClientMain {
	public static void main(String[] args) throws IOException 	{
		String currencyList = "EUR, USD, CHF, GBP, JPY, PLN";
		int status = 0;
		String port;
		Communicator communicator = null;
		String PESEL = null;
		java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));

		String line = null;
		communicator = Util.initialize(args);
		System.out.println("Bank Port? 5d");
		line = in.readLine();
		port = line;
		ObjectPrx adminServObj = communicator.stringToProxy(String.format("admin/admin:tcp -h localhost -p %s:udp -h localhost -p %s", port, port));

		AccountManagerPrx adminServ = AccountManagerPrx.checkedCast(adminServObj);
		if (adminServ == null) throw new Error("Invalid proxy");
		
		////////////////////////////////////////////////////
		// 		CREATE NEW USER OR CONNECT
		////////////////////////////////////////////////////			
		AccountPrx accountPrx = null;
		Map<String, String> context = new HashMap<String, String>();
		AccountType accType = null;
		try	{
			System.out.println("0-create new; 1-connect");
			line = in.readLine();
			if(line.contentEquals("0")) { // create new
				System.out.println("monthIncome?");
				line = in.readLine();
				int mIncome = Integer.parseInt(line);
				System.out.println("PESEL?");
				line = in.readLine();
				PESEL = line;
				context.put("name", "n"+randomAlphaNumeric(1));
				context.put("surname", "s"+randomAlphaNumeric(1));
				context.put("PESEL", PESEL);
				accountPrx = adminServ.createNewAccount(mIncome, context);
				System.out.println("New account:\tkey: " + accountPrx.getKey());	
			} else { // connect
				System.out.println("PESEL?");
				line = in.readLine();
				PESEL = line;
				System.out.println("0-standard; 1-premium");
				line = in.readLine();
				if(line.contentEquals("0")) { // standard
					accType = AccountType.STANDARD;
				} else {
					accType = AccountType.PREMIUM;
				}
				ObjectPrx accountPrxObj = communicator.stringToProxy(String.format("%s/%s:tcp -h localhost -p %s:udp -h localhost -p %s", 
						accType.toString().toLowerCase(), PESEL, port, port));
				accountPrx = AccountPrx.checkedCast(accountPrxObj);
				System.out.println("Connected account:\tkey: " + accountPrx.getKey());	
			}
		} catch (IOException ex) {
			System.err.println(ex);
			return;
		} catch (ErrorNewUser e) {
			System.out.println("ErrorNewUser");
			e.printStackTrace();
			return;
		} catch (com.zeroc.Ice.ObjectNotExistException e) {
			System.out.println("ERROR: User not found");
			return;
		}
		
		////////////////////////////////////////////////////
		// 		GET ACCOUNT TYPE AND KEY
		////////////////////////////////////////////////////	

		if(accountPrx == null) throw new Error("Invalid proxy");
		if(accountPrx.ice_isA ("::Banks::AccountPremium")) {
			System.out.println("Account type: PREMIUM");
		} else if(accountPrx.ice_isA ("::Banks::Account")) {
			System.out.println("Account type: STANDARD");
		} else throw new Error("IS_A?!");
		
		String accKey = accountPrx.getKey();
		System.out.println("Account key: " + accKey);
		
		////////////////////////////////////////////////////
		// 		MAIN LOOP
		////////////////////////////////////////////////////
		do {
			System.out.println("0-getStatus, 1-getLoan");
			try {
				line = in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
				break;
			}
			switch(line) {
			case("0"):
				System.out.println(accountPrx.getStatus());
				break;
			case("1"):
				Map<String, String> ctx = new HashMap<>();
				ctx.put("key", accKey);
				ctx.put("PESEL", PESEL);
				System.out.println(currencyList);
				System.out.println("currency? 0-default");
				Currency curr = null;
				int amount = 0, time = 0;
				try {
					line = in.readLine();
					
					if(line.equals("0")) {
						curr = Currency.EUR;
						amount = 1000;
						time = 100;
					} else {
						curr = Currency.valueOf(line); 
						System.out.println("amount?");
						line = in.readLine();
						amount = Integer.parseInt(line);
						System.out.println("time?");
						line = in.readLine();
						time = Integer.parseInt(line);
					}
					System.out.println(String.format("default:\ncurrency: %s\nammount: %d\ntime: %d", curr.toString(), amount, time));
				} catch (IOException e) {
					e.printStackTrace();
					break;
				}
				try {
					DualCurrency loanDet = accountPrx.getLoan(curr, amount, time, ctx);
					System.out.println(String.format("loan: amount in local:%d, in global (%s): %d", 
							loanDet.localCurrencyValue, loanDet.globalCurrency.toString(), loanDet.globalCurrencyValue));
				} catch (ErrorLoan e) {
					System.out.println("Loan cannot be granted");
					e.printStackTrace();
					break;
				}
				break;
			default:
				System.out.println("Wrong");
			}
			
		} while(!line.equals("x"));
		
		////////////////////////////////////////////////////
		
		
		if (communicator != null) {
			// Clean up
			//
			try {
				communicator.destroy();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				status = 1;
			}
		}
		System.exit(status);
	}
	
	private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	public static String randomAlphaNumeric(int count) {
		StringBuilder builder = new StringBuilder();
		while (count-- != 0) {
			int character = (int)(Math.random()*ALPHA_NUMERIC_STRING.length());
			builder.append(ALPHA_NUMERIC_STRING.charAt(character));
		}
		return builder.toString();
	}
}

