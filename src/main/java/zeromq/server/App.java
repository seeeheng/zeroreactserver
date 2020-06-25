package zeromq.server;

import java.net.InetSocketAddress;

// TODO: can't close server properly. closed null with exit code 1001 additional info.

/* Implementation 3: Freelance pirate */
public class App {
    static ReactServer react_server;
    static ZeroMQServer zmq_server;
    public static void main(String[] args) {
        int outbound_port = 9000;
        react_server = new ReactServer(new InetSocketAddress("localhost", outbound_port));
        react_server.start();
        zmq_server = new ZeroMQServer(react_server);
        new Thread (zmq_server).start();
        Runtime.getRuntime().addShutdownHook(new ShutdownHook(react_server));
    }
}