/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bruno
 */
public class TcpServerRunnable implements Runnable {
    private Thread t;
    private String threadName = "TCP Server";
    private ServerSocket welcomeSocket;
    private Boolean readyToRun = false;
    private SerialMonitorRunnable serialMonitor;
    private KeyboardMonitorRunnable keyboardMonitor;
    public ArrayList<TcpClientHandler> clientsArray;
    private boolean DEV;
    
    public void notifyClients(String txt){
        if(clientsArray.size() > 0){
            for(int i=0; i<=clientsArray.size()-1; i++){
                clientsArray.get(i).sendText(txt);
            }   
        }
        else{
            System.out.println("No clients");
        }
    }

    TcpServerRunnable(int serverPort, boolean DEVMODE){
        System.out.println("Creating " +  threadName );
        DEV = DEVMODE;
        clientsArray = new ArrayList<>();
        
        if(DEV){
        	keyboardMonitor = new KeyboardMonitorRunnable();
	        keyboardMonitor.start();
        }
        else{
	        serialMonitor = new SerialMonitorRunnable();
	        serialMonitor.start();
        }
        
        try {
            welcomeSocket = new ServerSocket(serverPort);
            readyToRun = true;
        } catch (IOException ex) {
            Logger.getLogger(TcpServerRunnable.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void closeServerSocket(){
    	try {
			welcomeSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @Override
    public void run() {
        System.out.println("Running " +  threadName );
        
        if(readyToRun){
            while(true) {
                try {
                    Socket connectionSocket = welcomeSocket.accept();
                    clientsArray.add(new TcpClientHandler(connectionSocket));
                    clientsArray.get(clientsArray.size()-1).start();
                    System.out.println("Number of clients: " +  clientsArray.size() );
                } 
                catch (IOException ex) {
                    Logger.getLogger(TcpServerRunnable.class.getName()).log(Level.SEVERE, null, ex);
                    closeServerSocket();
                    break;
                }
            }
        }

        System.out.println("Thread " +  threadName + " exiting.");
    }

    public void start() {
        System.out.println("Starting " +  threadName );
        if (t == null)
        {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}
