package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

public class Technik {

	private final String INFO_KEY = "info.#";
	private final String RESPONSE_KEY_PREFIX = "response.";
	private final String LOG_KEY = "log";
	private final String EXCHANGE_NAME = "my_exchange";
	//private String treatmentsOptions = "123";
	private Channel channel;
	final String options = "Biodro (hip) = 1, \n"
				    		+ "kolano (knee) = 2, \n"
				    		+ "³okieæ (elbow) = 3";
	
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public Technik() throws Exception {
		System.out.println(options);
		System.out.println("Procedure Numbers:");
		String treatment = br.readLine();
		treatment += br.readLine();
		
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();

        Consumer consumerTreatment = new DefaultConsumer(channel) {
        	@Override
        	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        		String message = new String(body, "UTF-8");
        		String responseKey = RESPONSE_KEY_PREFIX+message;
        		System.out.println("Received treatment message " + message + "\tKey: " + envelope.getRoutingKey());
        		try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
        		System.out.println("Treatment " + message + " DONE");
        		message += ".DONE";
        		channel.basicPublish(EXCHANGE_NAME, responseKey, null, message.getBytes("UTF-8"));
        		channel.basicPublish(EXCHANGE_NAME, LOG_KEY, null, message.getBytes("UTF-8"));
        	}
        };

        // queue
        for(char treat : treatment.toCharArray()) {
        	String QUEUE_NAME = queueName(treat);
        	System.out.println("Joing queue " + QUEUE_NAME);
        	channel.queueDeclare(QUEUE_NAME, false, false, false, null);     
        	channel.basicConsume(QUEUE_NAME, true, consumerTreatment);
        }

        // start listening
        Consumer consumerInfo = new DefaultConsumer(channel) {
        	@Override
        	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        		String message = new String(body, "UTF-8");
        		System.out.println("Received: " + message + "\n\tKey: " + envelope.getRoutingKey());
        	}
        };

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String queueNameExchange = channel.queueDeclare().getQueue();
        channel.queueBind(queueNameExchange, EXCHANGE_NAME, INFO_KEY);
        System.out.println("Created queue: " + queueNameExchange);
        channel.basicConsume(queueNameExchange, true, consumerInfo);
		
	}
	
	public void mainLoop() throws Exception {
		System.out.println("Waiting for messages");
	}
	
	private String queueName(char treat) {
		return "treat_" + treat;
	}
	
	
	public static void main(String[] args) throws Exception {
		Technik tech = new Technik();
		tech.mainLoop();

	}

}
