package currencyExchange;

import static io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import Banks.Currency;
import Banks.localCurrency;
import currencyExchangeGen.CurrencyExchangeStreamGrpc;
import currencyExchangeGen.CurrencyExchangeStreamGrpc.CurrencyExchangeStreamImplBase;
import currencyExchangeGen.CurrencyExchangeStreamGrpc.CurrencyExchangeStreamStub;
import currencyExchangeGen.CurrencyMap.MapFieldEntry;
import currencyExchangeGen.CurrencyList;
import currencyExchangeGen.CurrencyMap;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

public class CurrencyExchange extends CurrencyExchangeStreamImplBase implements localCurrency{
	
	private Map<String, Integer> currencyMap;
	Random rand = new Random();

	public CurrencyExchange() {
		currencyMap = new HashMap<>();
		int exampleVal = 100;
		for(Currency c : Currency.values()) {
			if(c!= localCurrency.value && c!=Currency.UNRECOGNIZED) {
				currencyMap.put(c.toString(), exampleVal);
				exampleVal+=150;				
			}
		}
		System.out.println("currencyMap\n\t" + currencyMap.toString());
	}
	
	public void updateCurrency() {
		for(String currStr : currencyMap.keySet()) {
			Integer val = currencyMap.get(currStr);
			val = val + rand.nextInt(50)-20;
			if(val <= 0) val = rand.nextInt(50);
			currencyMap.put(currStr, val);
		}
		System.out.println("currencyMap\n\t" + currencyMap.toString());
		System.out.flush();
	}
	
	
	@Override
	public void orderCurrencyExchange(CurrencyList request,
			StreamObserver<CurrencyMap> responseObserver) {
		while(true) {
			CurrencyMap map = CurrencyMap.newBuilder().build();
			for(String currStr : request.getItemList()) {
				Integer val = currencyMap.get(currStr);
				map = CurrencyMap.newBuilder(map)
						.addMapField(MapFieldEntry.newBuilder().setKey(currStr).setValue(val).build())
						.build();
			}
			responseObserver.onNext(map);
			try { Thread.sleep(5000); } catch(java.lang.InterruptedException ex) { } 
		}
	}
}
