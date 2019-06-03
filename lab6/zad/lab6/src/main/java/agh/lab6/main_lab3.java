package agh.lab6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.zookeeper.KeeperException;


/**
 * Hello world!
 *
 */
public class main_lab3 
{
	
	static BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in));
	static CalcWatcher watcher;
		
    public static void main( String[] args ) throws IOException 
    {
        System.out.println( "Main start" );
        System.out.print("port =>\t");
        String port = buffer.readLine();
        ZooManager zooManager = new ZooManager(port);
        watcher = new CalcWatcher("Calc", "Calculator");
        
        String line;
        String data;
        while(true) {
        	try {
        		System.out.println("======================================");
        		System.out.print("cmd =>\t");
				line=buffer.readLine();
	        	if(line.equals("q")) break;
	        	switch(line) {
	        	case "create":
	        		System.out.print("path =>\t");
	        		line=buffer.readLine();
	        		System.out.print("data =>\t");
	        		data=buffer.readLine();
	        		zooManager.createNode(line, data.getBytes());
	        		break;
	        	case "delete":
	        		System.out.print("path =>\t");
	        		line=buffer.readLine();
	        		zooManager.deleteNode(line);
	        		break;
	        	case "getData":
	        		System.out.print("path =>\t");
	        		line=buffer.readLine();
	        		String resp = zooManager.getZNodeData(line);
	        		System.out.println(resp);
	        		break;
	        	case "update":
	        		System.out.print("path =>\t");
	        		line=buffer.readLine();
	        		System.out.print("data =>\t");
	        		data=buffer.readLine();
	        		zooManager.update(line, data.getBytes());
	        		break;
	        	case "children":
	        		System.out.print("path =>\t");
	        		line=buffer.readLine();
	        		zooManager.getChildren(line);
	        		break;
	        	case "watcher":
	        		System.out.print("path =>\t");
	        		line=buffer.readLine();
	        		zooManager.bindWatcherToNode(line, watcher);
	        		break;
	        	case "watcherChildren":
	        		System.out.print("path =>\t");
	        		line=buffer.readLine();
	        		zooManager.bindWatcherToChildren(line, watcher);
	        		break;
	        	}
	        	
        	} catch (Exception e) {
				e.printStackTrace();
			}
        	
        }
        System.out.println("Main end");
    }
}
