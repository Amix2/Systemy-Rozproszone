package agh.lab6;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import io.netty.util.internal.SystemPropertyUtil;

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
		//System.out.println("~~~~~~~ Watcher ~~~~~~");
		//System.out.println("getType " + event.getType());
		//System.out.println("getPath " + event.getPath());
		//System.out.println("getState " + event.getState());
		//System.out.println("~~~~~~~~~~~~~~~~~~~~~~");
		try {
			switch(event.getType()) {
			case ChildWatchRemoved:
				break;
			case DataWatchRemoved:
				break;
			case NodeChildrenChanged:
				System.out.println("New Child " + path + ", total children: " + numChildren(path));
				List<String> children = zoo.getChildren(path, this);
				children.forEach(child -> {
					try {
						bindWatcherToChildren(path+"/"+child, this);
					} catch (Exception e) {
						e.printStackTrace();
					} 
				});
				break;
			case NodeDataChanged:
				System.out.println("Data changed, new data: " + zoo.getData(path, true, null).toString());
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
	
	private int numChildren(String path) {
		if(!path.startsWith("/")) {
			System.out.println(path);
			return 0;
		}
		int out = 0;
		List<String> children = null;
		//System.out.println(path);
		try {
			children = zoo.getChildren(path, true);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for(String ch : children) {
			try {
				out += numChildren(path+"/"+ch);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		return out+children.size();
	}
	
	public void bindWatcherToChildren(String path, Watcher watcher) throws KeeperException, InterruptedException {
		if(!path.startsWith("/")) {
			//System.out.println(path);
			return;
		}
		List<String> children = zoo.getChildren(path, watcher);
		children.forEach(child -> {
			try {
				bindWatcherToChildren(path+"/"+child, watcher);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		});
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
