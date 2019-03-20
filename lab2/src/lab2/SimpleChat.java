package lab2;
import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.Receiver;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

public class SimpleChat extends ReceiverAdapter {
    JChannel channel;
    String user_name= new Double(Math.random()*10).toString();

    private void start() throws Exception {
        channel=new JChannel();
        channel.setReceiver(this);
        channel.connect("ChatCluster");
        eventLoop();
        channel.close();
    }
    
    public void viewAccepted(View new_view) {
        System.out.println("** view: " + new_view);
    }

    public void receive(Message msg) {
        System.out.println(msg.getSrc() + ": " + msg.getObject());
    }

    private void eventLoop() {
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        System.out.println(user_name);
        while(true) {
            try {
                System.out.print("> "); System.out.flush();
                String line=in.readLine().toLowerCase();
                if(line.startsWith("quit") || line.startsWith("exit"))
                    break;
                line="[" + user_name + "] " + line;
                Message msg=new Message(null, null, line);
                channel.send(msg);
            }
            catch(Exception e) {
            }
        }
    }

    public static void main(String[] args) throws Exception {
        new SimpleChat().start();
    }
}