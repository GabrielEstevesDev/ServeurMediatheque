package bserveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class ServiceAbstract implements Runnable
{
    private  Socket maSocket;

    public ServiceAbstract(Socket socketCoteServeur) {
        this.maSocket = socketCoteServeur;
    }
   
    protected Socket getSocket() {
        return this.maSocket;
    }

    @Override
    public abstract void run();

    protected void closeSocket() {
        if (this.maSocket != null) {
            try {
                this.maSocket.close();
            } catch (IOException e) {
                // gérer l'exception
            }
            this.maSocket = null;
        }
    }
}