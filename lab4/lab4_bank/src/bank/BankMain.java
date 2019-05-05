package bank;

import java.io.IOException;
import java.util.ArrayList;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

import Banks.*;
import currencyExchange.CurrencyExchange;
import currencyExchangeGen.CurrencyList;
import currencyExchangeGen.CurrencyMap;
import currencyExchangeGen.CurrencyMap.MapFieldEntry;

public class BankMain {
	
	String port = "10000";
	ArrayList<Currency> currList;
	private final String currencyOptions = "EUR = 0; USD = 1; CHF = 2; GBP = 3; JPY = 4; PLN = 5; x";
	
	public void start(String[] args) throws IOException
	{
				
		java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
		String line;
		currList = new ArrayList<>();
		
		System.out.println("Port? 5d");
		line = in.readLine();
		port = line;
		
		System.out.println("Currency?\n"+currencyOptions);
		while(true) {
			line = in.readLine();
			if(line.equals("x")) break;
			currList.add(Currency.valueOf(line.toUpperCase()));
		}
		
		System.out.println(currList);
		
		
		CurrencyTranslator currencyTranslator = new CurrencyTranslator(currList);
		
		int status = 0;
		Communicator communicator = null;

		communicator = Util.initialize(args);

		ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("Adapter", String.format("tcp -h localhost -p %s:udp -h localhost -p %s",port, port));
  
		AccountManagerI accountManager = new AccountManagerI(adapter, currencyTranslator);
	
		adapter.add(accountManager, new Identity("admin", "admin"));

		adapter.activate();
		
		System.out.println("Entering event processing loop...");
		
		communicator.waitForShutdown(); 		
			
		
		
		if (communicator != null)
		{
			try
			{
				communicator.destroy();
			}
			catch (Exception e)
			{
				System.err.println(e);
				status = 1;
			}
		}
		System.exit(status);
	}
	
	public static void main(String[] args) throws IOException
	{
		BankMain app = new BankMain();
		app.start(args);
	}
}
