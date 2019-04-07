package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;


public class Lekarz {

	private final String INFO_KEY = "info";
	private final String RESPONSE_KEY = "response.#";
	private final String LOG_KEY = "log";
	private final String EXCHANGE_NAME = "my_exchange";
	private String treatments = "123";
	private Channel channel;
	final String options = "Biodro (hip) = 1, \n"
				    		+ "kolano (knee) = 2, \n"
				    		+ "³okieæ (elbow) = 3";
	ArrayList<String> orderedProcedures = new ArrayList<String>();
	
	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		
	public Lekarz() throws Exception {
        System.out.println(options);
        
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        channel = connection.createChannel();

        // queue
        for(char treat : treatments.toCharArray()) {
        	String QUEUE_NAME = queueName(treat);
        	System.out.println("Joing queue " + QUEUE_NAME);
        	channel.queueDeclare(QUEUE_NAME, false, false, false, null);     
        }

        
        Consumer consumer = new DefaultConsumer(channel) {
        	@Override
        	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        		String message = new String(body, "UTF-8");
        		System.out.println("handleDelivery");
        		if(isForMe(envelope.getRoutingKey())) {
        			System.out.println("Received: " + message + "\tKey: " + envelope.getRoutingKey());
        		}
        	}
        };
        
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        
        String queueNameExchange = channel.queueDeclare().getQueue();
        channel.queueBind(queueNameExchange, EXCHANGE_NAME, INFO_KEY);
        channel.queueBind(queueNameExchange, EXCHANGE_NAME, RESPONSE_KEY);
        System.out.println("Created queue: " + queueNameExchange);

        channel.basicConsume(queueNameExchange, true, consumer);
	}
	
	public void mainLoop() throws Exception {
		while(true) {
			System.out.println(options);
			
			System.out.println("Procedure Number:");
			String proc = br.readLine();
			
			if(!treatments.contains(proc)) {
				System.out.println("Wrong procedure");
				continue;
			}
			
			System.out.println("Patient:");
			String patient = br.readLine();
			
			sendMsg(proc, patient);			
		}
	}
	
	private boolean isForMe(String key) {
		String procedure = key.substring(key.indexOf(".")+1);
		if(key.startsWith(INFO_KEY)) 
			return true;
		if(orderedProcedures.contains(procedure)) {
			orderedProcedures.remove(procedure);
			return true;
		}
		return false;
	}
	
	private String queueName(char treat) {
		return "treat_" + treat;
	}
	
	private void sendMsg(String procNum, String patient) throws Exception {
		String message = procNum + "." + patient;
		System.out.println("Sending " + message + " to: " + queueName(procNum.charAt(0)));
		channel.basicPublish("", queueName(procNum.charAt(0)), null, message.getBytes());
		channel.basicPublish(EXCHANGE_NAME, LOG_KEY, null, message.getBytes("UTF-8"));
		orderedProcedures.add(message);
		System.out.println(orderedProcedures.toString());
	}
	
	public static void main(String[] args) throws Exception {
		Lekarz lek = new Lekarz();
		lek.mainLoop();
	}

}
