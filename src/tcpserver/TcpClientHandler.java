/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tcpserver;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bruno
 */
public class TcpClientHandler implements Runnable {
    private String threadName = "Tcp client Handler";
    private Thread t;
    private Socket clientSocket;
    private PrintWriter pw;

    TcpClientHandler(Socket socket){
        System.out.println("Creating " +  threadName );        
        clientSocket = socket;
    }

    @Override
    public synchronized void run() {
        System.out.println("Running " +  threadName );
        
        try {
            pw = new PrintWriter(clientSocket.getOutputStream(), true);
            wait();
        } catch (InterruptedException ex) {
            Logger.getLogger(TcpClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TcpClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println("Thread " +  threadName + " exiting.");
    }
    
    public synchronized void sendText(String txt){
        txt = txt.trim();
        pw.println(txt);
    }

    public void start () {
        System.out.println("Starting " +  threadName );
        if (t == null)
        {
            t = new Thread (this, threadName);
            t.start ();
        }
    }
}
