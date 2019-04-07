package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Administrator {

	private final String INFO_KEY = "info";
	private final String LOG_KEY = "log";
	private final String EXCHANGE_NAME = "my_exchange";
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	private Channel channel;
	public Administrator() {
		try {
			ConnectionFactory factory = new ConnectionFactory();
	        factory.setHost("localhost");
	        Connection connection;
			connection = factory.newConnection();
			channel = connection.createChannel();
	        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

	        String queueName = channel.queueDeclare().getQueue();
	        channel.queueBind(queueName, EXCHANGE_NAME, LOG_KEY);
	        System.out.println("created queue: " + queueName + " with key " + LOG_KEY);
	        
	        Consumer consumerLog = new DefaultConsumer(channel) {
	            @Override
	            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
	                String message = new String(body, "UTF-8");
	                System.out.println("LOG: " + message);
	            }
	        };
	        
	        channel.basicConsume(queueName, true, consumerLog);
	        
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void mainLoop() throws Exception {
		while(true) {
			System.out.println("Rdy to send info");			
			String info = br.readLine();
			
			System.out.println("Sending " + info);		
			channel.basicPublish(EXCHANGE_NAME, INFO_KEY, null, info.getBytes("UTF-8"));
		}
	}
	
	public static void main(String[] args) throws Exception {
		Administrator admin = new Administrator();
		admin.mainLoop();
	}

}
