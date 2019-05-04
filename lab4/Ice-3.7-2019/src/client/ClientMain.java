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
import Banks.ErrorNewUser;
import Banks.NewAccountDetails;
import Demo.CalcPrx;

public class ClientMain {
	public static void main(String[] args) 	{
		System.out.println(AccountType.STANDARD.toString().toLowerCase());
		int status = 0;
		Communicator communicator = null;
		String PESEL = null;
		java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));

			// 1. Inicjalizacja ICE
			communicator = Util.initialize(args);

			// 2. Uzyskanie referencji obiektu na podstawie linii w pliku konfiguracyjnym
			//Ice.ObjectPrx base = communicator.propertyToProxy("Calc1.Proxy");
			// 2. To samo co powy¿ej, ale mniej ³adnie
			ObjectPrx adminServObj = communicator.stringToProxy("admin/admin:tcp -h localhost -p 11111:udp -h localhost -p 11111");

			// 3. Rzutowanie, zawê¿anie
			AccountManagerPrx adminServ = AccountManagerPrx.checkedCast(adminServObj);
			if (adminServ == null) throw new Error("Invalid proxy");
			
			////////////////////////////////////////////////////
			// 		CREATE NEW USER
			////////////////////////////////////////////////////			
			String line = null;
			Map<String, String> context = new HashMap<String, String>();
			AccountType accType = null;
			System.out.println("0-create new; 1-connect");
			try	{
				line = in.readLine();
				if(line.contentEquals("0")) { // create new
					System.out.println("monthIncome?");
					line = in.readLine();
					int mIncome = Integer.parseInt(line);
					System.out.println("PESEL?");
					line = in.readLine();
					PESEL = line;
					context.put("name", "n"+randomAlphaNumeric(5));
					context.put("surname", "s"+randomAlphaNumeric(5));
					context.put("PESEL", PESEL);
					NewAccountDetails accDet = adminServ.createNewAccount(mIncome, context);
					System.out.println("New account:\t" + accDet.accountType.toString() + ", key: " + accDet.key);	
					accType = accDet.accountType;
				} else { // connect
					System.out.println("0-standard; 1-premium");
					line = in.readLine();
					if(line.contentEquals("0")) { // standard
						accType = AccountType.STANDARD;
					} else {
						accType = AccountType.PREMIUM;
					}
				}
			} catch (IOException ex) {
				System.err.println(ex);
				return;
			} catch (ErrorNewUser e) {
				System.out.println("ErrorNewUser");
				e.printStackTrace();
			}
			
		////////////////////////////////////////////////////
		// 		GET USER ACCOUNT
		////////////////////////////////////////////////////	
		ObjectPrx accountPrxObj = communicator.stringToProxy(accType.toString().toLowerCase()+"/"+PESEL+":tcp -h localhost -p 11111:udp -h localhost -p 11111");

		// 3. Rzutowanie, zawê¿anie
		AccountPrx accountPrx = AccountPrx.checkedCast(accountPrxObj);
		if (accountPrx == null) throw new Error("Invalid proxy");

		if(accountPrx.ice_isA ("::Banks::AccountPremium")) {
			System.out.println("AccountPremiumI");
		} else if(accountPrx.ice_isA ("::Banks::Account")) {
			System.out.println("Account");
		} else throw new Error("IS_A?!");
		
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

