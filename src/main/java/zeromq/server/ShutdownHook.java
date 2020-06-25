package zeromq.server;

import java.io.IOException;

public class ShutdownHook extends Thread {
    private ReactServer react_server;
    public ShutdownHook(ReactServer react_server){
        this.react_server = react_server;
    }
    
    @Override
    public void run() {
        try {
            System.out.println("\nJVM shutting down, shutting down WS thread.");
            react_server.stop();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }     
}