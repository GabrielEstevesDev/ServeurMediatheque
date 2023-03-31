package testmain;

import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


public class main {
	public static void main(String[] args) {
		Timer timer = new Timer();

		TimerTask task = new TimerTask() {
		    public void run() {
		        System.out.println("je me suis lancé");
		    }
		};

		timer.cancel();
		timer.purge();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		timer.schedule(task, 1000);
		
	

	}
}
