package bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import Banks.Currency;
import Banks.localCurrency;
import currencyExchangeGen.CurrencyExchangeStreamGrpc;
import currencyExchangeGen.CurrencyList;
import currencyExchangeGen.CurrencyMap;
import currencyExchangeGen.CurrencyExchangeStreamGrpc.CurrencyExchangeStreamStub;
import currencyExchangeGen.CurrencyMap.MapFieldEntry;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class CurrencyTranslator implements localCurrency{
	protected ArrayList<Currency> currList;
	private Map<Currency, Integer> currencyMap = new HashMap<>();
	// 1 * local_curr = x * key_curr 
	private ManagedChannel channel = null;
	private CurrencyExchangeStreamStub currencyExchangeStreamStub;
	
	
	public CurrencyTranslator(ArrayList<Currency> currList) {
		this.currList = currList;
		connectToExchange();
		System.out.println(currList);
		handleData();
	}
	
	private void connectToExchange() {
		channel = ManagedChannelBuilder.forAddress("localhost", 20000)
				.usePlaintext(true)
				.build();

		currencyExchangeStreamStub = CurrencyExchangeStreamGrpc.newStub(channel);
	}
	
	private void handleData() {
		CurrencyList request = CurrencyList.newBuilder().build();
		for(Currency c : currList) {
			if(c!= localCurrency.value && c!=Currency.UNRECOGNIZED) {
				request = CurrencyList.newBuilder(request).addItem(c.toString()).build();
			}
		} 
		
		StreamObserver<CurrencyMap> responseObserver = new StreamObserver<CurrencyMap>() {

			@Override
			public void onCompleted() {
				System.out.println("Currency Exchange Server stopped working");
			}

			@Override
			public void onError(Throwable arg0) {
				System.out.println("Currency Exchange Server error");
			}

			@Override
			public void onNext(CurrencyMap arg0) {
				for (MapFieldEntry entry : arg0.getMapFieldList()) {
					currencyMap.put(Currency.valueOf(entry.getKey()), entry.getValue());
				}
				System.out.println("currencyMapUpdate\n\t" + currencyMap.toString());
			}
			
		};
		
		currencyExchangeStreamStub.orderCurrencyExchange(request, responseObserver);
	}
	
	public void print() {
		System.out.println(currencyMap.toString());
	}
	
	public int exchange(Currency from, Currency to, int ammount) {
		int amountInLocal = localCurrency.value==from ? ammount : ammount * currencyMap.get(from);
		int amountOut = localCurrency.value==to ? amountInLocal : amountInLocal / currencyMap.get(to);
		System.out.println(String.format("exchange\tfrom %s, to %s amount %d, in local: %d, in out %d", from.toString(), to.toString(), ammount, amountInLocal, amountOut));
		return amountOut;
			
	}
}
