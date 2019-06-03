package agh.lab6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class CalcWatcher implements Watcher {

	private String programRemoveName, programCreateName;
	private ZooKeeper zoo;

	

	public CalcWatcher(String programCreateName, String programRemoveName) {
		this.programRemoveName = programRemoveName;
		this.programCreateName = programCreateName;
		zoo = ZooConnection.getLastZoo();
	}

	public void process(WatchedEvent event) {
		String path = event.getPath();
		System.out.println("~~~~~~~ Watcher ~~~~~~");
		System.out.println("getType " + event.getType());
		System.out.println("getPath " + event.getPath());
		System.out.println("getState " + event.getState());
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
		try {
			switch(event.getType()) {
			case ChildWatchRemoved:
				break;
			case DataWatchRemoved:
				break;
			case NodeChildrenChanged:
				System.out.println("New Child, total children: " + zoo.getChildren(path, true).size());
				zoo.getChildren(path, this);
				break;
			case NodeDataChanged:
				zoo.exists(path, this);
				break;
			case NodeDeleted:
				zoo.exists(path, this);
				killAllPrograms();
				break;
			case None:
				break;
			case NodeCreated:
				zoo.exists(path, this);
				createProgram();
				break;
			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	private void createProgram() {
		Process pr = null;
    	try {
			pr = Runtime.getRuntime().exec(programCreateName);
			pr.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void killAllPrograms() {
		String calcPID = null;
		while((calcPID = getPid(programRemoveName)) != null) {
			killProces(calcPID);
		}
	}
	
	public static String getPid(String name) {
		String pid = null;
		Process p;
		try {
			p = Runtime.getRuntime().exec("tasklist");
			String line;
			BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
			while ((line = input.readLine()) != null) {
				if(line.split(" ").length > 1) {
					line = line.trim().replaceAll(" +", " ");
					if(line.split(" ")[0].contains(name)) {
						pid = line.split(" ")[1];
						break;
					}
				}
			}
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pid;
	}
	
	public static void killProces(String pid) {
		 try {
			Runtime.getRuntime().exec("taskkill /PID " + pid + " /F");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
