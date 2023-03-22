package bserveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class ServiceAbstract implements Runnable
{
    private final Socket maSocket;

    public ServiceAbstract(final Socket socketCoteServeur) {
        this.maSocket = socketCoteServeur;
    }
   
    protected Socket getSocket() {
        return this.maSocket;
    }

    @Override
    public abstract void run();

    public void close() {
        try {
            this.maSocket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}