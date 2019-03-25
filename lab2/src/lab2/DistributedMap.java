package lab2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;
import java.util.Vector;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.MergeView;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.protocols.BARRIER;
import org.jgroups.protocols.DISCARD;
import org.jgroups.protocols.FD_ALL;
import org.jgroups.protocols.FD_SOCK;
import org.jgroups.protocols.FRAG2;
import org.jgroups.protocols.MERGE3;
import org.jgroups.protocols.MFC;
import org.jgroups.protocols.PING;
import org.jgroups.protocols.UDP;
import org.jgroups.protocols.UFC;
import org.jgroups.protocols.UNICAST3;
import org.jgroups.protocols.VERIFY_SUSPECT;
import org.jgroups.protocols.pbcast.GMS;
import org.jgroups.protocols.pbcast.NAKACK2;
import org.jgroups.protocols.pbcast.STABLE;
import org.jgroups.protocols.pbcast.STATE_SOCK;
import org.jgroups.stack.ProtocolStack;
import org.jgroups.util.Util;

public class DistributedMap extends ReceiverAdapter implements ISimpleStringMap {

	Hashtable<String, Integer> map = new Hashtable<String, Integer>();
	JChannel channel;
	String user_name = System.getProperty("user.name", "n/a") + new Double(Math.random() * 10).toString().substring(2);

	public DistributedMap() throws Exception {
		channel = new JChannel();
		ProtocolStack stack = new ProtocolStack();
		channel.setProtocolStack(stack);
		stack.addProtocol(new UDP()).addProtocol(new PING()).addProtocol(new MERGE3()).addProtocol(new FD_SOCK())
				.addProtocol(new FD_ALL().setValue("timeout", 12000).setValue("interval", 3000))
				.addProtocol(new VERIFY_SUSPECT()).addProtocol(new BARRIER()).addProtocol(new NAKACK2())
				.addProtocol(new UNICAST3()).addProtocol(new STABLE()).addProtocol(new GMS()).addProtocol(new UFC())
				.addProtocol(new MFC()).addProtocol(new FRAG2()).addProtocol(new STATE_SOCK());
		stack.init();
		channel.setReceiver(this);
		channel.connect("name1");
		channel.getState(null, 10000);

	}

	protected void finalize() throws Throwable {
		super.finalize();
		channel.close();
	}

	public void receive(Message msg) { // TO DO
		String line = msg.getObject().toString();
		if(channel.getAddress() == msg.getSrc()) {
			System.out.println("::receive from ME -> " + line);
			return;
		}		
		System.out.println("::receive from " + msg.getSrc().toString() + " -> " + line);
		
		String values[] = line.split(":");
		
		switch (values[0]) {
			case "put" :
				putLocal(values[1], Integer.parseInt(values[2]));
				break;
			case "remove" :
				removeLocal(values[1]);
				break;
		}
	}

    public synchronized void getState(OutputStream output) throws Exception {
    	//System.out.println("GET state()");
    	Util.objectToStream(map, new DataOutputStream(output));
    }

    @SuppressWarnings("unchecked")
    public void setState(InputStream input) throws Exception {
    	//System.out.println("SET STAT");
    	Hashtable<String, Integer> newMap=(Hashtable<String, Integer>)Util.objectFromStream(new DataInputStream(input));
        synchronized(map) {
            map.clear();
            map.putAll(newMap);
        }
    }
	
    public void put() {
    	Integer num = new Integer((int) (Math.random()*100));
		put("R_K_"+num.toString(), num);
    }
    
	public void put(String key, Integer value) { // send info
		//System.out.println("::put " + key + " : " + value);
		if(key.contains(":")) {
			System.out.println("WARNING: Key conteins forbidden character \":\"");
			key = key.replace(":", "");
		}
		String msgText = "put:" + key + ":" + value; 
		try {	
		channel.send(new Message(null, null, msgText));
		} catch (Exception e) {	e.printStackTrace(); }
		
		putLocal(key, value);
	}
	
	
	public Integer remove(String key) { // send info
		//System.out.println("::remove " + key);
		String msgText = "remove:" + key; 
		try {	
		channel.send(new Message(null, null, msgText));
		} catch (Exception e) {	e.printStackTrace(); }
		
		return removeLocal(key);
	}
	
	public boolean containsKey(String key) {
		return map.containsKey(key);
	}

	public Integer get(String key) {
		return map.get(key);
	}
	
	private synchronized void putLocal(String key, Integer value) {
		map.put(key, value);
	}
	
	public synchronized Integer removeLocal(String key) {
		return map.remove(key);
	}

	public String toPrint() {
		String out = "DistributedMap : " + map.size() + "\n";
		for (Entry<String, Integer> entry : map.entrySet()) {
			String key = entry.getKey();
			Integer value = entry.getValue();
			out += "\t" + key + " ->> " + value + "\n";
		}
		out += "============";
		return out;
	}
	
	public void print() {
		System.out.println(toPrint());
	}
	
	@SuppressWarnings("unused")
	public void viewAccepted(View new_view) {
        System.out.println("** view: " + new_view);
        if(new_view instanceof MergeView) {
        	// pierwsza grupa do wszystkich, inne ignorowane
        	Runnable runnable =
        	        () -> { 
        	        	List<View> subgroups = ((MergeView) new_view).getSubgroups();
        	            View firstView = subgroups.get(0); 
        	            Address localAddr = channel.getAddress();
        	            if(!firstView.getMembers().contains(localAddr)) {
        	                try {
        	                	channel.getState(null, 10000);
        	                }
        	                catch(Exception e) {
        	                	e.printStackTrace();
        	                }
        	            }

        	        };
        }
    }
}
