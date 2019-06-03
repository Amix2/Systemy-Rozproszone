package agh.lab6;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.Watcher.Event.KeeperState;

public class ZooConnection {
	private static ZooKeeper lastZoo = null;
	
	public static ZooKeeper createZoo(String host) throws IOException {
		if(lastZoo != null) throw new RuntimeException("createZoo but zoo already exists");
		lastZoo = new ZooKeeper(host, 2000, new Watcher() {
			public void process(WatchedEvent we) {
				if (we.getState() == KeeperState.SyncConnected) {
					new CountDownLatch(1).countDown();
				}
			}
		});
		
		return lastZoo;
	}
	
	public static ZooKeeper getLastZoo() {
		if(lastZoo == null) throw new RuntimeException("getLastZoo but zoo does not exist");
		return lastZoo;
	}
	
	public static void closeLastZoo() {
		if(lastZoo == null) throw new RuntimeException("closeLastZoo but zoo does not exist");
		try {
			lastZoo.close();
			lastZoo = null;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	private ZooConnection() {}
}
