package bserveur;

import java.lang.reflect.InvocationTargetException;
import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

public class Serveur implements Runnable
{
    private Class<? extends ServiceAbstract> laClassRunnable;
    private ServerSocket monServeur;

    public Serveur(final Class<? extends ServiceAbstract> laclass, final int port) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
        this.laClassRunnable = laclass;
        try {
            this.monServeur = new ServerSocket(port);
            laclass.getConstructor(Socket.class).newInstance(new Socket());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (true) {
                final Socket socketCoteServeur = this.monServeur.accept();
                new Thread(this.laClassRunnable.getConstructor(Socket.class).newInstance(socketCoteServeur)).start();
            }
        }
        catch (IOException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            this.closeServeur();
        }
    }
    
    protected ServerSocket getServeur() {
    	return monServeur;
    }
    
    protected void closeServeur() {
    	try {
			monServeur.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
}