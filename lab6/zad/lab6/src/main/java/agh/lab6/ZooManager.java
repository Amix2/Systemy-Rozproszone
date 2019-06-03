package agh.lab6;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.common.StringUtils;

public class ZooManager {
	
	private ZooKeeper zkeeper;
	
	public ZooManager(String port) {
		try {
			zkeeper = ZooConnection.createZoo("localhost:"+port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void bindWatcherToNode(String path, Watcher watcher) throws KeeperException, InterruptedException {
		zkeeper.exists(path, watcher);
	}
	
	public void bindWatcherToChildren(String path, Watcher watcher) throws KeeperException, InterruptedException {
		if(!path.startsWith("/")) {
			//System.out.println(path);
			return;
		}
		List<String> children = zkeeper.getChildren(path, watcher);
		children.forEach(child -> {
			try {
				bindWatcherToChildren(child, watcher);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		});
	}
	
	
	public void createNode(String path, byte[] data) throws KeeperException, InterruptedException {
		System.out.println("create");
		zkeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
	}
	
	public void deleteNode(String path) throws KeeperException, InterruptedException {
		System.out.println("delete");
		int version = zkeeper.exists(path, true).getVersion();
		zkeeper.delete(path, version);
	}
	
	public void getChildren(String path) throws KeeperException, InterruptedException {
		printChildren(path, 0);
	}
	
	private void printChildren(String path, int dec) throws KeeperException, InterruptedException {
		//System.out.println(path);
		if(!path.startsWith("/")) {
			//System.out.println(path);
			return;
		}
		List<String> children = zkeeper.getChildren(path, true);
		System.out.println(path);
		children.forEach(child -> {
			try {
				printChildren(path+"/"+child, dec+1);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}
	
	public String getZNodeData(String path) throws KeeperException, InterruptedException, UnsupportedEncodingException {

		byte[] b = null;
			
		b = zkeeper.getData(path, true, null);
		return new String(b, "UTF-8");
	}
	
	public void update(String path, byte[] data) throws KeeperException, InterruptedException {
		System.out.println("update");
		
		int version = zkeeper.exists(path, true).getVersion();
		zkeeper.setData(path, data, version);
	}

}
