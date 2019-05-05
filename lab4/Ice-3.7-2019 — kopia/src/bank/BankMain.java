package bank;

import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.Identity;
import com.zeroc.Ice.ObjectAdapter;
import com.zeroc.Ice.Util;

import Banks.*;
import sr.ice.server.CalcI;
import sr.ice.server.Server;

public class BankMain {
	
	public void start(String[] args)
	{
		CurrencyTranslator currencyTranslator = new CurrencyTranslator();
		int status = 0;
		Communicator communicator = null;


			// 1. Inicjalizacja ICE - utworzenie communicatora
			communicator = Util.initialize(args);

			// 2. Konfiguracja adaptera
			// METODA 1 (polecana produkcyjnie): Konfiguracja adaptera Adapter1 jest w pliku konfiguracyjnym podanym jako parametr uruchomienia serwera
			//Ice.ObjectAdapter adapter = communicator.createObjectAdapter("Adapter1");  
			
			// METODA 2 (niepolecana, dopuszczalna testowo): Konfiguracja adaptera Adapter1 jest w kodzie �r�d�owym
			ObjectAdapter adapter = communicator.createObjectAdapterWithEndpoints("Adapter", "tcp -h localhost -p 11111:udp -h localhost -p 11111");

			// 3. Stworzenie serwanta/serwant�w
			CalcI calcServant1 = new CalcI();    
			CalcI calcServant2 = new CalcI();    
			
			AccountManagerI accountManager = new AccountManagerI(adapter, currencyTranslator);
			
						    
			// 4. Dodanie wpis�w do tablicy ASM
			adapter.add(accountManager, new Identity("admin2", "admin2"));
			// adapter.add(accountManager, new Identity("admin", "admin")); // com.zeroc.Ice.AlreadyRegisteredException
	        //adapter.add(calcServant2, new Identity("calc22", "calc"));

	        
			// 5. Aktywacja adaptera i przej�cie w p�tl� przetwarzania ��da�
			
			System.out.println("Entering event processing loop...");
			
			communicator.waitForShutdown(); 		
			
		
		
		if (communicator != null)
		{
			// Clean up
			//
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
	
	public static void main(String[] args)
	{
		BankMain app = new BankMain();
		app.start(args);
	}
}
