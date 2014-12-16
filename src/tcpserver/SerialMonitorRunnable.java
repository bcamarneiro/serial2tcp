/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 *
 * @author Bruno
 */
public class SerialMonitorRunnable implements Runnable {
    private String threadName = "Serial Monitor";
    private Thread t;
    public ArrayList<TcpClientHandler> clientsArray;

    SerialMonitorRunnable(){
        System.out.println("Creating " +  threadName );
        //clientsArray = c;
    }

    public void run() {
        System.out.println("Running " +  threadName );
        Scanner sc = new Scanner(System.in);
        String xxx = null;
        
        //TO-DO Here listen to serial port
        
        while(true){    
            System.out.println("Write something!");
            xxx = sc.nextLine();
            TcpServer.R1.notifyClients(xxx);
        }
        
        //System.out.println("Thread " +  threadName + " exiting.");
    }

    public void start ()
    {
        System.out.println("Starting " +  threadName );
        if (t == null)
        {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}
