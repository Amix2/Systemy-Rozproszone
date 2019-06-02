package agh.lab6.test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import agh.lab6.CalcWatcher;

public class ZKManagerImpl implements ZKManager {
	private static ZooKeeper zkeeper;
	private static ZKConnection zkConnection;
	private static CalcWatcher watcher;

	public ZKManagerImpl() throws IOException, InterruptedException, KeeperException {
		System.out.println("init");
		initialize();
		//zkeeper.register(new CalcWatcher());
		//zkeeper.exists("/n12", new CalcWatcher());
	}

	private void initialize() throws IOException, InterruptedException {
		zkConnection = new ZKConnection();
		System.out.println("init2");
		zkeeper = zkConnection.connect("localhost");
		System.out.println("init3");
	}

	public void closeConnection() throws InterruptedException {
		zkConnection.close();
	}

	public void create(String path, byte[] data) throws KeeperException, InterruptedException {
		zkeeper.create(path, data, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.CONTAINER);
	}

	public Object getZNodeData(String path, boolean watchFlag) throws KeeperException, InterruptedException, UnsupportedEncodingException {

		byte[] b = null;
		b = zkeeper.getData(path, null, null);
		return new String(b, "UTF-8");
	}

	public void update(String path, byte[] data) throws KeeperException, InterruptedException {
		int version = zkeeper.exists(path, true).getVersion();
		zkeeper.setData(path, data, version);
	}
	
	public void bindWatcherToNode(String path, Watcher watcher) throws KeeperException, InterruptedException {
		zkeeper.exists(path, watcher);
	}
}
