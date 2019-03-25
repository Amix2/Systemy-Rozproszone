package lab2;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.protocols.BARRIER;
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

import java.io.*;
import java.util.List;
import java.util.LinkedList;

public class tut extends ReceiverAdapter {
    JChannel channel;
    String user_name=System.getProperty("user.name", "n/a");
    final List<String> state=new LinkedList<String>();

    public void viewAccepted(View new_view) {
        System.out.println("** view: " + new_view);
    }

    public void receive(Message msg) {
        String line=msg.getSrc() + ": " + msg.getObject();
        System.out.println(line);
        synchronized(state) {
            state.add(line);
        }
    }

    public void getState(OutputStream output) throws Exception {
        synchronized(state) {
            Util.objectToStream(state, new DataOutputStream(output));
        }
    }

    @SuppressWarnings("unchecked")
    public void setState(InputStream input) throws Exception {
    	System.out.println("SET STAT");
        List<String> list=(List<String>)Util.objectFromStream(new DataInputStream(input));
        synchronized(state) {
            state.clear();
            state.addAll(list);
        }
        System.out.println("received state (" + list.size() + " messages in chat history):");
        for(String str: list) {
            System.out.println(str);
        }
    }


    private void start() throws Exception {
		channel=new JChannel();
		ProtocolStack stack=new ProtocolStack();
		channel.setProtocolStack(stack);
		stack.addProtocol(new UDP())
		.addProtocol(new PING())
		.addProtocol(new MERGE3())
		.addProtocol(new FD_SOCK())
		.addProtocol(new FD_ALL().setValue("timeout", 12000).setValue("interval", 3000))
		.addProtocol(new VERIFY_SUSPECT())
		.addProtocol(new BARRIER())
		.addProtocol(new NAKACK2())
		.addProtocol(new UNICAST3())
		.addProtocol(new STABLE())
		.addProtocol(new GMS())
		.addProtocol(new UFC())
		.addProtocol(new MFC())
		.addProtocol(new FRAG2())
		.addProtocol(new STATE_SOCK());
		
		stack.init();
		channel.setReceiver(this);
		channel.connect("ChatCluster");
		channel.getState(null, 10000);
		eventLoop();
		channel.close();
	}

    private void eventLoop() {
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        while(true) {
            try {
                System.out.print("> "); System.out.flush();
                String line=in.readLine().toLowerCase();
                if(line.startsWith("quit") || line.startsWith("exit")) {
                    break;
                }
                line="[" + user_name + "] " + line;
                Message msg=new Message(null, null, line);
                channel.send(msg);
            }
            catch(Exception e) {
            }
        }
    }


    public static void main(String[] args) throws Exception {
        new tut().start();
    }
}