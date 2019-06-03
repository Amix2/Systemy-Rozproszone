package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import bookstore.Bookstore;

public class Main {
	public static void main(String[] args) throws Exception {

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String conf = br.readLine();
        // config
        File configFile = new File("main_conf"+conf+".conf");        
        Config config = ConfigFactory.parseFile(configFile);
        
        // create actor system & actors
        final ActorSystem system = ActorSystem.create("local_system", config);
        final ActorRef actorMain = system.actorOf(Props.create(MainActor.class), "mainActor");

        // interaction
        while (true) {
    		System.out.println("q, bookList, getPrice, stream, order");
    		String line = br.readLine();
    		String[] commands = line.split(" ");
    		if (commands[0].equals("q")) {
    			break;
    		}
    		actorMain.tell(commands, null);
    	}

        system.terminate();
    }
}
