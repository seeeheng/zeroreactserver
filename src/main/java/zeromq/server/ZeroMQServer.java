package zeromq.server;

import org.json.JSONObject;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ.Socket;
import org.zeromq.ZMsg;

public class ZeroMQServer implements Runnable{
    static final String zeromq_prefix = "tcp://*:";
    private ReactServer react_server;
    private Processor msg_processor = new Processor();

    public ZeroMQServer(ReactServer react_server){
        this.react_server = react_server;
    }

    @Override
    public void run() {
        try (ZContext ctx = new ZContext()) {
            int inbound_port = 3319;
            Socket inbound_server = ctx.createSocket(SocketType.REP);
            // Binding to TCP socket for ZeroMQ
            inbound_server.bind(zeromq_prefix + Integer.toString(inbound_port));
                
            System.out.println("Server is ready for inbound via zeromq at port: " + inbound_port);
            while (true){
                ZMsg msg = ZMsg.recvMsg(inbound_server);
                if (msg==null)
                    break;
                ZMsg.newStringMsg("Received!").send(inbound_server);
                String reply = msg.popString();
                System.out.println("Received type " + reply + " from client.");
                JSONObject json_send = msg_processor.processMessage(reply, msg);
                this.react_server.broadcast(json_send.toString());
            }
            if(Thread.currentThread().isInterrupted()){
                System.out.printf("Interrupted\n");    
            }       
        }
    }
}