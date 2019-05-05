package currencyExchange;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;


public class CurrencyExchangeServer {
	private int port = 20000;
	private Server server;
	private CurrencyExchange currencyExchange;
	
	private void start() throws IOException 
	{ 
		currencyExchange = new CurrencyExchange();
		server = ServerBuilder.forPort(port)
				.addService(currencyExchange)
				.build()
				.start();
		System.out.println("Server started, listening on " + port);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				// Use stderr here since the logger may have been reset by its JVM shutdown hook.
				System.err.println("*** shutting down gRPC server since JVM is shutting down");
				CurrencyExchangeServer.this.stop();
				System.err.println("*** server shut down");
			}
		});
	}

	private void stop() {
		if (server != null) {
			server.shutdown();
		}
	}

	/**
	 * Await termination on the main thread since the grpc library uses daemon threads.
	 */
	private void blockUntilShutdown() throws InterruptedException {
		if (server != null) {
			server.awaitTermination();
		}
	}

	/**
	 * Main launches the server from the command line.
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		final CurrencyExchangeServer server = new CurrencyExchangeServer();
		server.start();
		
		while(true) {
			server.currencyExchange.updateCurrency();
			try { Thread.sleep(5000); } catch(java.lang.InterruptedException ex) { } 
		}
		
		//server.blockUntilShutdown();
	}
}
